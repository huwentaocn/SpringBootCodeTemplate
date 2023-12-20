package com.wx.manage.config.tenant.aop;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * 使用租户所在的数据源
 *
 * 使用方式：当我们希望一个表使用租户所在的数据源，可以在该表的 Mapper 上添加该注解
 *
 * @author Hu Wentao
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS(TenantDS.KEY)
public @interface TenantDS {

    /**
     * 租户对应的数据源的占位符
     */
    String KEY = "hasProperty(#context, 'tenantId') ? #context.tenantId : ''";

}