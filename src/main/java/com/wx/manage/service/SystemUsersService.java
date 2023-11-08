package com.wx.manage.service;

import com.wx.manage.pojo.entity.SystemUsers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.resp.UserSimpleResp;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
public interface SystemUsersService extends IService<SystemUsers> {

    /**
     * 更新用户登录数据
     */
    void updateUserLogin(Long userId, String loginIp);

    /**
     * 获取用户精简信息列表
     * @return
     */
    List<UserSimpleResp> getSimpleUserList();
}
