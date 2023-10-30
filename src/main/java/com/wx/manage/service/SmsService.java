package com.wx.manage.service;

import com.wx.manage.pojo.req.SendSmsReq;
import com.wx.manage.result.Result;

/**
 * @Description 短信模块接口层
 * @Date 2023/8/26 13:57 星期六
 * @Author Hu Wentao
 */
public interface SmsService {
    Result sendFiveMinuteCommonMessage(SendSmsReq req);

    Result sendTwoMinuteCommonMessage(SendSmsReq req);

    Result sendOneMinuteCommonMessage(SendSmsReq req);
}
