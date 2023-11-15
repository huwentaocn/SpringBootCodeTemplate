package com.wx.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.wx.manage.pojo.entity.SystemRoleMenu;
import com.wx.manage.pojo.entity.SystemUserRole;
import com.wx.manage.mapper.SystemUserRoleMapper;
import com.wx.manage.service.SystemUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRole> implements SystemUserRoleService {


    /**
     * 删除指定角色的绑定的用户关系
     * @param roleId
     * @return
     */
    @Override
    public Boolean removeListByRoleId(Long roleId) {
        LambdaUpdateWrapper<SystemUserRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUserRole::getRoleId, roleId);
        return remove(updateWrapper);
    }

    /**
     * 删除指定用户的角色绑定关系
     * @param userId
     * @return
     */
    @Override
    public Boolean removeListByUserId(Long userId) {
        LambdaUpdateWrapper<SystemUserRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUserRole::getUserId, userId);
        return remove(updateWrapper);
    }

    /**
     * 删除指定用户的指定的角色绑定关系
     * @param userId
     * @param roleIdList
     * @return
     */
    public Boolean removeByUserIdAndRoleIdIdList(Long userId, List<Long> roleIdList) {
        LambdaUpdateWrapper<SystemUserRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUserRole::getUserId, userId)
                .in(SystemUserRole::getRoleId, roleIdList);
        return remove(updateWrapper);
    }

    @Override
    @DSTransactional // 多数据源，使用 @DSTransactional 保证本地事务，以及数据源的切换
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        List<Long> dbRoleIds = geRoleIdListByUserId(userId);
        // 计算新增和删除的角色编号
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteRoleIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (CollUtil.isNotEmpty(createRoleIds)) {
            List<SystemUserRole> userRoleList = createRoleIds.stream().map(item -> {
                SystemUserRole userRole = new SystemUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(item);
                return userRole;
            }).collect(Collectors.toList());
            saveOrUpdateBatch(userRoleList);
        }
        if (CollUtil.isNotEmpty(deleteRoleIds)) {
            removeByUserIdAndRoleIdIdList(userId, (List<Long>) deleteRoleIds);
        }
    }



    /**
     * 获取指定角色的菜单
     * @param userId
     * @return
     */
    @Override
    public List<Long> geRoleIdListByUserId(Long userId) {
        LambdaQueryWrapper<SystemUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUserRole::getUserId, userId)
                .select(SystemUserRole::getId);
        List<Object> objectList = listObjs(queryWrapper);

        return objectList.stream()
                .map(o -> (Long) o)// 指定要转换的类型
                .collect(Collectors.toList());
    }

}
