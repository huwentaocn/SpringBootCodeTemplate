package com.wx.manage.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 文件信息类
 * @Date 2023/8/31 17:10 星期四
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "FileInfo", description = "文件信息类")
public class FileInfo {

    @ApiModelProperty("唯一id")
    private Long id;

    @ApiModelProperty("文件md5")
    private String md5;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件类型")
    private String fileType;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("文件存储地址")
    private String location;

    @ApiModelProperty("文件访问地址")
    private String accLocation;

}
