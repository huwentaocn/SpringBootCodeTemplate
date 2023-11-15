package com.wx.manage.controller;

import com.wx.manage.service.SystemRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色和菜单关联表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@RestController
@RequestMapping("/systemRoleMenu")
public class SystemRoleMenuController {

    @Autowired
    private SystemRoleMenuService roleMenuService;



}
