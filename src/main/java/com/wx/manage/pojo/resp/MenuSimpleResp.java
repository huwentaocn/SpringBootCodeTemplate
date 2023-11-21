package com.wx.manage.pojo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 菜单精简信息
 * @Date 2023/11/21 13:26 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "MenuSimpleResp", description = "菜单精简信息")
public class MenuSimpleResp {

    @ApiModelProperty(value = "菜单编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "菜单名称", required = true, example = "芋道")
    private String name;

    @ApiModelProperty(value = "父菜单 ID", required = true, example = "1024")
    private Long parentId;

    @ApiModelProperty(value = "类型,参见 MenuTypeEnum 枚举类", required = true, example = "1")
    private Integer type;
}
