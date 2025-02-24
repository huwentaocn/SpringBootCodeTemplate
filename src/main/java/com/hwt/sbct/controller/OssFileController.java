package com.hwt.sbct.controller;

import com.hwt.sbct.pojo.vo.FileInfo;
import com.hwt.sbct.result.Result;
import com.hwt.sbct.service.OssFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * oss文件表 前端控制器
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-31
 */

@Slf4j
@Api(tags = "oss文件管理模块")
@CrossOrigin
@RestController
@RequestMapping("/ossFile")
public class OssFileController {

    @Autowired
    private OssFileService ossFileService;

    @ApiOperation(value = "oss文件上传", notes = "oss文件上传")
    @PostMapping("/upload/file")
    public Result<FileInfo> uploadFile(MultipartFile file) {

        return ossFileService.uploadFile(file);
    }

}
