package com.hwt.sbct.controller.tool;

import com.hwt.sbct.pojo.resp.LocationPointResp;
import com.hwt.sbct.pojo.vo.FileInfo;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.service.BaiduService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description 调用百度接口
 * @Date 2024/12/11 15:52 星期三
 * @Author Hu Wentao
 */

@Api(tags = "菜单管理模块")
@CrossOrigin
@RestController
@RequestMapping("/baidu")
public class BaiduController {

    @Resource
    private BaiduService baiduService;

    @ApiOperation(value = "获取地址经纬度", notes = "获取地址经纬度")
    @PostMapping("/hero/get/location/point")
    public Result<LocationPointResp> getLocationPoint(@RequestParam("location")String location) {

        return Result.success(baiduService.getLocationPoint(location));
    }


}
