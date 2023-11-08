package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemUsers;
import com.wx.manage.pojo.req.AuthLoginReq;
import com.wx.manage.pojo.req.SendSmsReq;
import com.wx.manage.pojo.resp.AuthLoginResp;
import com.wx.manage.result.Result;

/**
 * @Description 认证接口
 * @Date 2023/11/7 17:07 星期二
 * @Author Hu Wentao
 */
public interface AuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    SystemUsers authenticate(String username, String password);

    /**
     * 账号登录
     *
     * @param req 登录信息
     * @return 登录结果
     */

    AuthLoginResp login(AuthLoginReq req);
}
