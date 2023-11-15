package com.wx.manage.mapper;

import com.wx.manage.config.dynamic.TenantDS;
import com.wx.manage.pojo.entity.SystemUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Mapper
@TenantDS
public interface SystemUsersMapper extends BaseMapper<SystemUsers> {

}
