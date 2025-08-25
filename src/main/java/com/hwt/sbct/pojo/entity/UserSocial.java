package com.hwt.sbct.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 社交用户表
 * </p>
 *
 * @author Hu Wentao
 * @since 2025-08-21
 */
@Getter
@Setter
@TableName("user_social")
@ApiModel(value = "UserSocial对象", description = "社交用户表")
public class UserSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("社交平台类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("平台用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("社交 openid")
    @TableField("openid")
    private String openid;

    @ApiModelProperty("社交 token")
    @TableField("token")
    private String token;

    @ApiModelProperty("原始Token数据，一般是json格式")
    @TableField("raw_token_info")
    private String rawTokenInfo;

    @ApiModelProperty("社交平台用户id")
    @TableField("uuid")
    private String uuid;

    @ApiModelProperty("社交平台用户账号")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("用户昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("社交平台用户性别：1男，2女，0未知，默认0")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("社交平台用户博客")
    @TableField("blog")
    private String blog;

    @ApiModelProperty("社交平台用户公司")
    @TableField("company")
    private String company;

    @ApiModelProperty("社交平台用户位置")
    @TableField("location")
    private String location;

    @ApiModelProperty("社交平台用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("原始用户数据，一般是json格式")
    @TableField("raw_user_info")
    private String rawUserInfo;

    @ApiModelProperty("最后一次认证的code")
    @TableField("code")
    private String code;

    @ApiModelProperty("最后一次认证的state")
    @TableField("state")
    private String state;

    @ApiModelProperty("账号状态（0正常，1停用），默认0")
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
