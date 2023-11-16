package com.wx.manage.config.mybatisplus;

import com.wx.manage.tenant.TenantConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description mybatis-plus配置
 * @Date 2023/4/10 15:30 星期一
 * @Author Hu Wentao
 */

@Configuration
@Import({PaginationConfig.class, TenantConfig.class})
public class MyBatisPlusConfig {

}
