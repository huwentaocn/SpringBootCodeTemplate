package com.wx.manage.service.impl;

import com.wx.manage.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 微信服务接口实现层
 * @Date 2023/8/30 17:42 星期三
 * @Author Hu Wentao
 */

@Slf4j
@Service
public class WechatServiceImpl  implements WechatService {

    @Override
    public String getLoginQRCode() {
//        try {
//            //固定地址，后面拼接参数
////        String url = "https://open.weixin.qq.com/" +
////                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";
//
//            // 微信开放平台授权baseUrl  %s相当于?代表占位符
//            String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
//                    "?appid=%s" +
//                    "&redirect_uri=%s" +
//                    "&response_type=code" +
//                    "&scope=snsapi_login" +
//                    "&state=%s" +
//                    "#wechat_redirect";
//
//            //对redirect_url进行URLEncoder编码
//            String redirectUrl = URLEncoder.encode(WECHAT_OPEN_REDIRECT_URL, "utf-8");
//
//            //设置%s里面值
//            String url = String.format(
//                    baseUrl,
//                    WECHAT_OPEN_APP_ID,
//                    redirectUrl,
//                    "wenxiang"
//            );
//
//            //重定向到请求微信地址里面
//            return "redirect:"+url;
//        } catch (Exception e) {
//            log.error("WechatServiceImpl getLoginQRCode Exception: ==>", e);
//            return "";
//        }
        return null;
    }
}
