package com.wx.manage.service;

import com.wx.manage.pojo.entity.WxProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.OperationProjectReq;
import com.wx.manage.result.Result;

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
