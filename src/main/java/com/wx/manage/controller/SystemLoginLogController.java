package com.wx.manage.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@RestController
@CrossOrigin
@ApiIgnore
@Api(tags = "管理后台 - 登录日志管理模块")
@RequestMapping("/system/login/og")
public class SystemLoginLogController {

}
