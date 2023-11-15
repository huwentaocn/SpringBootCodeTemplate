package com.wx.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wx.manage.config.tenant.TenantContextHolder;
import com.wx.manage.config.tenant.TenantProperties;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.constant.RoleCodeEnum;
import com.wx.manage.constant.RoleTypeEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.handler.tenant.TenantInfoHandler;
import com.wx.manage.pojo.entity.InfraDataSourceConfig;
import com.wx.manage.pojo.entity.SystemRole;
import com.wx.manage.pojo.entity.SystemTenant;
import com.wx.manage.mapper.SystemTenantMapper;
import com.wx.manage.pojo.entity.SystemTenantPackage;
import com.wx.manage.pojo.excel.TenantExcelVo;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.*;
import com.wx.manage.pojo.resp.TenantResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.until.DateUtils;
import com.wx.manage.until.TenantUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Service
@Slf4j
public class SystemTenantServiceImpl extends ServiceImpl<SystemTenantMapper, SystemTenant> implements SystemTenantService {

    @Autowired(required = false) // 由于tenant.enable 配置项，可以关闭多租户的功能，所以这里只能不强制注入
    private TenantProperties tenantProperties;

    @Autowired
    private SystemRoleService roleService;

    @Autowired
    private SystemRoleMenuService roleMenuService;

    @Autowired
    @Lazy // 避免循环依赖的报错
    private SystemTenantPackageService tenantPackageService;

    @Autowired
    @Lazy // 避免循环依赖的报错
    private InfraDataSourceConfigService dataSourceConfigService;

    @Autowired
    @Lazy // 避免循环依赖的报错
    private SystemUsersService usersService;

    @Autowired
    private SystemUserRoleService userRoleService;

    @Autowired
    private SystemTenantMapper tenantMapper;

