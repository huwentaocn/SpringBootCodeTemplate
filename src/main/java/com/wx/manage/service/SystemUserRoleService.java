package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
public interface SystemUserRoleService extends IService<SystemUserRole> {


    /**
     * 删除指定角色的绑定的用户关系
     * @param roleId
     * @return
     */
    Boolean removeListByRoleId(Long roleId);

    /**
     * 删除指定用户的角色绑定关系
     * @param userId
     * @return
     */
    Boolean removeListByUserId(Long userId);

    /**
     * 获取指定用户的角色id集合
     * @param userId
     * @return
     */
    List<Long> geRoleIdListByUserId(Long userId);

    /**
     * 设置用户角色
     *
     * @param userId 角色编号
     * @param roleIds 角色编号集合
     */
    void assignUserRole(Long userId, Set<Long> roleIds);

}
