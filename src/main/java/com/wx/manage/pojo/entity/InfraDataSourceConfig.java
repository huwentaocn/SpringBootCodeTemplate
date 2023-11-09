package com.wx.manage.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.wx.manage.config.mybatisplus.EncryptTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.A;

/**
 * <p>
 * 数据源配置表
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Data
@TableName(value = "infra_data_source_config", autoResultMap = true)
@Accessors(chain = true)
@ApiModel(value = "InfraDataSourceConfig对象", description = "数据源配置表")
public class InfraDataSourceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号 - Master 数据源
     */
    public static final Long ID_MASTER = 0L;

    @ApiModelProperty("主键编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("参数名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("数据源连接")
    @TableField("url")
    private String url;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField(value = "password", typeHandler = EncryptTypeHandler.class)
    private String password;

    @ApiModelProperty("创建者")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private String creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新者")
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    @TableField("deleted")
    private Boolean deleted;
}
