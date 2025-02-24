package com.hwt.sbct.service;

import com.hwt.sbct.pojo.entity.WxProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwt.sbct.pojo.req.OperationProjectReq;
import com.hwt.sbct.result.Result;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-24
 */
public interface WxProjectService extends IService<WxProject> {

    Result addProject(OperationProjectReq req);
}
