package com.wx.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wx.manage.constant.EnableStatusEnum;
import com.wx.manage.pojo.entity.SystemUsers;
import com.wx.manage.mapper.SystemUsersMapper;
import com.wx.manage.pojo.resp.UserSimpleResp;
import com.wx.manage.service.SystemUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-07
 */
@Service
public class SystemUsersServiceImpl extends ServiceImpl<SystemUsersMapper, SystemUsers> implements SystemUsersService {

    /**
     * 更新用户登录数据
     * @param userId
     * @param loginIp
     */
    @Override
    public void updateUserLogin(Long userId, String loginIp) {
        SystemUsers systemUsers = new SystemUsers();
        systemUsers.setId(userId);
        systemUsers.setLoginIp(loginIp);
        updateById(systemUsers);
    }

    @Override
    public List<UserSimpleResp> getSimpleUserList() {
        LambdaQueryWrapper<SystemUsers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUsers::getStatus, EnableStatusEnum.ENABLE.getStatus());
        List<SystemUsers> list = list(queryWrapper);

        //构造返回对象
        List<UserSimpleResp> userSimpleRespList = list.stream().map(item -> {
            UserSimpleResp userSimpleResp = new UserSimpleResp();
            userSimpleResp.setId(item.getId());
            userSimpleResp.setNickname(item.getNickname());
            return userSimpleResp;
        }).collect(Collectors.toList());
        return userSimpleRespList;
    }
}
