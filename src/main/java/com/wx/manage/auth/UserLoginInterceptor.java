package com.wx.manage.auth;

import com.wx.manage.constant.RedisConstant;
import com.wx.manage.constant.TokenConstant;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.until.JwtUtil;
import org.apache.commons.lang3.StringUtils;
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
