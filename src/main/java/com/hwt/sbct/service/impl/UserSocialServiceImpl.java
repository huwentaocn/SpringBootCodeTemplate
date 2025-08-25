package com.hwt.sbct.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hwt.sbct.constant.AuthStateRedisCache;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.pojo.entity.UserSocial;
import com.hwt.sbct.mapper.UserSocialMapper;
import com.hwt.sbct.pojo.entity.WxUser;
import com.hwt.sbct.pojo.req.UserSocialBindReq;
import com.hwt.sbct.pojo.req.UserSocialLoginReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.UserSocialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwt.sbct.service.WxUserService;
import com.hwt.sbct.until.HttpUtils;
import com.xkcoding.http.config.HttpConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.enums.scope.*;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.*;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * 社交用户表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2025-08-19
 */
@Slf4j
@Service
public class UserSocialServiceImpl extends ServiceImpl<UserSocialMapper, UserSocial> implements UserSocialService {

    @Autowired
    private AuthStateRedisCache stateRedisCache;

    @Autowired
    private WxUserService userService;

    @Value("${justauth.DINGTALK.client-id}")
    private String DINGTALK_CLIENT_ID;

    @Value("${justauth.DINGTALK.client-secret}")
    private String DINGTALK_CLIENT_SECRET;

    @Value("${justauth.DINGTALK.ignore-check-redirect-uri}")
    private Boolean DINGTALK_IGNORE_CHECK_REDIRECT_URI;


    @Override
    public String getAuthorizeUrl(String type, String redirectUri) {
        // 获得对应的 AuthRequest 实现
        AuthRequest authRequest = getAuthRequest(type);
        // 生成跳转地址
        String authorizeUri = authRequest.authorize(AuthStateUtils.createState());
        return HttpUtils.replaceUrlQuery(authorizeUri, "redirect_uri", redirectUri);
    }

