package com.wx.manage.service.impl;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.InfraDataSourceConfig;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.service.TenantFrameworkService;
import com.wx.manage.until.CacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * @Description tenant框架实现
 * @Date 2023/11/14 15:54 星期二
 * @Author Hu Wentao
 */
@RequiredArgsConstructor
@Service
public class TenantFrameworkServiceImpl implements TenantFrameworkService {

    private static final GlobalException SERVICE_EXCEPTION_NULL = new GlobalException();

    private final SystemTenantService tenantService;

    /**
     * 针对 {@link #getTenantIds()} 的缓存
     */
    private final LoadingCache<Object, List<Long>> getTenantIdsCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Object, List<Long>>() {

                @Override
                public List<Long> load(Object key) {
                    return tenantService.getTenantIdList();
                }

            });

    /**
     * 针对 {@link #validTenant(Long)} 的缓存
     */
    private final LoadingCache<Long, GlobalException> validTenantCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Long, GlobalException>() {

                @Override
                public GlobalException load(Long id) {
                    try {
                        tenantService.validateTenant(id);
                        return SERVICE_EXCEPTION_NULL;
                    } catch (GlobalException ex) {
                        return ex;
                    }
                }

            });

    /**
     * 针对 {@link #getDataSourceProperty(Long)} 的缓存
     */
    private final LoadingCache<Long, DataSourceProperty> dataSourcePropertyCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<Long, DataSourceProperty>() {

                @Override
                public DataSourceProperty load(Long id) {
                    // 获得租户对应的数据源配置
                    DataSourceProperty dataSourceConfig = tenantService.getDataSourceProperty(id);
                    if (dataSourceConfig == null) {
                        return null;
                    }
                    // 转换成 dynamic-datasource 配置
                    return dataSourceConfig;
                }

            });

    @Override
    @SneakyThrows
    public List<Long> getTenantIds() {
        return getTenantIdsCache.get(Boolean.TRUE);
    }

    @Override
    public void validTenant(Long id) {
        GlobalException globalException = validTenantCache.getUnchecked(id);
        if (globalException != SERVICE_EXCEPTION_NULL) {
            throw globalException;
        }
    }

    @Override
    @SneakyThrows
    public DataSourceProperty getDataSourceProperty(Long id) {
        return dataSourcePropertyCache.get(id);
    }


}
