package com.hwt.sbct.controller;

import com.alibaba.fastjson.JSONObject;
import com.hwt.sbct.exception.BindingResultResponse;
import com.hwt.sbct.pojo.req.UserRegisterReq;
import com.hwt.sbct.pojo.req.UserSocialBindReq;
import com.hwt.sbct.pojo.req.UserSocialLoginReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.pojo.vo.UserInfoVo;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.UserSocialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 社交用户表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2025-08-19
 */
@RestController
@CrossOrigin
@Api(tags = "社交用户管理模块")
@RequestMapping("/user/social")
public class UserSocialController {

    @Resource
    private UserSocialService userSocialService;

    @GetMapping("/hero/auth/redirect")
    @ApiOperation(value = "社交授权的跳转", notes = "社交授权的跳转")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "社交类型", required = true),
            @ApiImplicitParam(name = "redirectUri", value = "回调路径")
    })
    public Result<String> socialLogin(@RequestParam("type") String type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return Result.success(userSocialService.getAuthorizeUrl(type, redirectUri));
    }

    @RequestMapping("/login")
    @ApiOperation(value = "社交快捷登录，使用 code 授权码", notes = "适合未登录的用户，但是社交账号已绑定用户")
    public Result<UserLoginResp> socialQuickLogin(@RequestBody @Valid UserSocialLoginReq req) {
        return Result.success(userSocialService.socialQuickLogin(req));
    }

    @RequestMapping("/bind")
    @ApiOperation(value = "社交账号绑定", notes = "绑定社交账号")
    public Result<Boolean> bindUserName(@RequestBody @Valid UserSocialBindReq req) {
        return Result.success(userSocialService.bindUserName(req));
    }


}
