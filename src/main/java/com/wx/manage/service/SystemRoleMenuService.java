package com.wx.manage.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wx.manage.pojo.entity.SystemRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.entity.SystemUserRole;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
public interface SystemRoleMenuService extends IService<SystemRoleMenu> {

    /**
     * 设置角色菜单
     *
     * @param roleId 角色编号
     * @param menuIds 菜单编号集合
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 获取指定角色的菜单
     * @param roleId
     * @return
     */
    List<Long> getMenIdListByRoleId(Long roleId);

    /**
     * 删除指定角色的绑定的菜单关系
     * @param roleId
     * @return
     */
    Boolean removeListByRoleId(Long roleId);

}
