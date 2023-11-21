package com.wx.manage.controller;

import com.wx.manage.pojo.req.RoleCreateReq;
import com.wx.manage.pojo.req.RoleUpdateReq;
import com.wx.manage.pojo.resp.RoleResp;
import com.wx.manage.pojo.resp.TenantResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@RestController
@CrossOrigin
@Api(tags = "前台 - 角色管理")
@RequestMapping("/system/role")
public class SystemRoleController {
    
    @Autowired
    private SystemRoleService roleService;

    @PostMapping("/create")
    @ApiOperation(value = "创建角色", notes = "创建角色")
    public Result<Long> createRole(@Valid @RequestBody RoleCreateReq createReq) {
        return Result.success(roleService.createRole(createReq, null));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新角色", notes = "更新角色")
    public Result<Boolean> updateRole(@Valid @RequestBody RoleUpdateReq updateReq) {
        return Result.success(roleService.updateRole(updateReq));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public Result<Boolean> deleteRole(@RequestParam("id") Long id) {
        return Result.success(roleService.deleteRole(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得角色详情", notes = "获得角色详情")
    public Result<RoleResp> getRoleDetail(@RequestParam("id") Long id) {
        return Result.success(roleService.getRoleDetail(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得角色列表", notes = "获得角色列表")
    public Result<List<RoleResp>> getRoleList() {
        return Result.success(roleService.getRoleList());
    }

}
