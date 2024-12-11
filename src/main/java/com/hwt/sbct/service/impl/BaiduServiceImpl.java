package com.hwt.sbct.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hwt.sbct.constant.BaiduUrlConstant;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.pojo.baidu.BaiduResultVo;
import com.hwt.sbct.pojo.baidu.GeocodingVo;
import com.hwt.sbct.pojo.resp.LocationPointResp;
import com.hwt.sbct.result.ResultCodeEnum;
import com.hwt.sbct.service.BaiduService;
import com.hwt.sbct.until.HttpClientUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 百度接口实现层
 * @Date 2024/12/11 16:01 星期三
 * @Author Hu Wentao
 */

@Service
public class BaiduServiceImpl implements BaiduService {

    @Value("${baidu.map.ak}")
    private String AK;

    /**
     * 默认ak
     * 选择了ak，使用IP白名单校验：
     * 根据您选择的AK已为您生成调用代码
     * 检测到您当前的ak设置了IP白名单校验
     * 您的IP白名单中的IP非公网IP，请设置为公网IP，否则将请求失败
     * 请在IP地址为0.0.0.0/0 外网IP的计算发起请求，否则将请求失败
     */
    public void requestGetAK(String strUrl, Map<String, String> param) throws Exception {
        if (strUrl == null || strUrl.length() <= 0 || param == null || param.size() <= 0) {
            return;
        }

        StringBuffer queryString = new StringBuffer();
        queryString.append(strUrl);
        for (Map.Entry<?, ?> pair : param.entrySet()) {
            queryString.append(pair.getKey() + "=");
            //    第一种方式使用的 jdk 自带的转码方式  第二种方式使用的 spring 的转码方法 两种均可
            //    queryString.append(URLEncoder.encode((String) pair.getValue(), "UTF-8").replace("+", "%20") + "&");
            queryString.append(UriUtils.encode((String) pair.getValue(), "UTF-8") + "&");
        }

        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }

        java.net.URL url = new java.net.URL(queryString.toString());
        System.out.println(queryString.toString());
        URLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        isr.close();
        System.out.println("AK: " + buffer.toString());
    }

    @SneakyThrows
    @Override
    public LocationPointResp getLocationPoint(String location) {
        try {
            Map<String, String> params = new LinkedHashMap<String, String>();
            params.put("address", location);
            params.put("output", "json");
            params.put("ak", AK);

            String result = HttpClientUtils.get(BaiduUrlConstant.GET_MAP_GEOCODING_URL, params);
            BaiduResultVo baiduResultVo = JSONObject.parseObject(result, BaiduResultVo.class);
            if (baiduResultVo.getStatus() != 0) {
                throw new GlobalException(ResultCodeEnum.BAIDU_CALL_GET_LOCATION_FAIL, baiduResultVo.getMsg());
            }
            GeocodingVo geocodingVo = JSONObject.parseObject(JSONObject.toJSONString(baiduResultVo.getResult()), GeocodingVo.class);

            //构建响应体
            LocationPointResp locationPointResp = new LocationPointResp();
            locationPointResp.setLongitude(geocodingVo.getLocation().getLng());
            locationPointResp.setLatitude(geocodingVo.getLocation().getLat());
            return locationPointResp;
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new GlobalException(ResultCodeEnum.BAIDU_CALL_FAIL);
        }
    }
}
