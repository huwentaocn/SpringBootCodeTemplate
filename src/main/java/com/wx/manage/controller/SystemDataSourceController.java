package com.wx.manage.controller;

import com.wx.manage.pojo.req.DataSourceConfigCreateVo;
import com.wx.manage.pojo.req.DataSourceCreateVo;
import com.wx.manage.result.Result;
import com.wx.manage.service.InfraDataSourceConfigService;
import com.wx.manage.service.SystemDataSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 数据源表	 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Slf4j
@Api(tags = "数据源管理模块1")
@RestController
@CrossOrigin
@RequestMapping("/system/data/source")
public class SystemDataSourceController {

    @Autowired
    private SystemDataSourceService dataSourceService;

    @PostMapping("/create")
    @ApiOperation(value = "创建数据源", notes = "创建数据源")
    public Result<Long> createDataSource(@Valid @RequestBody DataSourceCreateVo createReq) {
        return Result.success(dataSourceService.createDataSourceConfig(createReq));
    }

    @GetMapping("/test")
    @ApiOperation(value = "测试数据源配置", notes = "测试数据源配置")
    public Result<Boolean> testDataSourceConfig(@RequestParam("id") Long id) {
        return Result.success(dataSourceService.testDataSource(id));
    }

}
