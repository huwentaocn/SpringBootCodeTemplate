package com.wx.manage.pojo.callVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 保存项目响应体
 * @Date 2023/9/6 16:00 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "SaveProjectResp", description = "保存项目响应体")
public class SaveProjectResp {

    @ApiModelProperty(value="对称密钥key(非对称公钥加密)")
    private String encryptedSymmetricKey;
}
