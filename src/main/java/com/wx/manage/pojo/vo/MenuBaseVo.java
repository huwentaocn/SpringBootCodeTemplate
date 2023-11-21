package com.wx.manage.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description 菜单基础对象
 * @Date 2023/11/21 13:15 星期二
 * @Author Hu Wentao
 */

@ApiModel(value = "MenuBaseVo", description = "菜单基础对象")
@Data
public class MenuBaseVo {


    @ApiModelProperty(value = "菜单名称", required = true, example = "芋道")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    @ApiModelProperty(value = "权限标识,仅菜单类型为按钮时，才需要传递", example = "sys:menu:add")
    @Size(max = 100)
    private String permission;

    @ApiModelProperty(value = "类型,参见 MenuTypeEnum 枚举类", required = true, example = "1")
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "显示顺序不能为空", required = true, example = "1024")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "父菜单 ID", required = true, example = "1024")
    @NotNull(message = "父菜单 ID 不能为空")
    private Long parentId;

    @ApiModelProperty(value = "路由地址,仅菜单类型为菜单或者目录时，才需要传", example = "post")
    @Size(max = 200, message = "路由地址不能超过200个字符")
    private String path;

    @ApiModelProperty(value = "菜单图标,仅菜单类型为菜单或者目录时，才需要传", example = "/menu/list")
    private String icon;

    @ApiModelProperty(value = "组件路径,仅菜单类型为菜单时，才需要传", example = "system/post/index")
    @Size(max = 200, message = "组件路径不能超过255个字符")
    private String component;

    @ApiModelProperty(value = "组件名", example = "SystemUser")
    private String componentName;

    @ApiModelProperty(value = "状态,见 CommonStatusEnum 枚举", required = true, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "是否可见", example = "false")
    private Boolean visible;

    @ApiModelProperty(value = "是否缓存", example = "false")
    private Boolean keepAlive;

    @ApiModelProperty(value = "是否总是显示", example = "false")
    private Boolean alwaysShow;
}
