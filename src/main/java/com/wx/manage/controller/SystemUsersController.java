package com.wx.manage.controller;

import com.wx.manage.pojo.entity.SystemUsers;
import com.wx.manage.pojo.req.UserCreateReq;
import com.wx.manage.pojo.req.UserUpdatePasswordReq;
import com.wx.manage.pojo.req.UserUpdateReq;
import com.wx.manage.pojo.resp.UserResp;
import com.wx.manage.pojo.resp.UserSimpleResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemUsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@CrossOrigin
@Api(tags = "前台 - 用户管理")
@RequestMapping("/system/users")
public class SystemUsersController {

    @Autowired
    private SystemUsersService usersService;

    @PostMapping("/create")
    @ApiOperation(value = "创建用户", notes = "创建用户")
    public Result<Long> createUser(@Valid @RequestBody UserCreateReq createReq) {
        return Result.success(usersService.createUser(createReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新用户", notes = "更新用户")
    public Result<Boolean> updateUser(@Valid @RequestBody UserUpdateReq updateReq) {
        return Result.success(usersService.updateUser(updateReq));
    }

    @PutMapping("/update-password")
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    public Result<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReq updatePasswordReq) {
        return Result.success(usersService.updateUserPassword(updatePasswordReq));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public Result<Boolean> deleteUser(@RequestParam("id") Long id) {
        return Result.success(usersService.deleteUser(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得用户详情", notes = "获得用户详情")
    public Result<UserResp> getUserDetail(@RequestParam("id") Long id) {
        return Result.success(usersService.getUserDetail(id));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取用户精简信息列表", notes = "只包含被开启的用户，主要用于前端的下拉选项")
    public Result<List<UserSimpleResp>> getSimpleUserList() {
        return Result.success(usersService.getSimpleUserList());
    }



}
