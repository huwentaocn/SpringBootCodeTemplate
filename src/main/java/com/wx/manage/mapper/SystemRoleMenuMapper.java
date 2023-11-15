package com.wx.manage.mapper;

import com.wx.manage.config.dynamic.TenantDS;
import com.wx.manage.pojo.entity.SystemRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Mapper
@TenantDS
public interface SystemRoleMenuMapper extends BaseMapper<SystemRoleMenu> {

}
