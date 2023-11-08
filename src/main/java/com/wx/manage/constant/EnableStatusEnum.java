package com.wx.manage.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 开启状态枚举
 * @Date 2023/11/8 10:16 星期三
 * @Author Hu Wentao
 */
@Getter
@AllArgsConstructor
public enum EnableStatusEnum {

    ENABLE(0, "开启"),
    DISABLE(1, "关闭");

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;
}
