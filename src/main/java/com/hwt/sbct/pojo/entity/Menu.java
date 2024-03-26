package com.hwt.sbct.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author Hu Wentao
 * @since 2024-03-16
 */
@Getter
@Setter
@TableName("menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("菜单类型（0目录，1菜单，2按钮）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("父级id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("路由地址")
    @TableField("path")
    private String path;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("组件路径")
    @TableField("component")
    private String component;

    @ApiModelProperty("组件名称")
    @TableField("component_name")
    private String componentName;

    @ApiModelProperty("菜单状态：0正常，1隐藏，默认0")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("版本号")
    @TableField("version")
    @Version
    private Integer version;

    @ApiModelProperty("逻辑删除：0正常，1删除，默认0")
    @TableField("deleted")
    @TableLogic
    private String deleted;

    @ApiModelProperty("创建者")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新者")
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
