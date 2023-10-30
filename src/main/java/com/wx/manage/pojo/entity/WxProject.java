package com.wx.manage.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-09-05
 */
@Getter
@Setter
@TableName("wx_project")
@ApiModel(value = "WxProject对象", description = "项目表")
public class WxProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("项目名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("合同编号")
    @TableField("contract_number")
    private String contractNumber;

    @ApiModelProperty("项目部署的平台id，数组，“,”隔开")
    @TableField("pf_id")
    private String pfId;

    @ApiModelProperty("外网ip")
    @TableField("outnet_ip")
    private String outnetIp;

    @ApiModelProperty("内网ip")
    @TableField("intranet_ip")
    private String intranetIp;

    @ApiModelProperty("域名")
    @TableField("domain_name")
    private String domainName;

    @ApiModelProperty("公钥")
    @TableField("public_key")
    private String publicKey;

    @ApiModelProperty("私钥")
    @TableField("private_key")
    private String privateKey;

    @ApiModelProperty("对称密钥")
    @TableField("symmetric_key")
    private String symmetricKey;

    @ApiModelProperty("排序字段")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("逻辑删除：0未删除，1删除，默认0")
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty("版本")
    @TableField("version")
    @Version
    private Integer version;

    @ApiModelProperty("创建者")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;

    @ApiModelProperty("数据创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新者")
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @ApiModelProperty("数据更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
