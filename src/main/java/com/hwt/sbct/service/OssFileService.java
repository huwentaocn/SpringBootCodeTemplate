package com.hwt.sbct.service;

import com.hwt.sbct.pojo.entity.OssFile;
import com.hwt.sbct.pojo.vo.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwt.sbct.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * oss文件表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-31
 */
public interface OssFileService extends IService<OssFile> {

    Result<FileInfo> uploadFile(MultipartFile file);
}
