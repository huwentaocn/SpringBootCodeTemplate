package com.hwt.sbct;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Types;
import java.util.Collections;


@SpringBootTest
public class CodeGeneratorNew {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;


    @Test
    public void genCode() {

        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "/src/main/java";
        String outMapperDir = projectPath + "/src/main/resources/mapper";

        FastAutoGenerator.create(url, userName, password)
                //全局配置
                .globalConfig(builder -> {
                    builder.author("Hu Wentao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(outputDir) // 指定输出目录
                            .disableOpenDir() //禁止打开输出目录
                    ;
                })
                //数据库配置
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    } else if (typeCode == Types.TINYINT) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                //包配置
                .packageConfig(builder -> {
                    builder.parent("com.wx.manage") // 设置父包名
//                            .moduleName("wx-digital-lab") // 设置父包模块名
                            .entity("pojo.entity") //设置entity包名
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .controller("controller")
//                            .other("other")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, outMapperDir)); // 设置mapperXml生成路径
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder
                            //entity配置策略
                            .addInclude("user")
                            .entityBuilder()
                            .enableLombok()
                            .idType(IdType.ASSIGN_ID) //id策略，雪花算法
                            .enableFileOverride() //覆盖已生成文件
                            .enableTableFieldAnnotation() //开启生成实体时生成字段注解
//                            .enableRemoveIsPrefix() //开启 Boolean 类型字段移除 is 前缀
                            .versionColumnName("version") //乐观锁字段名(数据库)
                            .versionPropertyName("version") //乐观锁属性名(实体)
                            .logicDeleteColumnName("deleted") //逻辑删除字段名(数据库)
                            .logicDeletePropertyName("")// 逻辑删除属性名(实体)
                            .addTableFills(new Property("creator", FieldFill.INSERT))
                            .addTableFills(new Property("createTime", FieldFill.INSERT))
                            .addTableFills(new Property("updater", FieldFill.INSERT_UPDATE))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                            .enableFileOverride() //覆盖已生成文件

                            //controller配置策略
                            .controllerBuilder()
                            .enableRestStyle()  //开启生成@RestController 控制器

                            //mapper配置策略
                            .mapperBuilder()
                            .enableMapperAnnotation() //开启 @Mapper 注解
                            .enableBaseResultMap() //启用 BaseResultMap 生成
                            .enableBaseColumnList() //启用 BaseColumnList
                            .enableFileOverride() //覆盖已生成文件

                            //service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")

                    ;

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }



}
