package com.wx.manage.service;

import com.wx.manage.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.manage.pojo.req.UserLoginReq;
import com.wx.manage.pojo.req.UserRegisterReq;
import com.wx.manage.pojo.resp.UserLoginResp;
import com.wx.manage.pojo.vo.UserInfoVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2024-03-16
 */
public interface UserService extends IService<User> {

    UserInfoVo register(UserRegisterReq req);

    UserLoginResp login(UserLoginReq req);


    UserInfoVo getUserInfoVoById(Long id);
}
