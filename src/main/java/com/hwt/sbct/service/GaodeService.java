package com.hwt.sbct.service;

import com.hwt.sbct.pojo.resp.LocationPointResp;

/**
 * @Description 高德接口层
 * @Date 2024/12/11 15:57 星期三
 * @Author Hu Wentao
 */
public interface GaodeService {

    LocationPointResp getLocationPoint(String location);

}
