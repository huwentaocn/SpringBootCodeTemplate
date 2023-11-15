package com.wx.manage.handler.tenant;

import com.wx.manage.pojo.entity.SystemTenant;


/**
 * 租户信息处理
 * 目的：尽量减少租户逻辑耦合到系统中
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
public interface TenantInfoHandler {

    /**
     * 基于传入的租户信息，进行相关逻辑的执行
     * 例如说，创建用户时，超过最大账户配额
     *
     * @param tenant 租户信息
     */
    void handle(SystemTenant tenant);

}