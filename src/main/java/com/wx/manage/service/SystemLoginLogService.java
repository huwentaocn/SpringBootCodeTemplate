package com.wx.manage.service;

import com.wx.manage.constant.UserTypeEnum;
import com.wx.manage.constant.logger.LoginLogTypeEnum;
import com.wx.manage.constant.logger.LoginResultEnum;
import com.wx.manage.pojo.entity.SystemLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
public interface SystemLoginLogService extends IService<SystemLoginLog> {

    /**
     * 创建登录日志
     * @param userId
     * @param username
     * @param userTypeEnum
     * @param logTypeEnum
     * @param loginResult
     */
    void createLoginLog(Long userId, String username, UserTypeEnum userTypeEnum,
                        LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult);

}
