package com.hwt.sbct.controller.tool;

import com.hwt.sbct.pojo.resp.LocationPointResp;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.service.BaiduService;
import com.hwt.sbct.service.GaodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 调用高德接口
 * @Date 2024/12/11 15:52 星期三
 * @Author Hu Wentao
 */

@Api(tags = "高德接口管理模块")
@CrossOrigin
@RestController
@RequestMapping("/gaode")
public class GaodeController {

    @Resource
    private GaodeService gaodeService;

    @ApiOperation(value = "获取地址经纬度", notes = "获取地址经纬度")
    @PostMapping("/hero/get/location/point")
    public Result<LocationPointResp> getLocationPoint(@RequestParam("location")String location) {

        return Result.success(gaodeService.getLocationPoint(location));
    }


}
