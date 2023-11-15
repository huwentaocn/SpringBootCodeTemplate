package com.wx.manage.mapper;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.wx.manage.pojo.entity.SystemTenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 租户表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Mapper
@Master
public interface SystemTenantMapper extends BaseMapper<SystemTenant> {

}
