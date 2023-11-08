package com.wx.manage.constant;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import lombok.Getter;

/**
 * @Description
 * @Date 2023/8/31 10:06 星期四
 * @Author Hu Wentao
 */
@JSONType(serializer = EnumSerializer.class, deserializer = EnumDeserializer.class, serializeEnumAsJavaBean = true)
@Getter
public enum UserTypeEnum {
    ADMIN(1,"管理员"),
    USER(2,"普通用户" );

    @EnumValue
    private final Integer code ;
    private final String comment ;

    UserTypeEnum(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}
