package com.wx.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.manage.pojo.entity.SystemTenantPackage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.TenantPackageCreateReq;
import com.wx.manage.pojo.req.TenantPackagePageReq;
import com.wx.manage.pojo.req.TenantPackageUpdateReq;
import com.wx.manage.pojo.resp.TenantPackageResp;

import java.util.List;

/**
 * <p>
 * 租户套餐表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
public interface SystemTenantPackageService extends IService<SystemTenantPackage> {

    Long createTenantPackage(TenantPackageCreateReq createReq);

    Boolean updateTenantPackage(TenantPackageUpdateReq updateReq);

    Boolean deleteTenantPackage(Long id);

    TenantPackageResp getTenantPackageDetail(Long id);

    List<TenantPackageResp> getTenantPackageList(Integer status);

    PageResult<TenantPackageResp> getTenantPackagePage(TenantPackagePageReq pageReq);


    SystemTenantPackage validTenantPackage(Long id);
}
