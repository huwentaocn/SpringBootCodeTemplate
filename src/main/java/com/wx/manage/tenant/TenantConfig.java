package com.wx.manage.tenant;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.wx.manage.tenant.aop.TenantIgnoreAspect;
import com.wx.manage.tenant.aop.TenantDsProcessor;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.service.TenantFrameworkService;
import com.wx.manage.service.impl.TenantFrameworkServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多租户自动配置类
 * @Date 2023/11/9 10:14 星期四
 * @Author Hu Wentao
 */

@AutoConfiguration
@ConditionalOnProperty(prefix = "scct.tenant", value = "enable", matchIfMissing = true) // 允许使用 yudao.tenant.enable=false 禁用多租户
@EnableConfigurationProperties(TenantProperties.class)
public class TenantConfig {

    @Bean
    public TenantFrameworkService tenantFrameworkService(SystemTenantService tenantService) {
        return new TenantFrameworkServiceImpl(tenantService);
    }

    // ========== AOP ==========

    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // ========== DB ==========

    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties properties,
                                                                 MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        List<InnerInterceptor> inners = new ArrayList<>(interceptor.getInterceptors());
        inners.add(0, inner);
        interceptor.setInterceptors(inners);
        return inner;
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