    @Override
    public UserLoginResp socialQuickLogin(UserSocialLoginReq req) {
        String code = req.getCode();
        String state = req.getState();
        String type = req.getType();


        // 获得对应的 AuthRequest 实现
        AuthRequest authRequest = getAuthRequest(type);
        AuthCallback authCallback = AuthCallback.builder().code(code).state(state).build();
        AuthResponse<AuthUser> authUserAuthResponse = authRequest.login(authCallback);
        log.info("【获取三方登录】[请求社交平台 type({}) request({}) response({})]", type, JSONObject.toJSONString(authCallback), JSONObject.toJSONString(authUserAuthResponse));
        if (!authUserAuthResponse.ok()) {
            throw new GlobalException(ResultCodeEnum.SOCIAL_USER_AUTH_FAIL, authUserAuthResponse.getMsg());
        }
        AuthUser authUser = authUserAuthResponse.getData();
        if (Objects.isNull(authUser)) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "三方用户为空");
        }

        //保存或更新库中社交用户信息
        LambdaQueryWrapper<UserSocial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSocial::getType, type);
        queryWrapper.eq(UserSocial::getUuid, authUser.getUuid());
        UserSocial userSocial = getOne(queryWrapper);

        //校验社交用户存在
        if (Objects.isNull(userSocial)) {
            throw new GlobalException(ResultCodeEnum.SOCIAL_USER_NOT_EXIST_FAIL, "社交用户不存在或未绑定平台用户");
        }

        //获取用户平台内的用户信息
        Long userId = userSocial.getUserId();
        if (Objects.isNull(userId)) {
            throw new GlobalException(ResultCodeEnum.SOCIAL_USER_NOT_BIND_FAIL);
        }

        //更新社交用户信息
        userSocial.setType(type);
        userSocial.setState(state);
        userSocial.setCode(code);
        userSocial.setUuid(authUser.getUuid());
        userSocial.setUserName(authUser.getUsername());
        userSocial.setNickName(authUser.getNickname());
        userSocial.setAvatar(authUser.getAvatar());
        userSocial.setBlog(authUser.getBlog());
        userSocial.setCompany(authUser.getCompany());
        userSocial.setLocation(authUser.getLocation());
        userSocial.setEmail(authUser.getEmail());
        userSocial.setRemark(authUser.getRemark());
        userSocial.setToken(authUser.getToken().getAccessToken());
        userSocial.setRawTokenInfo(JSONObject.toJSONString(authUser.getToken()));
        userSocial.setRawUserInfo(JSONObject.toJSONString(authUser.getRawUserInfo()));

        AuthUserGender gender = authUser.getGender();
        if (Objects.nonNull(gender)) {
            String genderCode = gender.getCode();
            if (Objects.nonNull(genderCode)) {
                if (AuthUserGender.MALE.getCode().equals(genderCode)) {
                    userSocial.setSex(1);
                } else if (AuthUserGender.FEMALE.getCode().equals(genderCode)) {
                    userSocial.setSex(2);
                }
            }
        }

        saveOrUpdate(userSocial);

        //登录平台
        WxUser wxUser = userService.getById(userId);
        if (Objects.isNull(wxUser)) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
        }

        UserLoginResp userLoginResp = userService.createTokenAfterLoginSuccess(wxUser);

        return userLoginResp;
    }

    @Override
    public Boolean bindUserName(UserSocialBindReq req) {
        Long userId = req.getUserId();
        String type = req.getType();
        String code = req.getCode();
        String state = req.getState();

        //校验用户是否存在
        WxUser user = userService.getById(userId);
        if (Objects.isNull(user)) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "用户不存在");
        }

        // 获得对应的 AuthRequest 实现
        AuthRequest authRequest = getAuthRequest(type);
        AuthCallback authCallback = AuthCallback.builder().code(code).state(state).build();
        AuthResponse<AuthUser> authUserAuthResponse = authRequest.login(authCallback);
        log.info("【获取三方登录】[请求社交平台 type({}) request({}) response({})]", type, JSONObject.toJSONString(authCallback), JSONObject.toJSONString(authUserAuthResponse));
        if (!authUserAuthResponse.ok()) {
            throw new GlobalException(ResultCodeEnum.SOCIAL_USER_AUTH_FAIL, authUserAuthResponse.getMsg());
        }
        AuthUser authUser = authUserAuthResponse.getData();
        if (Objects.isNull(authUser)) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "三方用户为空");
        }

        //保存或更新库中社交用户信息
        LambdaQueryWrapper<UserSocial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSocial::getType, type);
        queryWrapper.eq(UserSocial::getUuid, authUser.getUuid());
        UserSocial userSocial = getOne(queryWrapper);

        if (Objects.nonNull(userSocial)) {
            //获取用户平台内的用户信息
            if (Objects.nonNull(userSocial.getUserId())) {
                throw new GlobalException(ResultCodeEnum.SOCIAL_USER_ALREADY_BIND_FAIL);
            }
        } else {
            userSocial = new UserSocial();
        }

        userSocial.setType(type);
        userSocial.setState(state);
        userSocial.setCode(code);
        userSocial.setUuid(authUser.getUuid());
        userSocial.setUserName(authUser.getUsername());
        userSocial.setNickName(authUser.getNickname());
        userSocial.setAvatar(authUser.getAvatar());
        userSocial.setBlog(authUser.getBlog());
        userSocial.setCompany(authUser.getCompany());
        userSocial.setLocation(authUser.getLocation());
        userSocial.setEmail(authUser.getEmail());
        userSocial.setRemark(authUser.getRemark());
        userSocial.setToken(authUser.getToken().getAccessToken());
        userSocial.setRawTokenInfo(JSONObject.toJSONString(authUser.getToken()));
        userSocial.setRawUserInfo(JSONObject.toJSONString(authUser.getRawUserInfo()));

        AuthUserGender gender = authUser.getGender();
        if (Objects.nonNull(gender)) {
            String genderCode = gender.getCode();
            if (Objects.nonNull(genderCode)) {
                if (AuthUserGender.MALE.getCode().equals(genderCode)) {
                    userSocial.setSex(1);
                } else if (AuthUserGender.FEMALE.getCode().equals(genderCode)) {
                    userSocial.setSex(2);
                }
            }
        }
        userSocial.setUserId(userId);

        return saveOrUpdate(userSocial);
    }


    /**
     * 根据具体的授权来源，获取授权请求工具类
     *
     * @param source
     * @return
     */
    private AuthRequest getAuthRequest(String source) {
        AuthRequest authRequest = null;
        switch (source.toLowerCase()) {
            case "dingtalk":
                authRequest = new AuthDingTalkRequest(AuthConfig.builder()
                        .clientId(DINGTALK_CLIENT_ID)
                        .clientSecret(DINGTALK_CLIENT_SECRET)
//                        .redirectUri("http://localhost:9000/sbct/user/social/login")
                        .ignoreCheckRedirectUri(DINGTALK_IGNORE_CHECK_REDIRECT_URI)
                        .build());
                break;
            case "baidu":
                authRequest = new AuthBaiduRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/baidu")
                        .scopes(Arrays.asList(
                                AuthBaiduScope.BASIC.getScope(),
                                AuthBaiduScope.SUPER_MSG.getScope(),
                                AuthBaiduScope.NETDISK.getScope()
                        ))
//                        .clientId("")
//                        .clientSecret("")
//                        .redirectUri("http://localhost:9001/oauth/baidu/callback")
                        .build());
                break;
            case "github":
                authRequest = new AuthGithubRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/github")
                        .scopes(AuthScopeUtils.getScopes(AuthGithubScope.values()))
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder()
                                .timeout(15000)
                                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                                .build())
                        .build(), stateRedisCache);
                break;
            case "gitee":
                authRequest = new AuthGiteeRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://127.0.0.1:8443/oauth/callback/gitee")
                        .scopes(AuthScopeUtils.getScopes(AuthGiteeScope.values()))
                        .build(), stateRedisCache);
                break;
            case "weibo":
                authRequest = new AuthWeiboRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/weibo")
                        .scopes(Arrays.asList(
                                AuthWeiboScope.EMAIL.getScope(),
                                AuthWeiboScope.FRIENDSHIPS_GROUPS_READ.getScope(),
                                AuthWeiboScope.STATUSES_TO_ME_READ.getScope()
                        ))
                        .build());
                break;
            case "coding":
                authRequest = new AuthCodingRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/coding")
                        .domainPrefix("")
                        .scopes(Arrays.asList(
                                AuthCodingScope.USER.getScope(),
                                AuthCodingScope.USER_EMAIL.getScope(),
                                AuthCodingScope.USER_PHONE.getScope()
                        ))
                        .build());
                break;
            case "oschina":
                authRequest = new AuthOschinaRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/oschina")
                        .build());
                break;
            case "alipay":
                // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1，所以这儿的回调地址使用的局域网内的ip
                authRequest = new AuthAlipayRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .alipayPublicKey("")
                        .redirectUri("https://www.zhyd.me/oauth/callback/alipay")
                        .build());
                break;
            case "qq":
                authRequest = new AuthQqRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/qq")
                        .build());
                break;
            case "wechat_open":
                authRequest = new AuthWeChatOpenRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://www.zhyd.me/oauth/callback/wechat")
                        .build());
                break;
            case "csdn":
                authRequest = new AuthCsdnRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/csdn")
                        .build());
                break;
            case "taobao":
                authRequest = new AuthTaobaoRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/taobao")
                        .build());
                break;
            case "google":
                authRequest = new AuthGoogleRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/google")
                        .scopes(AuthScopeUtils.getScopes(AuthGoogleScope.USER_EMAIL, AuthGoogleScope.USER_PROFILE, AuthGoogleScope.USER_OPENID))
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder()
                                .timeout(15000)
                                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                                .build())
                        .build());
                break;
            case "facebook":
                authRequest = new AuthFacebookRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("https://justauth.cn/oauth/callback/facebook")
                        .scopes(AuthScopeUtils.getScopes(AuthFacebookScope.values()))
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder()
                                .timeout(15000)
                                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                                .build())
                        .build());
                break;
            case "douyin":
                authRequest = new AuthDouyinRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/douyin")
                        .build());
                break;
            case "linkedin":
                authRequest = new AuthLinkedinRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/linkedin")
                        .scopes(null)
                        .build());
                break;
            case "microsoft":
                authRequest = new AuthMicrosoftRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/microsoft")
                        .scopes(Arrays.asList(
                                AuthMicrosoftScope.USER_READ.getScope(),
                                AuthMicrosoftScope.USER_READWRITE.getScope(),
                                AuthMicrosoftScope.USER_READBASIC_ALL.getScope(),
                                AuthMicrosoftScope.USER_READ_ALL.getScope(),
                                AuthMicrosoftScope.USER_READWRITE_ALL.getScope(),
                                AuthMicrosoftScope.USER_INVITE_ALL.getScope(),
                                AuthMicrosoftScope.USER_EXPORT_ALL.getScope(),
                                AuthMicrosoftScope.USER_MANAGEIDENTITIES_ALL.getScope(),
                                AuthMicrosoftScope.FILES_READ.getScope()
                        ))
                        .build());
                break;
            case "mi":
                authRequest = new AuthMiRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/mi")
                        .build());
                break;
            case "toutiao":
                authRequest = new AuthToutiaoRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/toutiao")
                        .build());
                break;
            case "teambition":
                authRequest = new AuthTeambitionRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://127.0.0.1:8443/oauth/callback/teambition")
                        .build());
                break;
            case "pinterest":
                authRequest = new AuthPinterestRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("https://eadmin.innodev.com.cn/oauth/callback/pinterest")
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder()
                                .timeout(15000)
                                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                                .build())
                        .build());
                break;
            case "renren":
                authRequest = new AuthRenrenRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://127.0.0.1:8443/oauth/callback/teambition")
                        .build());
                break;
            case "stack_overflow":
                authRequest = new AuthStackOverflowRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("((")
                        .redirectUri("http://localhost:8443/oauth/callback/stack_overflow")
                        .stackOverflowKey("")
                        .build());
                break;
            case "huawei":
                authRequest = new AuthHuaweiRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://127.0.0.1:8443/oauth/callback/huawei")
                        .scopes(Arrays.asList(
                                AuthHuaweiScope.BASE_PROFILE.getScope(),
                                AuthHuaweiScope.MOBILE_NUMBER.getScope(),
                                AuthHuaweiScope.ACCOUNTLIST.getScope(),
                                AuthHuaweiScope.SCOPE_DRIVE_FILE.getScope(),
                                AuthHuaweiScope.SCOPE_DRIVE_APPDATA.getScope()
                        ))
                        .build());
                break;
            case "wechat_enterprise":
                authRequest = new AuthWeChatEnterpriseQrcodeRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://justauth.cn/oauth/callback/wechat_enterprise")
                        .agentId("1000003")
                        .build());
                break;
            case "kujiale":
                authRequest = new AuthKujialeRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/kujiale")
                        .build());
                break;
            case "gitlab":
                authRequest = new AuthGitlabRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/gitlab")
                        .scopes(AuthScopeUtils.getScopes(AuthGitlabScope.values()))
                        .build());
                break;
            case "meituan":
                authRequest = new AuthMeituanRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/meituan")
                        .build());
                break;
            case "eleme":
                authRequest = new AuthElemeRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://dblog-web.zhyd.me/oauth/callback/eleme")
                        .build());
                break;
            case "mygitlab":
//                authRequest = new AuthMyGitlabRequest(AuthConfig.builder()
//                        .clientId("")
//                        .clientSecret("")
//                        .redirectUri("http://127.0.0.1:8443/oauth/callback/mygitlab")
//                        .build());
                break;
            case "twitter":
                authRequest = new AuthTwitterRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("https://threelogin.31huiyi.com/oauth/callback/twitter")
                        // 针对国外平台配置代理
                        .httpConfig(HttpConfig.builder()
                                .timeout(15000)
                                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                                .build())
                        .build());
                break;
            case "wechat_mp":
                authRequest = new AuthWeChatMpRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("")
                        .build());
                break;
            case "aliyun":
                authRequest = new AuthAliyunRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/aliyun")
                        .build());
                break;
            case "xmly":
                authRequest = new AuthXmlyRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/xmly")
                        .build());
                break;
            case "feishu":
                authRequest = new AuthFeishuRequest(AuthConfig.builder()
                        .clientId("")
                        .clientSecret("")
                        .redirectUri("http://localhost:8443/oauth/callback/feishu")
                        .build());
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw new AuthException("未获取到有效的Auth配置");
        }
        return authRequest;
    }
}
