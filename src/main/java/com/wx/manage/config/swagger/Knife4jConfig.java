package com.wx.manage.config.swagger;

import com.aliyun.oss.common.utils.HttpHeaders;
import com.wx.manage.constant.HeaderConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;


/**
 * 配置knife4j
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket wxManageDocket() {
        List<Parameter> pars = new ArrayList<>();
        // Authorization
        ParameterBuilder authorizationPar = new ParameterBuilder();
        authorizationPar.name(HeaderConstant.AUTHORIZATION).description("令牌").modelRef(new ModelRef("String")).parameterType("header").required(true).build();
        pars.add(authorizationPar.build());
        //tenantId
        ParameterBuilder tenantIdPar = new ParameterBuilder();
        tenantIdPar.name(HeaderConstant.HEADER_TENANT_ID).description("租户id").modelRef(new ModelRef("String")).parameterType("header").required(true).build();
        pars.add(tenantIdPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //设置文档的标题
                        .title("文香管理平台-API文档")
                        //设置文档的License信息->1.3 License information
                        .termsOfServiceUrl("https://www.wenxiang.cn/")
                        //设置文档的版本信息-> 1.0.0 Version information
                        .version("1.0.0")
                        .contact(new Contact("Hu Wentao", "https://www.wenxiang.cn", "huwentao@wenxiang.cn"))
                        .build())
                .globalOperationParameters(pars)
                //分组名称
                .groupName("文香项目管理平台")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.wx.manage.controller"))
                //可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.any())
                .build();

    }
}
