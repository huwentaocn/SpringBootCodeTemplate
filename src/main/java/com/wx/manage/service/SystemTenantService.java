package com.wx.manage.service;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.wx.manage.handler.tenant.TenantInfoHandler;
import com.wx.manage.handler.tenant.TenantMenuHandler;
import com.wx.manage.pojo.entity.SystemTenant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.excel.TenantExcelVo;
import com.wx.manage.pojo.page.PageResult;
import com.wx.manage.pojo.req.TenantCreateReq;
import com.wx.manage.pojo.req.TenantExportReq;
import com.wx.manage.pojo.req.TenantPageReq;
import com.wx.manage.pojo.req.TenantUpdateReq;
import com.wx.manage.pojo.resp.TenantResp;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
public interface SystemTenantService extends IService<SystemTenant> {


    TenantResp getTenantByName(String name);

    /**
     * 获得租户对应的数据源配置
     *
     * @param id 租户编号
     * @return 数据源配置
     */
    DataSourceProperty getDataSourceProperty(Long id);


    Long createTenant(TenantCreateReq createReq);

    Boolean updateTenant(TenantUpdateReq updateReq);


    Boolean deleteTenant(Long id);

    TenantResp getTenantDetail(Long id);

    List<TenantResp> getTenantList();

    List<TenantExcelVo> getTenantList(TenantExportReq exportReq);

    List<SystemTenant> getTenantListByPackageId(Long packageId);

    PageResult<TenantResp> getTenantPage(TenantPageReq tenantPageReq);

    void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds);

    /**
     * 进行租户的信息处理逻辑
     * 其中，租户编号从 {@link TenantContextHolder} 上下文中获取
     *
     * @param handler 处理器
     */
    void handleTenantInfo(TenantInfoHandler handler);


    /**
     * 进行租户的菜单处理逻辑
     * 其中，租户编号从 {@link TenantContextHolder} 上下文中获取
     *
     * @param handler 处理器
     */
    void handleTenantMenu(TenantMenuHandler handler);

    /**
     * 获取租户id集合
     * @return
     */
    List<Long> getTenantIdList();
    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validateTenant(Long id);

    /**
     * 校验租户名称是否存在
     * @param name
     * @return
     */
    Boolean validateTenantNameIsExist(String name);


    /**
     * 是否禁用租户
     * @return
     */
    Boolean isTenantDisable();

}
