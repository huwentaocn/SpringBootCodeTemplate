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
        //获取用户信息
        UserInfoVo userInfo = this.getUserInfo(request);

        //拿不到用户信息，返回
        if (userInfo == null) {
            throw new GlobalException(ResultCodeEnum.LOGIN_AUTH);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后执行的逻辑
        System.out.println("Interceptor: postHandle");
    }

    private UserInfoVo getUserInfo(HttpServletRequest request) {
        //从请求头获取token
        String token = request.getHeader(TokenConstant.AUTHORIZATION);

        UserInfoVo userInfoVo = null;
        //判断token不为空
        if(StringUtils.isNotBlank(token)) {
            //从token获取userId
            Long userId = JwtUtil.getUserId(token);
            //根据userId到Redis获取用户信息
            userInfoVo = (UserInfoVo)redisTemplate.opsForValue()
                    .get(RedisConstant.getUserInfoKey(userId));
            //获取数据放到ThreadLocal里面
            if(userInfoVo != null) {
                AuthContextHolder.setUserId(userInfoVo.getId());
                AuthContextHolder.setUserInfoVo(userInfoVo);
            }
        }
        return userInfoVo;
    }
}
