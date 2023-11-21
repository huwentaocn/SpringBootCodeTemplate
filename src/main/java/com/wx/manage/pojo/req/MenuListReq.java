package com.wx.manage.pojo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 菜单列表请求体
 * @Date 2023/11/21 13:56 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "MenuListReq", description = "菜单列表请求体")
public class MenuListReq {

    @ApiModelProperty(value = "菜单名称,模糊匹配", example = "芋道")
    private String name;

    @ApiModelProperty(value = "展示状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;
}
