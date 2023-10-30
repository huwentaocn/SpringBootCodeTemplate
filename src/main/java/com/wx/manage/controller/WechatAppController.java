package com.wx.manage.controller;

import com.wx.manage.exception.BindingResultResponse;
import com.wx.manage.pojo.req.GetWechatUserInfoReq;
import com.wx.manage.pojo.req.GetWechatUserPhoneReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.Result;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.WechatAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 微信小程序接口管理模块
 * @Date 2023/8/29 14:47 星期二
 * @Author Hu Wentao
 */

@Slf4j
@Api(tags = "微信小程序接口管理模块")
@RestController
@CrossOrigin
@RequestMapping("/wechat/app")
public class WechatAppController {

    @Autowired
    private WechatAppService wechatAppService;


    @ApiOperation(value = "微信登录获取openid（小程序）", notes = "微信登录获取openid（小程序）")
    @GetMapping("/login/{code}")
    public Result<UserLoginResp> appLogin(@PathVariable String code) {

        if (StringUtils.isBlank(code)) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL);
        }

        return wechatAppService.appLogin(code);
    }

    @ApiOperation(value = "用户绑定手机号码（小程序）", notes = "获取用户手机号码（小程序）")
    @PostMapping("/bind/user/phoneNumber")
    public Result<UserInfoVo> bindUserPhoneNumber(GetWechatUserPhoneReq req,
                                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return wechatAppService.bindUserPhoneNumber(req);
    }

    @ApiOperation(value = "获取微信用户信息（小程序）", notes = "获取微信用户信息（小程序）")
    @GetMapping("/get/wechat/user/info")
    public Result<UserInfoVo> getWechatUserInfo(GetWechatUserInfoReq req,
                                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return wechatAppService.getWechatUserInfo(req);
    }
}
