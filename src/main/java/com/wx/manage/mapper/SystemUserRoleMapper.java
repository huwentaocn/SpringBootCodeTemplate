package com.wx.manage.mapper;

import com.wx.manage.config.tenant.aop.TenantDS;
import com.wx.manage.pojo.entity.SystemUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Mapper
@TenantDS
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {

}
