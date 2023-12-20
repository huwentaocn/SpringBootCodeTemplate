package com.wx.manage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.constant.RedisConstant;
import com.wx.manage.constant.UserTypeEnum;
import com.wx.manage.constant.logger.LoginLogTypeEnum;
import com.wx.manage.constant.logger.LoginResultEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.SystemUsers;
import com.wx.manage.pojo.req.AuthLoginReq;
import com.wx.manage.pojo.resp.AuthLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.AuthService;
import com.wx.manage.service.SystemLoginLogService;
import com.wx.manage.service.SystemUsersService;
import com.wx.manage.until.EncryptionUtil;
import com.wx.manage.until.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Description 认证接口实现类
 * @Date 2023/11/7 17:28 星期二
 * @Author Hu Wentao
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SystemUsersService usersService;
    
    @Autowired
    private SystemLoginLogService systemLoginLogService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SystemUsers authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SystemUsers::getUsername, username);
        SystemUsers user = usersService.getOne(queryWrapper);
        if (user == null) {
            systemLoginLogService.createLoginLog(null, username, UserTypeEnum.ADMIN, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw new GlobalException(ResultCodeEnum.AUTH_LOGIN_BAD_CREDENTIALS_ERROR);
        }
        //判断密码是否正确
        if (!password.equals(user.getPassword())) {
            systemLoginLogService.createLoginLog(user.getId(), username, UserTypeEnum.ADMIN, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw new GlobalException(ResultCodeEnum.AUTH_LOGIN_BAD_CREDENTIALS_ERROR);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), EnableStatusEnum.ENABLE.getStatus())) {
            systemLoginLogService.createLoginLog(user.getId(), username, UserTypeEnum.ADMIN, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw new GlobalException(ResultCodeEnum.AUTH_LOGIN_USER_DISABLED_ERROR);
        }
        return user;
    }


    @Override
    public AuthLoginResp login(AuthLoginReq req, HttpServletRequest request) {
        // 使用账号密码，进行登录
        SystemUsers user = authenticate(req.getUsername(), req.getPassword());
//
//        // 如果 socialType 非空，说明需要绑定社交用户
//        if (reqVO.getSocialType() != null) {
//            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
//                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
//        }
        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(UserTypeEnum.ADMIN, user, LoginLogTypeEnum.LOGIN_USERNAME);
    }

    private AuthLoginResp createTokenAfterLoginSuccess(UserTypeEnum userTypeEnum, SystemUsers user, LoginLogTypeEnum logType) {
        Long userId = user.getId();
        String username = user.getUsername();
        String nickname = user.getNickname();
        // 插入登陆日志
        systemLoginLogService.createLoginLog(userId, username, userTypeEnum, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
//        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
//                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
//        // 构建返回结果
//        return AuthConvert.INSTANCE.convert(accessTokenDO);

        //生成token
        String token = JwtUtil.createToken(userId, username, userTypeEnum.getCode());

        AuthLoginResp authLoginResp = new AuthLoginResp();
        authLoginResp.setUserId(userId);
        authLoginResp.setAccessToken(token);

        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(userId);
        userInfoVo.setUserName(username);
        userInfoVo.setNickName(nickname);

        //获取当前登录用户信息，放到Redis里面，设置有效时间
        redisTemplate.opsForValue().set(RedisConstant.getTenantUserInfoKey(userTypeEnum.getCode(), userInfoVo.getId()), userInfoVo, RedisConstant.USER_KEY_TIMEOUT, TimeUnit.DAYS);


        return authLoginResp;
    }

}
