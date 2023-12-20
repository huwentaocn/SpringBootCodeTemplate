package com.wx.manage.auth;

import com.wx.manage.config.tenant.TenantContextHolder;
import com.wx.manage.constant.RedisConstant;
import com.wx.manage.constant.HeaderConstant;
import com.wx.manage.constant.UserTypeEnum;
import com.wx.manage.exception.GlobalException;
import com.wx.manage.pojo.vo.UserInfoVo;
import com.wx.manage.result.ResultCodeEnum;
import com.wx.manage.service.SystemTenantService;
import com.wx.manage.until.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户登录拦截器
 * @Date 2023/8/31 10:41 星期四
 * @Author Hu Wentao
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    //放行接口
    private final List<String> passPathList = new ArrayList<String>() {{
        // 添加Swagger接口的请求路径到passPathList
        add("/**/swagger-ui.html");
        add("/**/swagger-resources");
        add("/**/v2/api-docs");

        // 添加Knife4j相关请求路径到passPathList
        add("/**/doc.html");
        add("/**/webjars/**");

        // 添加静态资源
        add("/static/**");
        add("/templates/**");
        add("/error");

        //放行短信
        add("/**/sms/**");

        add("/**/login/**");

        //放行hero
        add("/**/hero/**");
    }};

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //设置租户id
        String tenantId = request.getHeader(HeaderConstant.HEADER_TENANT_ID);
        if (StringUtils.isNotBlank(tenantId)) {
            TenantContextHolder.setTenantId(Long.valueOf(tenantId));
        }

        // 获取请求的URL
        String requestURI = request.getRequestURI();
        //是否放行
        boolean isAllowed = passPathList.stream()
                .anyMatch(path -> requestURI.matches(path.replaceAll(".*", ".*")));
        if (isAllowed) {
            return true; // 放行请求
        }

        //获取管理用户信息
        UserInfoVo userInfo = this.getUserInfo(request);
        //获取租户内用户信息


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
        String token = request.getHeader(HeaderConstant.AUTHORIZATION);

        UserInfoVo userInfoVo = null;
        //判断token不为空
        if(StringUtils.isNotBlank(token)) {
            //从token获取userId
            Long userId = JwtUtil.getUserId(token);
            //根据userId到Redis获取用户信息

            //普通用户
            userInfoVo = (UserInfoVo)redisTemplate.opsForValue()
                    .get(RedisConstant.getUserInfoKey(userId));
            if (userInfoVo == null) {
                //管理用户
                userInfoVo = (UserInfoVo)redisTemplate.opsForValue()
                        .get(RedisConstant.getTenantUserInfoKey(UserTypeEnum.ADMIN.getCode(), userId));
            }

            //获取数据放到ThreadLocal里面
            if(userInfoVo != null) {
                AuthContextHolder.setUserId(userInfoVo.getId());
                AuthContextHolder.setUserInfoVo(userInfoVo);
            }
        }
        return userInfoVo;
    }
}
