package com.wx.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wx.manage.pojo.entity.SystemTenantDataSource;
import com.wx.manage.mapper.SystemTenantDataSourceMapper;
import com.wx.manage.service.SystemTenantDataSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户数据源关联表表	 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Service
public class SystemTenantDataSourceServiceImpl extends ServiceImpl<SystemTenantDataSourceMapper, SystemTenantDataSource> implements SystemTenantDataSourceService {


    @Override
    public Boolean removeTenantDataSourceByTenantId(Long tenantId) {
        LambdaUpdateWrapper<SystemTenantDataSource> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(tenantId != null, SystemTenantDataSource::getTenantId, tenantId);
        return remove(wrapper);
    }
}
