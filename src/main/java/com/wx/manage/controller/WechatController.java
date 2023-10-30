package com.wx.manage.controller;

import com.wx.manage.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 微信服务接口管理模块
 * @Date 2023/8/30 17:40 星期三
 * @Author Hu Wentao
 */

@Slf4j
@Api(tags = "微信服务接口管理模块")
@RestController
@CrossOrigin
@RequestMapping("/wechat/app")
public class WechatController {

    @Autowired
    private WechatService wechatService;


    @ApiOperation(value = "获取登录二维码", notes = "获取登录二维码")
    @GetMapping("/get/login/QR/code")
    public String getLoginQRCode() {

        return wechatService.getLoginQRCode();
    }
}
