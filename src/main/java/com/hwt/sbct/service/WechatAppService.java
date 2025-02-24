package com.hwt.sbct.service;

import com.hwt.sbct.result.Result;
import com.hwt.sbct.pojo.req.GetWechatUserInfoReq;
import com.hwt.sbct.pojo.req.GetWechatUserPhoneReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.pojo.vo.UserInfoVo;

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
