package com.wx.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.constant.RedisConstant;
import com.wx.manage.mapper.WxUserMapper;
import com.wx.manage.pojo.entity.WxUser;
import com.wx.manage.pojo.req.UserPasswordLoginReq;
import com.wx.manage.pojo.req.UserRegisterReq;
import com.wx.manage.pojo.req.UserSmsLoginReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.Result;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.WxUserService;
import com.wx.manage.until.EncryptionUtil;
import com.wx.manage.until.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-28
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

    @Autowired
    private WxUserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result<UserInfoVo> register(UserRegisterReq req) {
        String userName = req.getUserName();
        String code = req.getCode();
        String password = req.getPassword();
        Integer sex = req.getSex();
        String nickName = req.getNickName();
        String openId = req.getOpenId();
        Integer age = req.getAge();
        String headUrl = req.getHeadUrl();

        //获取redis中的code
        String smsCaptchaKey = RedisConstant.getSmsCaptchaKey(userName);
        String redisCode = (String) redisTemplate.opsForValue().get(smsCaptchaKey);
        //验证码失效
        if(StringUtils.isBlank(redisCode)){
            return Result.fail(ResultCodeEnum.SMS_CODE_INVALID_ERROR);
        }
        //验证码错误
        if (!redisCode.equals(code)) {
            return Result.fail(ResultCodeEnum.SMS_CODE_ERROR);
        }

        //验证码正确，一次使用，删除code中的redis
        redisTemplate.delete(smsCaptchaKey);

        //查询用户
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getUserName, userName);
        WxUser wxUser = userMapper.selectOne(queryWrapper);

        if (wxUser != null) {
            return Result.fail(ResultCodeEnum.DATA_EXIST_FAIL, "用户已存在");
        }

        //创建用户
        WxUser user = new WxUser();
        user.setUserName(userName);
        user.setPassword(EncryptionUtil.encryptMD5(password));
        user.setSex(sex);
        user.setNickName(nickName);
        user.setOpenId(openId);
        user.setAge(age);
        user.setHeadUrl(headUrl);
        saveOrUpdate(user);

        //构建响应体
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);

        return Result.success(userInfoVo);
    }

    @Override
    public Result<UserLoginResp> passwordLogin(UserPasswordLoginReq req) {
        String userName = req.getUserName();
        String password = req.getPassword();

        //查询用户
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getUserName, userName);
        WxUser wxUser = userMapper.selectOne(queryWrapper);

        if (wxUser == null) {
            return Result.fail(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
        }

        //判断密码是否正确
        if (!EncryptionUtil.encryptMD5(password).equals(wxUser.getPassword())) {
            return Result.fail(ResultCodeEnum.PASSWORD_ERROR);
        }

        //生成token
        String token = JwtUtil.createToken(wxUser.getId(), wxUser.getUserName());

        //构建响应体
        UserLoginResp userLoginResp = new UserLoginResp();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(wxUser, userInfoVo);
        userLoginResp.setUserInfoVo(userInfoVo);
        userLoginResp.setToken(token);

        //获取当前登录用户信息，放到Redis里面，设置有效时间
        redisTemplate.opsForValue().set(RedisConstant.getUserInfoKey(userInfoVo.getId()), userInfoVo, RedisConstant.USER_KEY_TIMEOUT, TimeUnit.DAYS);


        return Result.success(userLoginResp);
    }

    @Override
    public Result<UserLoginResp> smsLogin(UserSmsLoginReq req) {
        String userName = req.getUserName();
        String code = req.getCode();

        //查询用户
        LambdaQueryWrapper<WxUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WxUser::getUserName, userName);
        WxUser wxUser = userMapper.selectOne(queryWrapper);

        if (wxUser == null) {
            return Result.fail(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
        }

        //获取redis中的code
        String smsCaptchaKey = RedisConstant.getSmsCaptchaKey(userName);
        String redisCode = (String) redisTemplate.opsForValue().get(smsCaptchaKey);
        //验证码失效
        if(StringUtils.isBlank(redisCode)){
            return Result.fail(ResultCodeEnum.SMS_CODE_INVALID_ERROR);
        }
        //验证码错误
        if (!redisCode.equals(code)) {
            return Result.fail(ResultCodeEnum.SMS_CODE_ERROR);
        }

        //验证码正确，一次使用，删除code中的redis
        redisTemplate.delete(smsCaptchaKey);

        //生成token
        String token = JwtUtil.createToken(wxUser.getId(), wxUser.getUserName());

        //构建响应体
        UserLoginResp userLoginResp = new UserLoginResp();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(wxUser, userInfoVo);
        userLoginResp.setUserInfoVo(userInfoVo);
        userLoginResp.setToken(token);

        //获取当前登录用户信息，放到Redis里面，设置有效时间
        redisTemplate.opsForValue().set(RedisConstant.getUserInfoKey(userInfoVo.getId()), userInfoVo, RedisConstant.USER_KEY_TIMEOUT, TimeUnit.DAYS);


        return Result.success(userLoginResp);
    }
}
