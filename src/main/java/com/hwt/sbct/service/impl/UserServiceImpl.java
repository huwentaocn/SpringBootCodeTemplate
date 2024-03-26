package com.hwt.sbct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwt.sbct.constant.LoginType;
import com.hwt.sbct.mapper.UserMapper;
import com.hwt.sbct.pojo.entity.User;
import com.hwt.sbct.pojo.req.UserLoginReq;
import com.hwt.sbct.service.UserService;
import com.hwt.sbct.constant.RedisConstant;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.pojo.entity.OssFile;
import com.hwt.sbct.pojo.req.UserRegisterReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.pojo.vo.UserInfoVo;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.OssFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwt.sbct.until.EncryptionUtil;
import com.hwt.sbct.until.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2024-03-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OssFileService ossFileService;

    @Override
    public UserInfoVo register(UserRegisterReq req) {
        String userName = req.getUserName();
        String code = req.getCode();
        String password = req.getPassword();

        //获取redis中的code
        String smsCaptchaKey = RedisConstant.getSmsCaptchaKey(userName);
        String redisCode = (String) redisTemplate.opsForValue().get(smsCaptchaKey);
        //验证码失效
        if(StringUtils.isBlank(redisCode)){
            throw new GlobalException(ResultCodeEnum.SMS_CODE_INVALID_ERROR);
        }
        //验证码错误
        if (!redisCode.equals(code)) {
            throw new GlobalException(ResultCodeEnum.SMS_CODE_ERROR);
        }
        //验证码正确，一次使用，删除code中的redis
        redisTemplate.delete(smsCaptchaKey);

        //校验用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        if (count(queryWrapper) > 0) {
            throw new GlobalException(ResultCodeEnum.DATA_EXIST_FAIL, "用户已存在");
        }

        //创建用户
        User user = new User();
        BeanUtils.copyProperties(req, user);
        user.setPassword(EncryptionUtil.encryptMD5(password));
        saveOrUpdate(user);

        //构建响应体
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        //查询头像url
        OssFile ossFile = ossFileService.getById(user.getHeadResourceId());
        userInfoVo.setHeadUrl(ossFile.getAccLocation());

        return userInfoVo;
    }

    @Override
    public UserLoginResp login(UserLoginReq req) {
        Integer type = req.getType();
        String userName = req.getUserName();
        String password = req.getPassword();

        //检查登录
        User user = checkLoginByType(type, userName, password);

        //生成token
        String token = JwtUtil.createToken(user.getId(), user.getUserName());

        //构建响应体
        UserLoginResp userLoginResp = new UserLoginResp();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userLoginResp);
        userLoginResp.setUserInfoVo(userInfoVo);
        userLoginResp.setToken(token);

        return userLoginResp;
    }

    @Override
    public UserInfoVo getUserInfoVoById(Long id) {
        UserInfoVo userInfoVo = null;
        User user = getById(id);
        if (user != null) {
            userInfoVo = new UserInfoVo();
            BeanUtils.copyProperties(user, userInfoVo);
            //查询头像url
            OssFile ossFile = ossFileService.getById(user.getHeadResourceId());
            userInfoVo.setHeadUrl(ossFile.getAccLocation());
        }
        return userInfoVo;
    }

    private User checkLoginByType(Integer type, String userName, String password) {
        User user = null;
        if (LoginType.PASSWORD_LOGIN.code.equals(type)) {
            //用户名密码登录
            user = loginByUserNamePassword(userName, password);
        } else if (LoginType.MOBILE_CODE_LOGIN.code.equals(type)) {
            //手机号 + 验证码登录
            user = loginByMobileCode(userName, password);
        } else {
            throw new GlobalException(ResultCodeEnum.LOGIN_TYPE_NOT_OPENED_ERROR);
        }
        return user;
    }

    /**
     * 手机号码 + 验证码登录
     * @param mobile
     * @param code
     * @return
     */
    private User loginByMobileCode(String mobile, String code) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getMobile, mobile);
        User user = getOne(queryWrapper);
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
        }

        //获取redis中的code
        String smsCaptchaKey = RedisConstant.getSmsCaptchaKey(mobile);
        String redisCode = (String) redisTemplate.opsForValue().get(smsCaptchaKey);
        //验证码失效
        if(StringUtils.isBlank(redisCode)){
            throw new GlobalException(ResultCodeEnum.SMS_CODE_INVALID_ERROR);
        }
        //验证码错误
        if (!redisCode.equals(code)) {
            throw new GlobalException(ResultCodeEnum.SMS_CODE_ERROR);
        }
        return user;
    }

    /**
     * 用户名 + 密码登录
     * @param userName
     * @param password
     * @return
     */
    private User loginByUserNamePassword(String userName, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User user = getOne(queryWrapper);
        if (user == null) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
        }
        if (!user.getPassword().equals(EncryptionUtil.encryptMD5(password))) {
            throw new GlobalException(ResultCodeEnum.PASSWORD_ERROR, "密码错误");
        }
        return user;
    }
}
