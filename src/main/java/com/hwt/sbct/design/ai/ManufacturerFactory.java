package com.hwt.sbct.design.ai;

import com.hwt.sbct.constant.ManufacturerEnum;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.result.ResultCodeEnum;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 厂商工厂
 * @Date 2025/6/6 14:18 星期五
 * @Author Hu Wentao
 */
public class ManufacturerFactory {

    private static final Map<Integer, ManufacturerTemplate> map = new ConcurrentHashMap<>();

    public static ManufacturerTemplate getInstance(Integer type) {
        if(Objects.isNull(type)){
            throw new GlobalException(ResultCodeEnum.PARAM_FAIL, "厂商类型不能为空");
        }
        if (!map.containsKey(type)) {
            throw new GlobalException(ResultCodeEnum.PARAM_FAIL, "厂商类型不存在");
        }
        return map.get(type);
    }

    public static void register(ManufacturerEnum manufacturerEnum, ManufacturerTemplate manufacturerTemplate) {
        if (Objects.isNull(manufacturerEnum) || Objects.isNull(manufacturerTemplate)) {
            return;
        }
        map.put(manufacturerEnum.code, manufacturerTemplate);
    }
}
