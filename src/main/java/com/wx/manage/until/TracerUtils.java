package com.wx.manage.until;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * @Description 链路追踪工具类
 * @Date 2023/11/8 9:37 星期三
 * @Author Hu Wentao
 */
public class TracerUtils {

    /**
     * 私有化构造方法
     */
    private TracerUtils() {
    }

    /**
     * 获得链路追踪编号，直接返回 SkyWalking 的 TraceId。
     * 如果不存在的话为空字符串！！！
     *
     * @return 链路追踪编号
     */
    public static String getTraceId() {
        return TraceContext.traceId();
    }

}
