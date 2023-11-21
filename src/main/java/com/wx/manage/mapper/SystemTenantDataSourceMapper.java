package com.wx.manage.mapper;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.wx.manage.pojo.entity.SystemTenantDataSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 租户数据源关联表表	 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Mapper
@Master
public interface SystemTenantDataSourceMapper extends BaseMapper<SystemTenantDataSource> {

}
