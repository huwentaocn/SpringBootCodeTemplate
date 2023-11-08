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
 * 系统访问记录
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-11-08
 */
@Getter
@Setter
@TableName("system_login_log")
@ApiModel(value = "SystemLoginLog对象", description = "系统访问记录")
public class SystemLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访问ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("日志类型")
    @TableField("log_type")
    private Integer logType;

    @ApiModelProperty("链路追踪编号")
    @TableField("trace_id")
    private String traceId;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("用户类型")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty("用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("登陆结果")
    @TableField("result")
    private Integer result;

    @ApiModelProperty("用户 IP")
    @TableField("user_ip")
    private String userIp;

    @ApiModelProperty("浏览器 UA")
    @TableField("user_agent")
    private String userAgent;

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

    @ApiModelProperty("租户编号")
    @TableField("tenant_id")
    private Long tenantId;
}
