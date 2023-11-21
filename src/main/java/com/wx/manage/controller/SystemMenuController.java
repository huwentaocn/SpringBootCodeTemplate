package com.wx.manage.controller;

import com.wx.manage.pojo.req.MenuCreateReq;
import com.wx.manage.pojo.req.MenuListReq;
import com.wx.manage.pojo.req.MenuUpdateReq;
import com.wx.manage.pojo.resp.MenuResp;
import com.wx.manage.pojo.resp.MenuSimpleResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@RestController
@CrossOrigin
@Api(tags = "管理后台 - 菜单管理")
@RequestMapping("/system/menu")
public class SystemMenuController {

    @Autowired
    private SystemMenuService menuService;

    @PostMapping("/create")
    @ApiOperation(value = "创建菜单", notes = "创建菜单")
    public Result<Long> createMenu(@Valid @RequestBody MenuCreateReq createReq) {
        return Result.success(menuService.createMenu(createReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新菜单", notes = "更新菜单")
    public Result<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReq updateReq) {
        return Result.success(menuService.updateMenu(updateReq));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    public Result<Boolean> deleteMenu(@RequestParam("id") Long id) {
        return Result.success(menuService.deleteMenu(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得菜单详情", notes = "获得菜单详情")
    public Result<MenuResp> getMenuDetail(@RequestParam("id") Long id) {
        return Result.success(menuService.getMenuDetail(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得菜单列表", notes = "获得菜单列表")
    public Result<List<MenuResp>> getMenuList(MenuListReq req) {
        return Result.success(menuService.getMenuList(req));
    }

    @GetMapping("/list/by/tenant")
    @ApiOperation(value = "获取租户的菜单", notes = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。" +
            "在多租户的场景下，会只返回租户所在套餐有的菜单")
    public Result<List<MenuSimpleResp>> getMenuListByTenant() {
        return Result.success(menuService.getMenuListByTenant());
    }



}
