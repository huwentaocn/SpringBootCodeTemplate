package com.wx.manage.constant;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.text.MessageFormat;

/**
 * @Description 文香私有化平台url常量
 * @Date 2023/9/5 17:14 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "WxUrlConstant", description = "文香私有化平台url常量")
public class WxUrlConstant {

    /**
     * 测试服务url
     */
//    private static final String TEST_SERVER_URL = "http://{0}/gateway/basic/project/hero/test/server";
    private static final String TEST_SERVER_URL = "http://{0}:8001/basic/project/hero/test/server";

//    private static final String SAVE_PROJECT_URL = "http://{0}/gateway/basic/project/hero/save/project";
    private static final String SAVE_PROJECT_URL = "http://{0}:8001/basic/project/hero/save/project";

    public static String getTestServerUrl(String host) {
        return MessageFormat.format(TEST_SERVER_URL, host);
    }

    public static String getSaveProjectUrl(String host) {
        return MessageFormat.format(SAVE_PROJECT_URL, host);
    }
}
