package com.wx.manage.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Date 2023/11/15 13:39 星期三
 * @Author Hu Wentao
 */

@Data
public abstract class TenantBaseDO {

    /**
     * 多租户编号
     */
    private Long tenantId;

}
