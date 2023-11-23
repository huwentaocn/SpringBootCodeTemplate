package com.wx.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.manage.config.tenant.TenantContextHolder;
import com.wx.manage.config.tenant.TenantProperties;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.constant.RoleCodeEnum;
import com.wx.manage.constant.RoleTypeEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.handler.tenant.TenantInfoHandler;
import com.wx.manage.handler.tenant.TenantMenuHandler;
import com.wx.manage.pojo.entity.*;
import com.wx.manage.mapper.SystemTenantMapper;
import com.wx.manage.pojo.excel.TenantExcelVo;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.*;
import com.wx.manage.pojo.resp.TenantResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.until.DateUtils;
import com.wx.manage.until.JdbcUtils;
import com.wx.manage.until.TenantUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
    @Lazy
    private SystemDataSourceService dataSourceService;

    @Autowired
    @Lazy // 避免循环依赖的报错
    private SystemUsersService usersService;

    @Autowired
    private SystemUserRoleService userRoleService;

    @Autowired
    private SystemTenantMapper tenantMapper;

    @Autowired
    private SystemTenantDataSourceService tenantDataSourceService;

    @Autowired
    private SystemMenuService menuService;

    @Override
    public TenantResp getTenantByName(String name) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemTenant::getName, name);
        SystemTenant systemTenant = getOne(queryWrapper);
        if (systemTenant == null) {
            return null;
        }

        TenantResp tenantResp = new TenantResp();
        BeanUtils.copyProperties(systemTenant, tenantResp);
        return tenantResp;
    }

    //TODO 明天要适配这个方法，改为用新的那套逻辑
    @Override
    @SneakyThrows
    public DataSourceProperty getDataSourceProperty(Long id) {
        // 获得租户对应的数据源配置
        LambdaQueryWrapper<SystemTenantDataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemTenantDataSource::getTenantId, id);
        SystemTenantDataSource tenantDataSource = tenantDataSourceService.getOne(queryWrapper);
        if (tenantDataSource == null) {
            throw new GlobalException(ResultCodeEnum.DATA_NOT_EXIST_FAIL, "租户未绑定数据源");
        }

        //获取数据源账号密码
        SystemDataSource dataSource = dataSourceService.getById(tenantDataSource.getDataSourceId());
        if (dataSource == null) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_EXISTS);
        }

        // 转换成 dynamic-datasource 配置
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(tenantDataSource.getDatabaseName());
        dataSourceProperty.setUrl(tenantDataSource.getUrl());
        dataSourceProperty.setUsername(dataSource.getUsername());
        dataSourceProperty.setPassword(dataSource.getPassword());

        return dataSourceProperty;
    }

//    //TODO 明天要适配这个方法，改为用新的那套逻辑
//    @Override
//    @SneakyThrows
//    public DataSourceProperty getDataSourceProperty(Long id) {
//        // 获得租户对应的数据源配置
//        SystemTenant tenant = getById(id);
//        if (tenant == null) {
//            return null;
//        }
//        InfraDataSourceConfig dataSourceConfig = dataSourceConfigService.getById(tenant.getDataSourceConfigId());
//        // 转换成 dynamic-datasource 配置
//        DataSourceProperty dataSourceProperty = new DataSourceProperty();
//        dataSourceProperty.setPoolName(dataSourceConfig.getName());
//        dataSourceProperty.setUrl(dataSourceConfig.getUrl());
//        dataSourceProperty.setUsername(dataSourceConfig.getUsername());
//        dataSourceProperty.setPassword(dataSourceConfig.getPassword());
//
//        return dataSourceProperty;
//    }

