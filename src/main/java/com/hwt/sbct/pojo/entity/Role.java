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
 * 角色表
 * </p>
 *
 * @author Hu Wentao
 * @since 2024-03-16
 */
@Getter
@Setter
@TableName("role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("角色权限字符串")
    @TableField("code")
    private String code;

    @ApiModelProperty("角色状态（0正常，1停用），默认0")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("版本号，默认0")
    @TableField("version")
    @Version
    private String version;

    @ApiModelProperty("逻辑删除：0未删除，1已删除，默认0")
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
