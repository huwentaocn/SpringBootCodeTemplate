package com.wx.manage.controller;

import com.wx.manage.exception.BindingResultResponse;
import com.wx.manage.pojo.req.UserLoginReq;
import com.wx.manage.pojo.req.UserRegisterReq;
import com.wx.manage.pojo.req.UserSmsLoginReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.Result;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
