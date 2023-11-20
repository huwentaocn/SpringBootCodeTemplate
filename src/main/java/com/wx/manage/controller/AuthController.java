package com.wx.manage.controller;

import com.wx.manage.pojo.req.AuthLoginReq;
import com.wx.manage.pojo.resp.AuthLoginResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description 管理后台-认证
 * @Date 2023/11/7 17:07 星期二
 * @Author Hu Wentao
 */

@RestController
@CrossOrigin
@Api(tags = "用户管理模块")
@RequestMapping("/system/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value= "使用账号密码登录", notes = "使用账号密码登录")
    public Result<AuthLoginResp> login(@RequestBody @Valid AuthLoginReq req, HttpServletRequest request) {
        return Result.success(authService.login(req, request));
    }
}
