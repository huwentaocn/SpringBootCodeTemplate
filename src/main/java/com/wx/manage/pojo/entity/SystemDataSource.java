package com.wx.manage.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.wx.manage.handler.type.EncryptTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据源表	
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "system_data_source", autoResultMap = true)
@ApiModel(value = "SystemDataSource对象", description = "数据源表	")
public class SystemDataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号 - Master 数据源
     */
    public static final Long ID_MASTER = 0L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("ip地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("端口号")
    @TableField("port")
    private String port;

    @ApiModelProperty("url")
    @TableField("url")
    private String url;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField(value = "password", typeHandler = EncryptTypeHandler.class)
    private String password;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("数据库类型：1mysql，默认1")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("逻辑删除：0未删除，1删除，默认0")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("版本")
    @TableField("version")
    @Version
    private Integer version;

    @ApiModelProperty("创建者id")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新者id")
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updater;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
