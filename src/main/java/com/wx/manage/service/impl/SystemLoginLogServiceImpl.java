package com.wx.manage.service.impl;

import com.wx.manage.constant.UserTypeEnum;
import com.wx.manage.constant.logger.LoginLogTypeEnum;
import com.wx.manage.constant.logger.LoginResultEnum;
import com.wx.manage.pojo.entity.SystemLoginLog;
import com.wx.manage.mapper.SystemLoginLogMapper;
import com.wx.manage.service.SystemLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.manage.service.SystemUsersService;
import com.wx.manage.until.ServletUtils;
import com.wx.manage.until.TracerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Service
public class SystemLoginLogServiceImpl extends ServiceImpl<SystemLoginLogMapper, SystemLoginLog> implements SystemLoginLogService {

    @Autowired
    private SystemUsersService usersService;

    @Override
    public void createLoginLog(Long userId, String username, UserTypeEnum userTypeEnum, LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        SystemLoginLog systemLoginLog = new SystemLoginLog();
        systemLoginLog.setLogType(logTypeEnum.getType());
        systemLoginLog.setTraceId(TracerUtils.getTraceId());
        systemLoginLog.setUserId(userId);
        systemLoginLog.setUserType(userTypeEnum.getCode());
        systemLoginLog.setUsername(username);
        systemLoginLog.setUserAgent(ServletUtils.getUserAgent());
        systemLoginLog.setUserIp(ServletUtils.getClientIP());
        systemLoginLog.setResult(loginResult.getResult());
        saveOrUpdate(systemLoginLog);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            usersService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }
}
