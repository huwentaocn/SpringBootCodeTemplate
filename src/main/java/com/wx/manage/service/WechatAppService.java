package com.wx.manage.service;

import com.wx.manage.pojo.req.GetWechatUserInfoReq;
import com.wx.manage.pojo.req.GetWechatUserPhoneReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.Result;

/**
 * @Description 微信小程序接口管理接口层
 * @Date 2023/8/29 14:48 星期二
 * @Author Hu Wentao
 */
public interface WechatAppService {

    Result<UserLoginResp> appLogin(String code);

    Result<UserInfoVo> bindUserPhoneNumber(GetWechatUserPhoneReq req);

    Result<UserInfoVo> getWechatUserInfo(GetWechatUserInfoReq req);
}
