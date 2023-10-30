package com.wx.manage.serviceCall;

import com.alibaba.fastjson.JSONObject;
import com.wx.manage.constant.WxUrlConstant;
import com.wx.manage.pojo.callVo.SaveProjectReq;
import com.wx.manage.result.Result;
import com.wx.manage.until.HttpClientUtils;
import org.springframework.stereotype.Service;

/**
 * @Description 文香平台接口调用
 * @Date 2023/9/6 11:15 星期三
 * @Author Hu Wentao
 */


@Service
public class WxpfCallService {

    /**
     * 测试服务器是否联通
     * @param host
     * @return
     * @throws Exception
     */
    public Result testServer(String host) throws Exception {
        String url = WxUrlConstant.getTestServerUrl(host);

        String response = HttpClientUtils.get(url);

        JSONObject jsonObject = JSONObject.parseObject(response);

        Result objectResult = new Result<>();
        objectResult.setCode((Integer) jsonObject.get("code"));
        objectResult.setMessage((String) jsonObject.get("message"));
        objectResult.setData(jsonObject.get("data"));

        return objectResult;
    }


    /**
     * 保存项目信息
     * @param req
     * @return
     * @throws Exception
     */
    public Result saveProject(SaveProjectReq req) throws Exception {
        String host = req.getHost();
        String url = WxUrlConstant.getSaveProjectUrl(host);
        String param = JSONObject.toJSONString(req);

        String response = HttpClientUtils.postBody(url, param);
        JSONObject jsonObject = JSONObject.parseObject(response);

        Result objectResult = new Result<>();
        objectResult.setCode((Integer) jsonObject.get("code"));
        objectResult.setMessage((String) jsonObject.get("message"));
        objectResult.setData(jsonObject.get("data"));
        return objectResult;
    }


}