    @Override
    public TenantResp getTenantByName(String name) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemTenant::getName, name);
        SystemTenant systemTenant = getOne(queryWrapper);

        TenantResp tenantResp = new TenantResp();
        BeanUtils.copyProperties(systemTenant, tenantResp);
        return tenantResp;
    }

    @Override
    @SneakyThrows
    public DataSourceProperty getDataSourceProperty(Long id) {
        // 获得租户对应的数据源配置
        SystemTenant tenant = getById(id);
        if (tenant == null) {
            return null;
        }
        InfraDataSourceConfig dataSourceConfig = dataSourceConfigService.getById(tenant.getDataSourceConfigId());
        // 转换成 dynamic-datasource 配置
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(dataSourceConfig.getName());
        dataSourceProperty.setUrl(dataSourceConfig.getUrl());
        dataSourceProperty.setUsername(dataSourceConfig.getUsername());
        dataSourceProperty.setPassword(dataSourceConfig.getPassword());

        return dataSourceProperty;
    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public Long createTenant(TenantCreateReq createReq) {
        // 校验套餐被禁用
        SystemTenantPackage tenantPackage = tenantPackageService.validTenantPackage(createReq.getPackageId());

        //创建租户
        SystemTenant tenant = new SystemTenant();
        BeanUtils.copyProperties(createReq, tenant);
        saveOrUpdate(tenant);

        TenantUtils.execute(tenant.getId(), () -> {
            // 创建角色，并分配菜单
            Long roleId = createRoleAndAssignRoleMenu(tenantPackage);
            // 创建用户，并分配角色
            Long userId = createUserAndAssignRole(roleId, createReq);
            // 修改租户的管理员
            tenant.setContactUserId(userId);
            saveOrUpdate(tenant);
        });
        return tenant.getId();
    }

    /**
     * 创建角色，并分配菜单
     *
     * @param tenantPackage
     * @return
     */
    private Long createRoleAndAssignRoleMenu(SystemTenantPackage tenantPackage) {
        RoleCreateReq roleCreateReq = new RoleCreateReq();
        roleCreateReq.setName(RoleCodeEnum.TENANT_ADMIN.getName());
        roleCreateReq.setCode(RoleCodeEnum.TENANT_ADMIN.getCode());
        roleCreateReq.setSort(0);
        roleCreateReq.setRemark("系统自动生成");
        Long roleId = roleService.createRole(roleCreateReq, RoleTypeEnum.SYSTEM.getType());
        //分配权限
        roleMenuService.assignRoleMenu(roleId, tenantPackage.getMenuIds());
        return roleId;
    }

    /**
     * 创建用户，并分配角色
     *
     * @param roleId
     * @param tenantCreateReq
     * @return
     */
    private Long createUserAndAssignRole(Long roleId, TenantCreateReq tenantCreateReq) {
        // 创建用户
        UserCreateReq userCreateReq = new UserCreateReq();
        userCreateReq.setUsername(tenantCreateReq.getUsername());
        userCreateReq.setPassword(tenantCreateReq.getPassword());
        userCreateReq.setNickname(tenantCreateReq.getContactName());
        userCreateReq.setMobile(tenantCreateReq.getContactMobile());
        Long userId = usersService.createUser(userCreateReq);
        // 分配角色
        userRoleService.assignUserRole(userId, singleton(roleId));
        return userId;
    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public Boolean updateTenant(TenantUpdateReq updateReq) {
        // 校验存在
        SystemTenant tenant = validateUpdateTenant(updateReq.getId());
        // 校验套餐被禁用
        SystemTenantPackage tenantPackage = tenantPackageService.validTenantPackage(updateReq.getPackageId());

        // 如果套餐发生变化，则修改其角色的权限
        if (ObjectUtil.notEqual(tenant.getPackageId(), updateReq.getPackageId())) {
            updateTenantRoleMenu(tenant.getId(), tenantPackage.getMenuIds());
        }

        //更新租户
        BeanUtils.copyProperties(updateReq, tenant);
        return saveOrUpdate(tenant);
    }

    @Override
    public Boolean deleteTenant(Long id) {
        // 校验存在
        validateUpdateTenant(id);
        // 删除
        return removeById(id);
    }

    @Override
    public TenantResp getTenantDetail(Long id) {
        SystemTenant tenant = getById(id);
        TenantResp tenantResp = new TenantResp();
        BeanUtils.copyProperties(tenant, tenantResp);
        return tenantResp;
    }

    @Override
    public List<TenantResp> getTenantList() {
        List<SystemTenant> systemTenantList = list();
        return systemTenantList.stream().map(item -> {
            TenantResp tenantResp = new TenantResp();
            BeanUtils.copyProperties(item, tenantResp);
            return tenantResp;
        }).collect(Collectors.toList());
    }

    @Override
    public List<TenantExcelVo> getTenantList(TenantExportReq req) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(req.getName()), SystemTenant::getName, req.getName())
                .like(StringUtils.isNotBlank(req.getContactName()), SystemTenant::getContactName, req.getContactName())
                .like(StringUtils.isNotBlank(req.getContactMobile()), SystemTenant::getContactMobile, req.getContactMobile())
                .eq(req.getStatus() != null, SystemTenant::getStatus, req.getStatus())
                .between(req.getCreateTime() != null, SystemTenant::getCreateTime, req.getCreateTime()[0], req.getCreateTime()[1])
                .orderByDesc(SystemTenant::getId);
        List<SystemTenant> systemTenantList = list(queryWrapper);
        return systemTenantList.stream().map(item -> {
            TenantExcelVo tenantExcelVo = new TenantExcelVo();
            BeanUtils.copyProperties(item, tenantExcelVo);
            return tenantExcelVo;
        }).collect(Collectors.toList());
    }


    @Override
    public List<SystemTenant> getTenantListByPackageId(Long packageId) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemTenant::getPackageId, packageId);
        return list(queryWrapper);
    }

    @Override
    public PageResult<TenantResp> getTenantPage(TenantPageReq req) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(req.getName()), SystemTenant::getName, req.getName())
                .like(StringUtils.isNotBlank(req.getContactName()), SystemTenant::getContactName, req.getContactName())
                .like(StringUtils.isNotBlank(req.getContactMobile()), SystemTenant::getContactMobile, req.getContactMobile())
                .eq(req.getStatus() != null, SystemTenant::getStatus, req.getStatus())
