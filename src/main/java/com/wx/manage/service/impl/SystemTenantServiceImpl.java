package com.wx.manage.service.impl;

import com.wx.manage.pojo.entity.SystemTenant;
import com.wx.manage.mapper.SystemTenantMapper;
import com.wx.manage.service.SystemTenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Service
public class SystemTenantServiceImpl extends ServiceImpl<SystemTenantMapper, SystemTenant> implements SystemTenantService {

}
