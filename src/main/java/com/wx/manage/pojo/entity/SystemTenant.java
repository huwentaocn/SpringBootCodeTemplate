package com.wx.manage.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 租户表
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Getter
@Setter
@TableName("system_tenant")
@ApiModel(value = "SystemTenant对象", description = "租户表")
public class SystemTenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 套餐编号 - 系统
     */
    public static final Long PACKAGE_ID_SYSTEM = 0L;

    @ApiModelProperty("租户编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("租户名")
    @TableField("name")
    private String name;

    @ApiModelProperty("联系人的用户编号")
    @TableField("contact_user_id")
    private Long contactUserId;

    @ApiModelProperty("联系人")
    @TableField("contact_name")
    private String contactName;

    @ApiModelProperty("联系手机")
    @TableField("contact_mobile")
    private String contactMobile;

    @ApiModelProperty("租户状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("绑定域名")
    @TableField("domain")
    private String domain;

    @ApiModelProperty("租户套餐编号")
    @TableField("package_id")
    private Long packageId;

    @ApiModelProperty("过期时间")
    @TableField("expire_time")
    private LocalDateTime expireTime;

    @ApiModelProperty("账号数量")
    @TableField("account_count")
    private Integer accountCount;

    @ApiModelProperty("数据源配置编号")
    @TableField("data_source_config_id")
    private Long dataSourceConfigId;

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
