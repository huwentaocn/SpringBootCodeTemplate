package com.wx.manage.controller;

import com.wx.manage.exception.BindingResultResponse;
import com.wx.manage.pojo.entity.WxProject;
import com.wx.manage.pojo.req.OperationProjectReq;
import com.wx.manage.result.Result;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.WxProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */

@Slf4j
@Api(tags = "项目管理模块")
@RestController
@CrossOrigin
@RequestMapping("/wxProject")
public class WxProjectController {

    @Autowired
    private WxProjectService wxProjectService;

    @ApiOperation(value = "查询所有项目", notes = "查询可管理的所有的项目列表")
    @GetMapping("/get/all/project")
    public Result<List<WxProject>> getAllProject() {
        List<WxProject> list = wxProjectService.list();

        return Result.success(list);
    }


    @ApiOperation(value = "添加项目")
    @PostMapping("/add/project")
    public Result addProject(@RequestBody OperationProjectReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(ResultCodeEnum.PARAM_FAIL, BindingResultResponse.getError(bindingResult));
        }

        return wxProjectService.addProject(req);
    }

}
