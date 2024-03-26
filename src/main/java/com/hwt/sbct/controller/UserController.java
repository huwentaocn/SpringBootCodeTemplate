package com.hwt.sbct.controller;

import com.hwt.sbct.pojo.req.UserLoginReq;
import com.hwt.sbct.pojo.req.UserRegisterReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.pojo.vo.UserInfoVo;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2024-03-16
 */
@Api(tags = "用户管理模块")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户登录（密码登录）")
    @PostMapping("/hero/register")
    public Result<UserInfoVo> register(@RequestBody @Valid UserRegisterReq req) {

        return Result.success(userService.register(req));
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/hero/login")
    public Result<UserLoginResp> login(@RequestBody @Valid UserLoginReq req) {
        return Result.success(userService.login(req));
    }
}
