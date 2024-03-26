package com.hwt.sbct.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 登录拦截器
 * @Date 2023/8/31 10:49 星期四
 * @Author Hu Wentao
 */
@Configuration
public class LoginMvcConfigurerAdapter implements WebMvcConfigurer {

    @Resource
    private RedisTemplate redisTemplate;

    //放行接口
    private final List<String> passPathList = new ArrayList<String>() {{
        //swagger文档放行
        add("/swagger-resources/**");
        add("/doc.html");
        add("/v2/**");
        add("/webjars/**");

        add("/static/**");
        add("/templates/**");
        add("/error");

        //放行短信
        add("/**/sms/**");

        add("/**/hero/**");
    }};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
//                "/api", "/api-docs", "/api-docs/**", "/doc.html/**"};

        registry.addInterceptor(new UserLoginInterceptor(redisTemplate))
                .addPathPatterns("/**")
                .excludePathPatterns(passPathList.toArray(new String[0]))
        ;
    }
}
