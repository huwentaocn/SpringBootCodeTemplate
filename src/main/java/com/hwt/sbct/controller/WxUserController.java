package com.hwt.sbct.controller;

import com.hwt.sbct.pojo.vo.UserInfoVo;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.WxUserService;
import com.hwt.sbct.exception.BindingResultResponse;
import com.hwt.sbct.pojo.req.UserPasswordLoginReq;
import com.hwt.sbct.pojo.req.UserRegisterReq;
import com.hwt.sbct.pojo.req.UserSmsLoginReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
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
 * @since 2023-08-28
 */
@RestController
@CrossOrigin
@Api(tags = "用户管理模块")
@RequestMapping("/wxUser")
public class WxUserController {

    @Autowired
    private WxUserService userService;

    @ApiOperation(value = "用户注册", notes = "用户登录（密码登录）")
    @PostMapping("/register")
    public Result<UserInfoVo> register(@RequestBody @Valid UserRegisterReq req,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return userService.register(req);
    }

    @ApiOperation(value = "用户密码登录", notes = "用户登录（密码登录）")
    @PostMapping("/password/login")
    public Result<UserLoginResp> passwordLogin(@RequestBody @Valid UserPasswordLoginReq req,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return userService.passwordLogin(req);
    }

    @ApiOperation(value = "短信验证码登录", notes = "用户登录（短信验证码登录）")
    @PostMapping("/sms/login")
    public Result<UserLoginResp> smsLogin(@RequestBody @Valid UserSmsLoginReq req,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return userService.smsLogin(req);
    }

}
