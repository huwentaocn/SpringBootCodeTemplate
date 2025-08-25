package com.hwt.sbct.service;

import com.hwt.sbct.pojo.entity.WxUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwt.sbct.pojo.req.UserPasswordLoginReq;
import com.hwt.sbct.pojo.req.UserRegisterReq;
import com.hwt.sbct.pojo.req.UserSmsLoginReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.pojo.vo.UserInfoVo;
import com.hwt.sbct.result.Result;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-28
 */
public interface WxUserService extends IService<WxUser> {

    Result<UserInfoVo> register(UserRegisterReq req);

    Result<UserLoginResp> passwordLogin(UserPasswordLoginReq req);

    Result<UserLoginResp> smsLogin(UserSmsLoginReq req);

    UserLoginResp createTokenAfterLoginSuccess(WxUser wxUser);
}