//                .between(req.getCreateTime() != null, SystemTenant::getCreateTime, req.getCreateTime()[0], req.getCreateTime()[1])
                .orderByDesc(SystemTenant::getId);

        Page<SystemTenant> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<SystemTenant> systemTenantPage = tenantMapper.selectPage(page, queryWrapper);
        List<TenantResp> tenantRespList = systemTenantPage.getRecords().stream().map(item -> {
            TenantResp tenantResp = new TenantResp();
            BeanUtils.copyProperties(item, tenantResp);
            return tenantResp;
        }).collect(Collectors.toList());

        return new PageResult<>(systemTenantPage, tenantRespList);
    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds) {
        // 获取所有角色
        List<SystemRole> roleList = roleService.list();
        roleList.forEach(role -> Assert.isTrue(tenantId.equals(role.getTenantId()), "角色({}/{}) 租户不匹配",
                role.getId(), role.getTenantId(), tenantId)); // 兜底校验

        //重新分配每个角色的权限
        roleList.forEach(role -> {
            // 如果是租户管理员，重新分配其权限为租户套餐的权限
            if (Objects.equals(role.getCode(), RoleCodeEnum.TENANT_ADMIN.getCode())) {
                roleMenuService.assignRoleMenu(role.getId(), menuIds);
                log.info("[updateTenantRoleMenu][租户管理员({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), menuIds);
                return;
            }
            // 如果是其他角色，则去掉超过套餐的权限
            List<Long> menuIdList = roleMenuService.getMenIdListByRoleId(role.getId());
            Set<Long> roleMenuIds = CollUtil.intersectionDistinct(menuIdList, menuIds);
            roleMenuService.assignRoleMenu(role.getId(), roleMenuIds);
            log.info("[updateTenantRoleMenu][角色({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), roleMenuIds);
        });
    }

    /**
     * 校验租户
     *
     * @param id
     * @return
     */
    private SystemTenant validateUpdateTenant(Long id) {
        SystemTenant tenant = getById(id);
        if (tenant == null) {
            throw new GlobalException(ResultCodeEnum.TENANT_NOT_EXISTS);
        }
        // 内置租户，不允许删除
        if (isSystemTenant(tenant)) {
            throw new GlobalException(ResultCodeEnum.TENANT_CAN_NOT_UPDATE_SYSTEM);
        }
        return tenant;
    }

    @Override
    public void handleTenantInfo(TenantInfoHandler handler) {
        // 如果禁用，则不执行逻辑
        if (isTenantDisable()) {
            return;
        }
        // 获得租户
        SystemTenant tenant = getById(TenantContextHolder.getRequiredTenantId());
        // 执行处理器
        handler.handle(tenant);
    }

    @Override
    public List<Long> getTenantIdList() {
        List<SystemTenant> systemTenantList = list();

        return systemTenantList.stream().map(SystemTenant::getId).collect(Collectors.toList());
    }

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    @Override
    public void validateTenant(Long id) {
        SystemTenant tenant = getById(id);
        if (tenant == null) {
            throw new GlobalException(ResultCodeEnum.TENANT_NOT_EXISTS);
        }
        if (tenant.getStatus().equals(EnableStatusEnum.DISABLE.getStatus())) {
            throw new GlobalException(ResultCodeEnum.TENANT_DISABLE, tenant.getName());
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw new GlobalException(ResultCodeEnum.TENANT_EXPIRE, tenant.getName());
        }
    }

    private boolean isTenantDisable() {
        return tenantProperties == null || Boolean.FALSE.equals(tenantProperties.getEnable());
    }

    private static boolean isSystemTenant(SystemTenant tenant) {
        return Objects.equals(tenant.getPackageId(), SystemTenant.PACKAGE_ID_SYSTEM);
    }

}

