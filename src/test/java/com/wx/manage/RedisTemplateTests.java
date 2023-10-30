package com.wx.manage;

import com.alibaba.fastjson.JSONObject;
import com.wx.manage.mapper.WxProjectMapper;
import com.wx.manage.pojo.entity.WxProject;
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
        WxProject wxProject = (WxProject)redisTemplate.opsForValue().get("wxProject");
        System.out.println(JSONObject.toJSONString(wxProject));
    }
}