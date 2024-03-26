package com.hwt.sbct.service;

import com.hwt.sbct.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwt.sbct.pojo.req.UserLoginReq;
import com.hwt.sbct.pojo.req.UserRegisterReq;
import com.hwt.sbct.pojo.resp.UserLoginResp;
import com.hwt.sbct.pojo.vo.UserInfoVo;

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
