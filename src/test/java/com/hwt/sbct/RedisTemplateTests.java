package com.hwt.sbct;

import com.alibaba.fastjson.JSONObject;
import com.hwt.sbct.mapper.WxProjectMapper;
import com.hwt.sbct.pojo.entity.WxProject;
import com.hwt.sbct.until.EncryptionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTests {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private WxProjectMapper wxProjectMapper;


    @Test
    public void saveDict(){
        WxProject wxProject = wxProjectMapper.selectById(1);
        //向数据库中存储string类型的键值对, 过期时间5分钟
        redisTemplate.opsForValue().set("wxProject", wxProject, 5, TimeUnit.MINUTES);
    }


    @Test
    public void getDict(){
        String s = EncryptionUtil.encryptMD5("123456789");
        System.out.println(s);
    }
}