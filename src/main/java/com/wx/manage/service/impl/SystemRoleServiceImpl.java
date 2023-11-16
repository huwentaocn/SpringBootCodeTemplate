package com.wx.manage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.annotations.VisibleForTesting;
import com.wx.manage.tenant.aop.TenantDS;
import com.wx.manage.constant.DataScopeEnum;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.constant.RoleCodeEnum;
import com.wx.manage.constant.RoleTypeEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.SystemRole;
import com.wx.manage.mapper.SystemRoleMapper;
import com.wx.manage.pojo.req.RoleCreateReq;
import com.wx.manage.pojo.req.RoleUpdateReq;
import com.wx.manage.pojo.resp.RoleResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemRoleMenuService;
import com.wx.manage.service.SystemRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.service.SystemUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
@TenantDS
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    @Autowired
    private SystemUserRoleService userRoleService;

    @Autowired
    private SystemRoleMenuService roleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateReq createReq, Integer type) {
        // 校验角色
        validateRoleDuplicate(createReq.getName(), createReq.getCode(), null);
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(createReq, systemRole);
        systemRole.setType(ObjectUtil.defaultIfNull(type, RoleTypeEnum.CUSTOM.getType()));
        systemRole.setStatus(EnableStatusEnum.ENABLE.getStatus());
        systemRole.setDataScope(DataScopeEnum.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限

        saveOrUpdate(systemRole);

        return systemRole.getId();
    }

    @Override
    public Boolean updateRole(RoleUpdateReq updateReq) {
        // 校验是否可以更新
        validateRoleForUpdate(updateReq.getId());
        // 校验角色的唯一字段是否重复
        validateRoleDuplicate(updateReq.getName(), updateReq.getCode(), updateReq.getId());

        // 更新到数据库
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(updateReq, systemRole);

        return saveOrUpdate(systemRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRole(Long id) {
        // 校验是否可以更新
        validateRoleForUpdate(id);
        // 删除相关数据
        userRoleService.removeListByRoleId(id);
        roleMenuService.removeListByRoleId(id);
        // 标记删除
        return removeById(id);
    }

    @Override
    public RoleResp getRoleDetail(Long id) {
        SystemRole systemRole = getById(id);

        RoleResp roleResp = new RoleResp();
        BeanUtils.copyProperties(systemRole, roleResp);
        return roleResp;
    }

    @Override
    public List<RoleResp> getRoleList() {
        List<SystemRole> systemRoleList = list();

        return systemRoleList.stream().map(item -> {
            RoleResp roleResp = new RoleResp();
            BeanUtils.copyProperties(item, roleResp);
            return roleResp;
        }).collect(Collectors.toList());
    }


    /**
     * 校验角色的唯一字段是否重复
     *
     * 1. 是否存在相同名字的角色
     * 2. 是否存在相同编码的角色
     *
     * @param name 角色名字
     * @param code 角色额编码
     * @param id 角色编号
     */
    void validateRoleDuplicate(String name, String code, Long id) {
        // 0. 超级管理员，不允许创建
        if (RoleCodeEnum.isSuperAdmin(code)) {
            throw new GlobalException(ResultCodeEnum.ROLE_ADMIN_CODE_ERROR, code);
        }

        // 1. 该 name 名字被其它角色所使用
        LambdaQueryWrapper<SystemRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(name), SystemRole::getName, name);
        SystemRole role = getOne(queryWrapper);
        if (role != null && !role.getId().equals(id)) {
            throw new GlobalException(ResultCodeEnum.ROLE_NAME_DUPLICATE, name);
        }
        // 2. 是否存在相同编码的角色
        if (StringUtils.isBlank(code)) {
            return;
        }
        // 该 code 编码被其它角色所使用
        LambdaQueryWrapper<SystemRole> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(StringUtils.isNotBlank(code), SystemRole::getCode, code);
        SystemRole systemRole = getOne(roleLambdaQueryWrapper);
        if (systemRole != null && !systemRole.getId().equals(id)) {
            throw new GlobalException(ResultCodeEnum.ROLE_CODE_DUPLICATE, code);
        }
    }


    /**
     * 校验角色是否可以被更新
     *
     * @param id 角色编号
     */
    @VisibleForTesting
    void validateRoleForUpdate(Long id) {
        SystemRole systemRole = getById(id);
        if (systemRole == null) {
            throw new GlobalException(ResultCodeEnum.ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (RoleTypeEnum.SYSTEM.getType().equals(systemRole.getType())) {
            throw new GlobalException(ResultCodeEnum.ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

}
