package com.hwt.sbct.auth;

import com.hwt.sbct.until.JwtUtil;
import com.hwt.sbct.constant.TokenConstant;
import com.hwt.sbct.exception.GlobalException;
import com.hwt.sbct.result.ResultCodeEnum;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 用户登录拦截器
 * @Date 2023/8/31 10:41 星期四
 * @Author Hu Wentao
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //从请求头获取token
        String token = request.getHeader(TokenConstant.AUTHORIZATION);
        if (!JwtUtil.checkToken(token)) {
            throw new GlobalException(ResultCodeEnum.LOGIN_AUTH);
        }
        //将userId放入上下文
        AuthContextHolder.setUserId(JwtUtil.getUserId(token));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后执行的逻辑
        System.out.println("Interceptor: postHandle");
    }
}