//    @Override
//    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
//    public Long createTenant(TenantCreateReq createReq) {
//        // 校验套餐被禁用
//        SystemTenantPackage tenantPackage = tenantPackageService.validTenantPackage(createReq.getPackageId());
//
//        //校验数据源是否正确
//        dataSourceConfigService.testDataSourceConfig(createReq.getDataSourceConfigId());
//
//        //创建租户
//        SystemTenant tenant = new SystemTenant();
//        BeanUtils.copyProperties(createReq, tenant);
//        saveOrUpdate(tenant);
//
//        TenantUtils.execute(tenant.getId(), () -> {
//            // 创建角色，并分配菜单
//            Long roleId = createRoleAndAssignRoleMenu(tenantPackage);
//            // 创建用户，并分配角色
//            Long userId = createUserAndAssignRole(roleId, createReq);
//            // 修改租户的管理员
//            tenant.setContactUserId(userId);
//            saveOrUpdate(tenant);
//        });
//        return tenant.getId();
//    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public Long createTenant(TenantCreateReq createReq) {
        //校验租户名称是否已经存在
        TenantResp tenantByName = getTenantByName(createReq.getName());
        if (tenantByName != null) {
            throw new GlobalException(ResultCodeEnum.TENANT_NAME_EXISTS, createReq.getName());
        }

        // 校验套餐被禁用
        SystemTenantPackage tenantPackage = tenantPackageService.validTenantPackage(createReq.getPackageId());

        //校验数据源是否正确
        dataSourceService.testDataSource(createReq.getDataSourceConfigId());

        //创建租户
        SystemTenant tenant = new SystemTenant();
        BeanUtils.copyProperties(createReq, tenant);
        saveOrUpdate(tenant);

        //创建数据库并初始化表
        createDatabaseAndInitTableStructure(createReq.getDataSourceConfigId(), tenant);

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
     * 创建租户库并且初始化表结构
     * @param dataSourceId
     * @param tenant
     * @return
     */
    private void createDatabaseAndInitTableStructure(Long dataSourceId, SystemTenant tenant) {
        SystemDataSource dataSource = dataSourceService.getById(dataSourceId);
        if (dataSource == null) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CONFIG_NOT_EXISTS);
        }

        String username = dataSource.getUsername();
        String password = dataSource.getPassword();
        String url = dataSource.getUrl();
        String dbName = "tenant-" + tenant.getId();
        String databaseUrl = url.replace("?", "/" + dbName + "?");

        boolean connectionOK = JdbcUtils.isConnectionOK(databaseUrl, username, password);
        if (connectionOK) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_DATABASE_ALREADY_EXISTS);
        }

        //创建租户库
        boolean createSuccess = JdbcUtils.createDatabase(url, username, password, dbName);
        if (!createSuccess) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_CREATE_DATABASE_ERROR);
        }

        //存关联关系
        SystemTenantDataSource tenantDataSource = new SystemTenantDataSource();
        tenantDataSource.setTenantId(tenant.getId());
        tenantDataSource.setDataSourceId(dataSourceId);
        tenantDataSource.setDatabaseName(dbName);

        tenantDataSource.setUrl(databaseUrl);
        tenantDataSource.setType(1);
        tenantDataSourceService.saveOrUpdate(tenantDataSource);

        //初始化表结构
        String filePath = this.getClass().getClassLoader().getResource("sql/init_table.sql").getPath();
        boolean initSuccess = JdbcUtils.executeSqlFile(filePath, databaseUrl, username, password);
        if (!initSuccess) {
            throw new GlobalException(ResultCodeEnum.DATA_SOURCE_INIT_TABLE_STRUCT_ERROR);
        }
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

        //校验租户名称是否已经存在
        TenantResp tenantByName = getTenantByName(updateReq.getName());
        if (tenantByName != null && !tenant.getId().equals(tenantByName.getId())) {
            throw new GlobalException(ResultCodeEnum.TENANT_NAME_EXISTS, updateReq.getName());
        }

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

        //删除数据源关联关系
        tenantDataSourceService.removeTenantDataSourceByTenantId(id);

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
    public void handleTenantMenu(TenantMenuHandler handler) {
        // 如果禁用，则不执行逻辑
        if (isTenantDisable()) {
            return;
        }
        // 获得租户，然后获得菜单
        SystemTenant tenant = getById(TenantContextHolder.getRequiredTenantId());
        Set<Long> menuIds;
        if (isSystemTenant(tenant)) { // 系统租户，菜单是全量的
            menuIds = menuService.list().stream().map(SystemMenu::getId).collect(Collectors.toSet());
        } else {
            menuIds = tenantPackageService.getById(tenant.getPackageId()).getMenuIds();
        }
        // 执行处理器
        handler.handle(menuIds);
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

    /**
     * 校验租户名称是否已存在
     * @param name
     * @return
     */
    @Override
    public Boolean validateTenantNameIsExist(String name) {
        LambdaQueryWrapper<SystemTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(name), SystemTenant::getName, name);
        SystemTenant tenant = getOne(queryWrapper);

        return tenant != null;
    }

    private boolean isTenantDisable() {
        return tenantProperties == null || Boolean.FALSE.equals(tenantProperties.getEnable());
    }

    private static boolean isSystemTenant(SystemTenant tenant) {
        return Objects.equals(tenant.getPackageId(), SystemTenant.PACKAGE_ID_SYSTEM);
    }

}

