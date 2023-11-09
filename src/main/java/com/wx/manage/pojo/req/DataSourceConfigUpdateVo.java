package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.DataSourceConfigBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 更新数据源请求体
 * @Date 2023/11/8 16:33 星期三
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "DataSourceConfigUpdateReq", description = "更新数据源请求体")
public class DataSourceConfigUpdateVo extends DataSourceConfigBaseVo {

    @ApiModelProperty(name = "主键编号", required = true, example = "1024")
    @NotNull(message = "主键编号不能为空")
    private Long id;

    @ApiModelProperty(name = "密码", required = true, example = "123456")
    @NotNull(message = "密码不能为空")
    private String password;

}
