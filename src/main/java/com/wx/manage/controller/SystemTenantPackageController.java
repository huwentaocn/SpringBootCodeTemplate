package com.wx.manage.controller;

import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.TenantPackageCreateReq;
import com.wx.manage.pojo.req.TenantPackagePageReq;
import com.wx.manage.pojo.req.TenantPackageUpdateReq;
import com.wx.manage.pojo.resp.TenantPackageResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemTenantPackageService;
import com.wx.manage.until.ExcelUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 租户套餐套餐表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/system/TenantPackage/package")
public class SystemTenantPackageController {


    @Autowired
    private SystemTenantPackageService tenantPackageService;


    @PostMapping("/create")
    @ApiOperation(value = "创建租户套餐", notes = "创建租户套餐")
    public Result<Long> createTenantPackage(@Valid @RequestBody TenantPackageCreateReq createReq) {
        return Result.success(tenantPackageService.createTenantPackage(createReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新租户套餐", notes = "更新租户套餐")
    public Result<Boolean> updateTenantPackage(@Valid @RequestBody TenantPackageUpdateReq updateReq) {
        return Result.success(tenantPackageService.updateTenantPackage(updateReq));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除租户套餐", notes = "删除租户套餐")
    public Result<Boolean> deleteTenantPackage(@RequestParam("id") Long id) {
        return Result.success(tenantPackageService.deleteTenantPackage(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得租户套餐详情", notes = "获得租户套餐详情")
    public Result<TenantPackageResp> getTenantPackageDetail(@RequestParam("id") Long id) {
        return Result.success(tenantPackageService.getTenantPackageDetail(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得租户套餐列表", notes = "获得租户套餐列表")
    public Result<List<TenantPackageResp>> getTenantPackageList(@RequestParam("status") Integer status) {
        return Result.success(tenantPackageService.getTenantPackageList(status));
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取租户套餐列表（分页）", notes = "获取租户套餐列表（分页）")
    public Result<PageResult<TenantPackageResp>> getTenantPackagePage(@Valid TenantPackagePageReq pageReq) {
        return Result.success(tenantPackageService.getTenantPackagePage(pageReq));
    }



}
