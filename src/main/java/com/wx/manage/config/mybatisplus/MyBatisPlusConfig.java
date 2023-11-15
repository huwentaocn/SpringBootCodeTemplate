package com.wx.manage.config.mybatisplus;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.wx.manage.config.dynamic.TenantDsProcessor;
import com.wx.manage.config.tenant.TenantDatabaseInterceptor;
import com.wx.manage.config.tenant.TenantProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description mybatis-plus配置
 * @Date 2023/4/10 15:30 星期一
 * @Author Hu Wentao
 */

@EnableConfigurationProperties(TenantProperties.class)
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor myBatisPlusInterceptor(TenantProperties properties) {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //多租户配置
        TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        //添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        return interceptor;
    }

    @Bean
    public DsProcessor dsProcessor(
//            TenantFrameworkService tenantFrameworkService,
//                                   DataSource dataSource,
//                                   DefaultDataSourceCreator dataSourceCreator
    ) {
//        TenantDsProcessor tenantDsProcessor = new TenantDsProcessor(tenantFrameworkService, dataSourceCreator);
        TenantDsProcessor tenantDsProcessor = new TenantDsProcessor();
        tenantDsProcessor.setNextProcessor(new DsSpelExpressionProcessor());

        return tenantDsProcessor;
    }
}
