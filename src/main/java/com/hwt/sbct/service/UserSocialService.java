package com.hwt.sbct.service;

import com.hwt.sbct.pojo.entity.UserSocial;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwt.sbct.pojo.req.UserSocialBindReq;
import com.hwt.sbct.pojo.req.UserSocialLoginReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 社交用户表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2025-08-19
 */
public interface UserSocialService extends IService<UserSocial> {

    String getAuthorizeUrl(String type, String redirectUri);

    UserLoginResp socialQuickLogin(UserSocialLoginReq req);

    Boolean bindUserName(UserSocialBindReq req);
}
