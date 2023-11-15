package com.wx.manage.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description 角色基础对象
 * @Date 2023/11/13 10:05 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "RoleBaseVo", description = "角色基础对象")
public class RoleBaseVo {

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 30, message = "角色名称长度不能超过30个字符")
    @ApiModelProperty(value = "角色名称")
    private String name;

    @NotBlank(message = "角色标志不能为空")
    @Size(max = 100, message = "角色标志长度不能超过100个字符")
    @ApiModelProperty(value = "角色编码", required = true, example = "ADMIN")
    private String code;

    @ApiModelProperty(value = "显示顺序不能为空", required = true, example = "1024")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "备注", example = "我是一个角色")
    private String remark;
}
