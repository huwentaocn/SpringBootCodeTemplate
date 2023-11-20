package com.wx.manage.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wx.manage.constant.UserTypeEnum;
import com.wx.manage.handler.type.EncryptTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-08-31
 */
@Getter
@Setter
@TableName(value = "wx_user", autoResultMap = true)
@ApiModel(value = "WxUser对象", description = "用户表")
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("密码")
    @TableField(value = "password", typeHandler = EncryptTypeHandler.class)
    private String password;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("微信openid")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty("性别：1男，2女，默认1")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty("头像")
    @TableField("head_url")
    private String headUrl;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("用户类型：1管理员，2普通用户，默认2")
    @TableField("user_type")
    private UserTypeEnum userTypeEnum;

    @ApiModelProperty("逻辑删除：0未删除，1删除，默认0")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

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
