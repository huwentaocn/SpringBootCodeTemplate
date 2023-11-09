package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.DataSourceConfigBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 创建数据源请求体
 * @Date 2023/11/8 15:48 星期三
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "DataSourceConfigCreateReq", description = "创建数据源请求体")
public class DataSourceConfigCreateVo extends DataSourceConfigBaseVo {

    @ApiModelProperty(name = "密码", required = true, example = "123456")
    @NotNull(message = "密码不能为空")
    private String password;
}
