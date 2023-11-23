package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemTenantDataSource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 租户数据源关联表表	 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
public interface SystemTenantDataSourceService extends IService<SystemTenantDataSource> {


    /**
     * 删除指定租户的数据源关联关系
     * @param tenantId
     * @return
     */
    Boolean removeTenantDataSourceByTenantId(Long tenantId);

}
