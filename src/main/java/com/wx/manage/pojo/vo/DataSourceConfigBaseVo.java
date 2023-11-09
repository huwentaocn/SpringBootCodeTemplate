package com.wx.manage.pojo.vo;

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
public class DataSourceConfigBaseVo {

    @ApiModelProperty(name = "数据源名称", required = true, example = "test")
    @NotNull(message = "数据源名称不能为空")
    private String name;

    @ApiModelProperty(name = "数据源连接", required = true, example = "jdbc:mysql://127.0.0.1:3306/ruoyi-vue-pro")
    @NotNull(message = "数据源连接不能为空")
    private String url;

    @ApiModelProperty(name = "用户名", required = true, example = "root")
    @NotNull(message = "用户名不能为空")
    private String username;
}
