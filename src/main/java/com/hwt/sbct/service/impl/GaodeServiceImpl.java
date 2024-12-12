package com.hwt.sbct.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hwt.sbct.constant.GaodeUrlConstant;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.pojo.callVo.baidu.BaiduResultVo;
import com.hwt.sbct.pojo.callVo.baidu.GeocodingVo;
import com.hwt.sbct.pojo.callVo.gaode.GaodeResultVo;
import com.hwt.sbct.pojo.resp.LocationPointResp;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.GaodeService;
import com.hwt.sbct.until.HttpClientUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 高德接口实现层
 * @Date 2024/12/11 16:01 星期三
 * @Author Hu Wentao
 */

@Service
public class GaodeServiceImpl implements GaodeService {

    @Value("${gaode.map.ak}")
    private String AK;

    @SneakyThrows
    @Override
    public LocationPointResp getLocationPoint(String location) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("address", location);
            params.put("output", "JSON");
            params.put("key", AK);

            String result = HttpClientUtils.get(GaodeUrlConstant.GET_MAP_GEOCODING_URL, params);
            GaodeResultVo gaodeResultVo = JSONObject.parseObject(result, GaodeResultVo.class);
            if (gaodeResultVo.getStatus() != 1) {
                throw new GlobalException(ResultCodeEnum.GAODE_CALL_GET_LOCATION_FAIL, gaodeResultVo.getInfo());
            }
            List<com.hwt.sbct.pojo.callVo.gaode.GeocodingVo> geocodingVoList = gaodeResultVo.getGeocodes();
            //多个地址取第一个
            String[] split = geocodingVoList.get(0).getLocation().split(",");

            //构建响应体
            LocationPointResp locationPointResp = new LocationPointResp();
            locationPointResp.setLongitude(split[0]);
            locationPointResp.setLatitude(split[1]);
            return locationPointResp;
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new GlobalException(ResultCodeEnum.GAODE_CALL_FAIL);
        }
    }
}
