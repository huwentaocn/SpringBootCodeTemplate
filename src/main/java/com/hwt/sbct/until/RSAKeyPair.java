package com.hwt.sbct.until;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description RSA非对称密钥对象
 * @Date 2023/9/6 11:04 星期三
 * @Author Hu Wentao
 */

@ApiModel(value = "RSAKeyPair", description = "RSA非对称密钥对象")
@Data
public class RSAKeyPair {

    //公钥
    @ApiModelProperty(name = "publicKey", value = "公钥")
    private String publicKey;

    //私钥
    @ApiModelProperty(name = "privateKey", value = "私钥")
    private String privateKey;
}
