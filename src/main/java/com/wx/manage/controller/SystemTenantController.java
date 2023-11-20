package com.wx.manage.controller;

import com.wx.manage.pojo.excel.TenantExcelVo;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.TenantCreateReq;
import com.wx.manage.pojo.req.TenantExportReq;
import com.wx.manage.pojo.req.TenantPageReq;
import com.wx.manage.pojo.req.TenantUpdateReq;
import com.wx.manage.pojo.resp.TenantResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.until.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@RestController
@CrossOrigin
@Api(tags = "租户管理模块")
@RequestMapping("/system/tenant")
public class SystemTenantController {

    @Autowired
    private SystemTenantService tenantService;

    @GetMapping("/get/tenant/by/name")
    @ApiOperation(value = "根据租户名称获取租户信息", notes = "根据租户名称获取租户信息")
    public Result<TenantResp> getTenantByName(@RequestParam("name") String name) {
        return Result.success(tenantService.getTenantByName(name));
    }


    @PostMapping("/create")
    @ApiOperation(value = "创建租户", notes = "创建租户")
    public Result<Long> createTenant(@Valid @RequestBody TenantCreateReq createReq) {
        return Result.success(tenantService.createTenant(createReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新租户", notes = "更新租户")
    public Result<Boolean> updateTenant(@Valid @RequestBody TenantUpdateReq updateReq) {
        return Result.success(tenantService.updateTenant(updateReq));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除租户", notes = "删除租户")
    public Result<Boolean> deleteTenant(@RequestParam("id") Long id) {
        return Result.success(tenantService.deleteTenant(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得租户详情", notes = "获得租户详情")
    public Result<TenantResp> getTenantDetail(@RequestParam("id") Long id) {
        return Result.success(tenantService.getTenantDetail(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得租户列表", notes = "获得租户列表")
    public Result<List<TenantResp>> getTenantList() {
        return Result.success(tenantService.getTenantList());
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取租户列表（分页）", notes = "获取租户列表（分页）")
    public Result<PageResult<TenantResp>> getTenantPage(@Valid TenantPageReq tenantPageReq) {
        return Result.success(tenantService.getTenantPage(tenantPageReq));
    }

    @GetMapping("/export-excel")
    @ApiOperation(value = "导出租户 Excel", notes = "导出租户 Excel")
    public void exportTenantExcel(@Valid TenantExportReq exportReq,
                                  HttpServletResponse response) throws IOException {
        // 导出 Excel
        List<TenantExcelVo> tenantExcelVoList = tenantService.getTenantList(exportReq);
        ExcelUtils.write(response, "租户.xls", "数据", TenantExcelVo.class, tenantExcelVoList);
    }



}
