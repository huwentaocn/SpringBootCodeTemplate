package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.MenuCreateReq;
import com.wx.manage.pojo.req.MenuListReq;
import com.wx.manage.pojo.req.MenuUpdateReq;
import com.wx.manage.pojo.resp.MenuResp;
import com.wx.manage.pojo.resp.MenuSimpleResp;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
public interface SystemMenuService extends IService<SystemMenu> {

    Long createMenu(MenuCreateReq createReq);

    Boolean updateMenu(MenuUpdateReq updateReq);

    Boolean deleteMenu(Long id);

    MenuResp getMenuDetail(Long id);

    List<MenuResp> getMenuList(MenuListReq req);

    List<MenuSimpleResp> getMenuListByTenant();
}
