package com.wx.manage.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 数据源配置对象
 * @Date 2023/11/8 15:46 星期三
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "DataSourceConfigVo", description = "数据源配置对象")
public class DataSourceBaseVo {

    @ApiModelProperty(value = "数据源名称", required = true, example = "test")
    @NotNull(message = "数据源名称不能为空")
    private String name;

    @ApiModelProperty(value = "ip地址",  required = true, example = "127.0.0.1")
    @NotNull(message = "ip地址不能为空")
    private String ip;


    @ApiModelProperty(value = "端口号",  required = true, example = "3306")
    @NotNull(message = "端口号不能为空")
    private String port;

    @ApiModelProperty(value = "用户名", required = true, example = "root")
    @NotNull(message = "用户名不能为空")
    private String username;



}
