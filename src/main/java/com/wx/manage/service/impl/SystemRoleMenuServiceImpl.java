package com.wx.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wx.manage.config.tenant.aop.TenantDS;
import com.wx.manage.pojo.entity.SystemRoleMenu;
import com.wx.manage.mapper.SystemRoleMenuMapper;
import com.wx.manage.service.SystemRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
@TenantDS
public class SystemRoleMenuServiceImpl extends ServiceImpl<SystemRoleMenuMapper, SystemRoleMenu> implements SystemRoleMenuService {


    /**
     * 设置角色菜单
     * @param roleId 角色编号
     * @param menuIds 菜单编号集合
     */
    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有菜单编号
        List<Long> dbMenuIds = getMenIdListByRoleId(roleId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (CollUtil.isNotEmpty(createMenuIds)) {
            List<SystemRoleMenu> systemRoleMenuList = createMenuIds.stream().map(item -> {
                SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                systemRoleMenu.setRoleId(roleId);
                systemRoleMenu.setMenuId(item);
                return systemRoleMenu;
            }).collect(Collectors.toList());
            saveOrUpdateBatch(systemRoleMenuList);
        }
        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            removeByRoleIdAndMenuIdList(roleId, (List<Long>) deleteMenuIds);
        }
    }

    /**
     * 获取指定角色的菜单
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getMenIdListByRoleId(Long roleId) {
        LambdaQueryWrapper<SystemRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemRoleMenu::getRoleId, roleId)
                .select(SystemRoleMenu::getMenuId);
        List<Object> objectList = listObjs(queryWrapper);

        return objectList.stream()
                .map(o -> (Long) o)// 指定要转换的类型
                .collect(Collectors.toList());
    }

    /**
     * 删除指定角色的绑定的菜单关系
     * @param roleId
     * @return
     */
    @Override
    public Boolean removeListByRoleId(Long roleId) {
        LambdaUpdateWrapper<SystemRoleMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemRoleMenu::getRoleId, roleId);
        return remove(updateWrapper);
    }


    /**
     * 删除指定角色关联的指定菜单
     * @param roleId
     * @param menuIdList
     * @return
     */
    public Boolean removeByRoleIdAndMenuIdList(Long roleId, List<Long> menuIdList) {
        LambdaUpdateWrapper<SystemRoleMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemRoleMenu::getRoleId, roleId)
                .in(SystemRoleMenu::getMenuId, menuIdList);
        return remove(updateWrapper);
    }
}
