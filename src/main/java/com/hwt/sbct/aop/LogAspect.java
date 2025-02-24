package com.hwt.sbct.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @Description 日志切面
 * @Date 2023/4/23 13:40 星期日
 * @Author Hu Wentao
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 进入方法时间戳
     */
    private Long startTime;
    /**
     * 方法结束时间戳(计时)
     */
    private Long endTime;

    public LogAspect() {

    }

    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(* com.wx.manage.controller..*.*(..))")
    public void logPointcut() {
    }

    /**
     * 前置通知：
     * 1. 在执行目标方法之前执行，比如请求接口之前的登录验证;
     * 2. 在前置通知中设置请求日志信息，如开始时间，请求参数，注解内容等
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("logPointcut()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
//        //获取请求头中的User-Agent
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //打印请求的内容
        startTime = System.currentTimeMillis();
        log.info("==================================== 请求开始 ====================================");
//        printLog(request);
        log.info("请求开始时间：{}", LocalDateTime.now());
        log.info("请求Url : {}", request.getRequestURL().toString());
        log.info("请求方式 : {}", request.getMethod());
        log.info("请求ip : {}", request.getRemoteAddr());
        log.info("请求方法 : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : {}", Arrays.toString(joinPoint.getArgs()));
        // 系统信息
        log.info("用户系统信息：{}", request.getHeader("User-Agent"));
//        log.info("浏览器：{}", userAgent.getBrowser().toString());
//        log.info("浏览器版本：{}", userAgent.getBrowserVersion());
//        log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
    }


    /**
     * 返回通知：
     * 1. 在目标方法正常结束之后执行
     * 1. 在返回通知中补充请求日志信息，如返回时间，方法耗时，返回值，并且保存日志信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "logPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();

        log.info("请求耗时：{}", (endTime - startTime));
        // 处理完请求，返回内容
        log.info("请求返回 : {}", ret == null ? ret : JSONObject.toJSONString(ret));
        log.info("请求结束时间：{}", LocalDateTime.now());
        log.info("==================================== 请求结束 ====================================");
    }

    /**
     * 异常通知：
     * 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     * 1. 在异常通知中设置异常信息，并将其保存
     *
     * @param throwable
     */
    @AfterThrowing(value = "logPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        endTime = System.currentTimeMillis();
        // 保存异常日志记录
        log.error("发生异常时间：{}", LocalDateTime.now());
        log.error("抛出异常：{}", throwable.getMessage());
        log.info("请求耗时：{}", (endTime - startTime));
        // 处理完请求，返回内容
        log.info("请求结束时间：{}", LocalDateTime.now());
        log.info("==================================== 请求结束 ====================================");
    }


    public void printLog(HttpServletRequest request) {
        log.info("=================== print request start ======================");
        log.info("request.getRequestURI() : " + request.getRequestURI());
        log.info("request.getMethod() : " + request.getMethod());

        log.info("=================== request headers ======================");
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("Header Name - " + headerName + ", Value - " + request.getHeader(headerName));
        }

        log.info("==================== request params ============================");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            log.info("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
        }

        log.info("==================== request body ============================");
        try {
            ServletInputStream inputStream = request.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bfReader = new BufferedReader(reader);

            StringBuilder sb = new StringBuilder();
            String line;
            //第一行 判断来源是 form-data 还是 json
            if ((line = bfReader.readLine()) != null) {
                if (line.charAt(0) == '-') {
                    log.info("================= request body form-data =================");
                    //form-data
                    String formDataLine = line;
                    sb.append(formDataLine);
                    while ((formDataLine = bfReader.readLine()) != null) {
                        //筛除文件流
//                        if (line.contains("filename")) {
//                            break;
//                        }
                        if ("".equals(formDataLine)) {
                            sb.append("value:");
                        } else {
                            sb.append(formDataLine);
                        }
                    }
                    String bodyStr = StringUtils.substringBeforeLast(sb.toString(), "--");
                    if (StringUtils.isNotBlank(bodyStr)) {
                        String codeStr = StringUtils.substringBefore(bodyStr, "Content");
                        String[] bodyStrs = bodyStr.split(codeStr);
                        for (String str : bodyStrs) {
                            if (StringUtils.isNotBlank(str)) {
                                String name = StringUtils.substringBetween(str, "name=", "Content");
                                String value = name.contains("filename") ? "" : StringUtils.substringAfter(str, "value:");
                                log.info("Name - " + name + ",Value - " + value);
                            }
                        }
                    }
                } else if (line.charAt(0) == '{') {
                    //json
                    log.info("==================== request body json ============================");
                    sb.append(line);
                    while ((line = bfReader.readLine()) != null) {
                        sb.append(line);
                    }
                    log.info(sb.toString());
                }
            }
        } catch (IOException ex) {
            log.error("read http request body failed.", ex);
        }

        log.info("=================== print request end ======================");
    }
}