package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemDataSource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.DataSourceConfigCreateVo;
import com.wx.manage.pojo.req.DataSourceCreateVo;

/**
 * <p>
 * 数据源表	 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
public interface SystemDataSourceService extends IService<SystemDataSource> {

    Long createDataSourceConfig(DataSourceCreateVo createReq);

    Boolean testDataSource(Long id);
}
