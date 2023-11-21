package com.wx.manage.controller;

import com.wx.manage.pojo.req.DataSourceCreateVo;
import com.wx.manage.pojo.req.DataSourceUpdateVo;
import com.wx.manage.pojo.resp.DataSourceResp;
import com.wx.manage.result.Result;
import com.wx.manage.service.SystemDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 数据源表	 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Slf4j
@Api(tags = "管理后台 - 数据源管理")
@RestController
@CrossOrigin
@RequestMapping("/system/data/source")
public class SystemDataSourceController {

    @Autowired
    private SystemDataSourceService dataSourceService;

    @PostMapping("/create")
    @ApiOperation(value = "创建数据源", notes = "创建数据源")
    public Result<Long> createDataSource(@Valid @RequestBody DataSourceCreateVo createReq) {
        return Result.success(dataSourceService.createDataSource(createReq));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新数据源配置", notes = "更新数据源配置")
    public Result<Boolean> updateDataSource(@Valid @RequestBody DataSourceUpdateVo updateReq) {
        return Result.success(dataSourceService.updateDataSource(updateReq));
    }

    @PutMapping("/delete")
    @ApiOperation(value = "删除数据源配置", notes = "删除数据源配置")
    public Result<Boolean> deleteDataSource(@RequestParam("id") Long id) {
        return Result.success(dataSourceService.deleteDataSource(id));
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获得数据源配置详情", notes = "获得数据源配置详情")
    public Result<DataSourceResp> getDataSourceDetail(@RequestParam("id") Long id) {
        return Result.success(dataSourceService.getDataSourceDetail(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获得数据源配置列表", notes = "获得数据源配置列表")
    public Result<List<DataSourceResp>> getDataSourceList() {
        return Result.success(dataSourceService.getDataSourceList());
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试数据源配置", notes = "测试数据源配置")
    public Result<Boolean> testDataSource(@RequestParam("id") Long id) {
        return Result.success(dataSourceService.testDataSource(id));
    }

}
