package com.wx.manage.service;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;

import java.util.List;

/**
 * @Description tenant框架
 * @Date 2023/11/14 15:54 星期二
 * @Author Hu Wentao
 */
public interface TenantFrameworkService {


    /**
     * 获得所有租户
     *
     * @return 租户编号数组
     */
    List<Long> getTenantIds();

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validTenant(Long id);

    /**
     * 获得租户对应的数据源配置
     *
     * @param id 租户编号
     * @return 数据源配置
     */
    DataSourceProperty getDataSourceProperty(Long id);
}
