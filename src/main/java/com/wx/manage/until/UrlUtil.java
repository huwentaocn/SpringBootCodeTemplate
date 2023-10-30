package com.wx.manage.until;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description url工具类
 * @Date 2023/9/1 9:35 星期五
 * @Author Hu Wentao
 */

public class UrlUtil {

    /**
     * 判断链接是否有效
     * 输入链接
     * 返回true或者false
     */
    public static boolean isUrlValid(String strLink) {
        URL url;
        try {
            url = new URL(strLink);
            HttpURLConnection connect = (HttpURLConnection)url.openConnection();
            connect.setRequestMethod("HEAD");
            String strMessage = connect.getResponseMessage();
            if (strMessage.compareTo("Not Found") == 0) {
                return false;
            }
            connect.disconnect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
