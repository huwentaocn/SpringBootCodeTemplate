package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.RoleCreateReq;
import com.wx.manage.pojo.req.RoleUpdateReq;
import com.wx.manage.pojo.resp.RoleResp;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
public interface SystemRoleService extends IService<SystemRole> {

    Long createRole(RoleCreateReq createReq, Integer type);

    Boolean updateRole(RoleUpdateReq updateReq);

    Boolean deleteRole(Long id);

    RoleResp getRoleDetail(Long id);

    List<RoleResp> getRoleList();

}
