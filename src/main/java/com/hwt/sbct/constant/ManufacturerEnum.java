package com.hwt.sbct.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description 厂商枚举
 * @Date 2025/6/6 14:32 星期五
 * @Author Hu Wentao
 */

@AllArgsConstructor
public enum ManufacturerEnum {

    ALI_CLOUD_TONGYITINGWU(1, "阿里云通义听悟"),

    MICROSOFT(3, "微软"),


    ;

    public final Integer code;

    public final String message;
}
