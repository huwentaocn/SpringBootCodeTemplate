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
public enum LoginType {
    PASSWORD_LOGIN(1,"密码登录"),
    MOBILE_CODE_LOGIN(2,"短信验证码登录" );

    @EnumValue
    public Integer code ;
    public String comment ;

    LoginType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}
