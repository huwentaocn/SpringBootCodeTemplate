package com.wx.manage.config.dynamic;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.wx.manage.config.tenant.TenantContextHolder;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.service.TenantFrameworkService;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * 基于 {@link TenantDS} 的数据源处理器
 *
 * 1. 如果有 @TenantDS 注解，返回该租户的数据源
 * 2. 如果该租户的数据源未创建，则进行创建
 *
 * @author Hu Wentao
 */
@RequiredArgsConstructor
public class TenantDsProcessor extends DsProcessor {

    /**
     * 用于获取租户数据源配置的 Service
     */
    @Resource
    @Lazy
    private TenantFrameworkService tenantFrameworkService;

    /**
     * 动态数据源
     */
    @Resource
    @Lazy  // 为什么添加 @Lazy 注解？因为它和 DynamicRoutingDataSource 相互依赖，导致无法初始化
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 用于创建租户数据源的 Creator
     */
    @Resource
    @Lazy
    private DefaultDataSourceCreator dataSourceCreator;

    @Override
    public boolean matches(String key) {
        return Objects.equals(key, TenantDS.KEY);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        // 获得数据源配置
        Long tenantId = TenantContextHolder.getRequiredTenantId();
        DataSourceProperty dataSourceProperty = tenantFrameworkService.getDataSourceProperty(tenantId);
        // 创建 or 创建数据源，并返回数据源名字
        return createDatasourceIfAbsent(dataSourceProperty);
    }

    private String createDatasourceIfAbsent(DataSourceProperty dataSourceProperty) {
        // 1. 重点：如果数据源不存在，则进行创建
        if (isDataSourceNotExist(dataSourceProperty)) {
            // 问题一：为什么要加锁？因为，如果多个线程同时执行到这里，会导致多次创建数据源
            // 问题二：为什么要使用 poolName 加锁？保证多个不同的 poolName 可以并发创建数据源
            // 问题三：为什么要使用 intern 方法？因为，intern 方法，会返回一个字符串的常量池中的引用
            // intern 的说明，可见 https://www.cnblogs.com/xrq730/p/6662232.html 文章
            synchronized (dataSourceProperty.getPoolName().intern()) {
                if (isDataSourceNotExist(dataSourceProperty)) {
                    DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
                    dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
                }
            }
        }
        // 2. 返回数据源的名字
        return dataSourceProperty.getPoolName();
    }

    private boolean isDataSourceNotExist(DataSourceProperty dataSourceProperty) {
        return !dynamicRoutingDataSource.getDataSources().containsKey(dataSourceProperty.getPoolName());
    }

}
