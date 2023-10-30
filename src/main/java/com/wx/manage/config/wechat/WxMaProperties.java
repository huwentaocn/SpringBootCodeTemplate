package com.wx.manage.config.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Description 微信配置类
 * @Date 2023/8/31 9:08 星期四
 * @Author Hu Wentao
 */

@Data
@ConfigurationProperties(prefix = "wechat.app")
public class WxMaProperties {

    private List<Config> configs;

    @Data
    public static class Config {
        /**
         * 设置微信小程序的appid
         */
        private String appid;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;
    }
}
