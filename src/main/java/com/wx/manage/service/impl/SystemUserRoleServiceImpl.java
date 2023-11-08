package com.wx.manage.service.impl;

import com.wx.manage.pojo.entity.SystemUserRole;
import com.wx.manage.mapper.SystemUserRoleMapper;
import com.wx.manage.service.SystemUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRole> implements SystemUserRoleService {

}
