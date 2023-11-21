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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 租户数据源关联表表	
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-20
 */
@Getter
@Setter
@TableName("system_tenant_data_source")
@ApiModel(value = "SystemTenantDataSource对象", description = "租户数据源关联表表	")
public class SystemTenantDataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("租户id")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("数据源id")
    @TableField("data_source_id")
    private Long dataSourceId;

    @ApiModelProperty("数据库名称")
    @TableField("database_name")
    private String databaseName;

    @ApiModelProperty("数据库url")
    @TableField("url")
    private String url;

    @ApiModelProperty("状态：1:待创建；2已创建，待初始化；3已初始化，4：使用中")
    @TableField("status")
    private String status;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("数据库类型")
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
