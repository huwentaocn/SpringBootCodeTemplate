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
 * oss文件表
 * </p>
 *
 * @author Hu Wentao
 * @since 2023-09-01
 */
@Getter
@Setter
@TableName("oss_file")
@ApiModel(value = "OssFile对象", description = "oss文件表")
public class OssFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("唯一id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("文件md5")
    @TableField("md5")
    private String md5;

    @ApiModelProperty("文件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty("文件类型")
    @TableField("file_type")
    private String fileType;

    @ApiModelProperty("文件大小")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty("文件存储地址")
    @TableField("location")
    private String location;

    @ApiModelProperty("文件访问地址")
    @TableField("acc_location")
    private String accLocation;

    @ApiModelProperty("使用计数")
    @TableField("use_count")
    private Integer useCount;

    @ApiModelProperty("逻辑删除：0未删除，1已删除，默认0")
    @TableField("id_delete")
    private Integer idDelete;

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
