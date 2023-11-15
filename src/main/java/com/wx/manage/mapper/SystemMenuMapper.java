package com.wx.manage.mapper;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.wx.manage.config.dynamic.TenantDS;
import com.wx.manage.pojo.entity.SystemMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Mapper
@Master
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

}
