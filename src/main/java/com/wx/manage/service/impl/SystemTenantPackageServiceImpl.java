package com.wx.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.entity.SystemTenant;
import com.wx.manage.pojo.entity.SystemTenantPackage;
import com.wx.manage.mapper.SystemTenantPackageMapper;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.TenantPackageCreateReq;
import com.wx.manage.pojo.req.TenantPackagePageReq;
import com.wx.manage.pojo.req.TenantPackageUpdateReq;
import com.wx.manage.pojo.resp.TenantPackageResp;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemTenantPackageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.service.SystemTenantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 租户套餐表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Service
public class SystemTenantPackageServiceImpl extends ServiceImpl<SystemTenantPackageMapper, SystemTenantPackage> implements SystemTenantPackageService {

    @Autowired
    private SystemTenantService tenantService;

    @Autowired
    private SystemTenantPackageMapper tenantPackageMapper;

    @Override
    public Long createTenantPackage(TenantPackageCreateReq createReq) {
        SystemTenantPackage systemTenant = new SystemTenantPackage();
        BeanUtils.copyProperties(createReq, systemTenant);
        saveOrUpdate(systemTenant);

        return systemTenant.getId();
    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public Boolean updateTenantPackage(TenantPackageUpdateReq updateReq) {
        //校验是否存在
        SystemTenantPackage tenantPackage = validateTenantPackageExists(updateReq.getId());

        //如果菜单发生变化，则修改每个租户的菜单
        if (!CollUtil.isEqualList(updateReq.getMenuIds(), tenantPackage.getMenuIds())) {
            List<SystemTenant> tenantList = tenantService.getTenantListByPackageId(tenantPackage.getId());
            tenantList.forEach(tenant -> tenantService.updateTenantRoleMenu(tenant.getId(), updateReq.getMenuIds()));
        }

        //更新数据库
        BeanUtils.copyProperties(updateReq, tenantPackage);
        return saveOrUpdate(tenantPackage);
    }

    @Override
    public Boolean deleteTenantPackage(Long id) {
        // 校验存在
        validateTenantPackageExists(id);
        // 校验正在使用
        validateTenantUsed(id);
        //删除
        return removeById(id);
    }

    @Override
    public TenantPackageResp getTenantPackageDetail(Long id) {
        SystemTenantPackage tenantPackage = getById(id);

        TenantPackageResp tenantPackageResp = new TenantPackageResp();
        BeanUtils.copyProperties(tenantPackage, tenantPackageResp);

        return tenantPackageResp;
    }

    @Override
    public List<TenantPackageResp> getTenantPackageList(Integer status) {
        LambdaQueryWrapper<SystemTenantPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(status != null, SystemTenantPackage::getStatus, status);
        List<SystemTenantPackage> tenantPackageList = list(queryWrapper);

        List<TenantPackageResp> tenantPackageRespList = tenantPackageList.stream().map(item -> {
            TenantPackageResp tenantPackageResp = new TenantPackageResp();
            BeanUtils.copyProperties(item, tenantPackageResp);
            return tenantPackageResp;
        }).collect(Collectors.toList());
        return tenantPackageRespList;
    }

    @Override
    public PageResult<TenantPackageResp> getTenantPackagePage(TenantPackagePageReq pageReq) {

        LambdaQueryWrapper<SystemTenantPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(pageReq.getName()), SystemTenantPackage::getName, pageReq.getName())
                .eq(pageReq.getStatus() != null, SystemTenantPackage::getStatus, pageReq.getStatus())
                .like(StringUtils.isNotBlank(pageReq.getRemark()), SystemTenantPackage::getRemark, pageReq.getRemark())
                .between(pageReq.getCreateTime() != null, SystemTenantPackage::getCreateTime, pageReq.getCreateTime()[0], pageReq.getCreateTime()[1]);

        Page<SystemTenantPackage> systemTenantPackagePage = tenantPackageMapper.selectPage(new Page<>(pageReq.getPageNo(), pageReq.getPageSize()), queryWrapper);
        List<TenantPackageResp> tenantPackageRespList = systemTenantPackagePage.getRecords().stream().map(item -> {
            TenantPackageResp tenantPackageResp = new TenantPackageResp();
            BeanUtils.copyProperties(item, tenantPackageResp);
            return tenantPackageResp;
        }).collect(Collectors.toList());

        return new PageResult<>(systemTenantPackagePage, tenantPackageRespList);
    }


    /**
     * 校验是否存在
     *
     * @param id
     * @return
     */
    private SystemTenantPackage validateTenantPackageExists(Long id) {
        SystemTenantPackage tenantPackage = getById(id);
        if (tenantPackage == null) {
            throw new GlobalException(ResultCodeEnum.TENANT_PACKAGE_NOT_EXISTS);
        }
        return tenantPackage;
    }

    private void validateTenantUsed(Long id) {
        if (tenantService.getTenantListByPackageId(id).size() > 0) {
            throw new GlobalException(ResultCodeEnum.TENANT_PACKAGE_USED);
        }
    }

    /**
     * 校验租户套餐是否被禁用
     * @param id
     * @return
     */
    @Override
    public SystemTenantPackage validTenantPackage(Long id) {
        SystemTenantPackage tenantPackage = validateTenantPackageExists(id);
        if (tenantPackage.getStatus().equals(EnableStatusEnum.DISABLE.getStatus())) {
            throw new GlobalException(ResultCodeEnum.TENANT_PACKAGE_DISABLE, tenantPackage.getName());
        }
        return tenantPackage;
    }
}
