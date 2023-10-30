package com.wx.manage.pojo.callVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 保存项目信息请求体
 * @Date 2023/9/6 11:33 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "SaveProjectReq", description = "保存项目信息请求体")
public class SaveProjectReq {

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    @ApiModelProperty(value="项目id")
    private Long projectId;

    /**
     * 非对称密钥公钥
     */
    @NotNull(message = "非对称密钥公钥不能为空")
    @ApiModelProperty(value="非对称密钥公钥")
    private String publicKey;

    @ApiModelProperty(value = "请求ip")
    private String host;
}
