package com.wx.manage.service;

import com.wx.manage.pojo.entity.WxUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.UserPasswordLoginReq;
import com.wx.manage.pojo.req.UserRegisterReq;
import com.wx.manage.pojo.req.UserSmsLoginReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.Result;

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
}
