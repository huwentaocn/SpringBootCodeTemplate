package com.wx.manage.controller;

import com.wx.manage.pojo.req.DataSourceConfigCreateVo;
import com.wx.manage.pojo.req.DataSourceConfigUpdateVo;
import com.wx.manage.pojo.resp.DataSourceConfigResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.InfraDataSourceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 数据源配置表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@RestController
@CrossOrigin
@Api(tags = "数据源管理模块")
@RequestMapping("/infra/dataSourceConfig")
public class InfraDataSourceConfigController {

    @Autowired
    private InfraDataSourceConfigService dataSourceConfigService;

    @PostMapping("/create")
    @ApiOperation(value = "创建数据源配置", notes = "创建数据源配置")
    public Result<Long> createDataSourceConfig(@Valid @RequestBody DataSourceConfigCreateVo createReq) {
        return Result.success(dataSourceConfigService.createDataSourceConfig(createReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新数据源配置", notes = "更新数据源配置")
    public Result<Boolean> updateDataSourceConfig(@Valid @RequestBody DataSourceConfigUpdateVo updateReq) {
        return Result.success(dataSourceConfigService.updateDataSourceConfig(updateReq));
    }

    @PutMapping("/delete")
    @ApiOperation(value = "删除数据源配置", notes = "删除数据源配置")
    public Result<Boolean> deleteDataSourceConfig(@RequestParam("id") Long id) {
        return Result.success(dataSourceConfigService.deleteDataSourceConfig(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得数据源配置详情", notes = "获得数据源配置详情")
    public Result<DataSourceConfigResp> getDataSourceConfigDetail(@RequestParam("id") Long id) {
        return Result.success(dataSourceConfigService.getDataSourceConfigDetail(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得数据源配置列表", notes = "获得数据源配置列表")
    public Result<List<DataSourceConfigResp>> getDataSourceConfigList() {
        return Result.success(dataSourceConfigService.getDataSourceConfigList());
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试数据源配置", notes = "测试数据源配置")
    public Result<Boolean> testDataSourceConfig(@RequestParam("id") Long id) {
        return Result.success(dataSourceConfigService.testDataSourceConfig(id));
    }

    @GetMapping("/init/table/structure")
    @ApiOperation(value = "初始化表结构", notes = "初始化表结构")
    public Result<Boolean> initTableStructureDataSourceConfig(@RequestParam("id") Long id) {
        return Result.success(dataSourceConfigService.initTableStructureDataSourceConfig(id));
    }




}
