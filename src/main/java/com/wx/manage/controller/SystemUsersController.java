package com.wx.manage.controller;

import com.wx.manage.pojo.entity.SystemUsers;
import com.wx.manage.pojo.resp.UserSimpleResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@RestController
@RequestMapping("/systemUsers")
public class SystemUsersController {

    @Autowired
    private SystemUsersService usersService;


    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取用户精简信息列表", notes = "只包含被开启的用户，主要用于前端的下拉选项")
    public Result<List<UserSimpleResp>> getSimpleUserList() {
        return Result.success(usersService.getSimpleUserList());
    }



}
