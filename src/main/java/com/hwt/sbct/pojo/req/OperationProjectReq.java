package com.hwt.sbct.pojo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 操作项目请求体
 * @Date 2023/8/24 10:16 星期四
 * @Author Hu Wentao
 */

@Data
public class OperationProjectReq {

    @ApiModelProperty("唯一id")
    private Long id;

    @NotBlank(message = "项目名称必填")
    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("合同编号")
    private String contractNumber;

    @ApiModelProperty("项目部署的平台id，数组，“,”隔开")
    private String pfId;

    @ApiModelProperty("外网ip")
    private String outnetIp;

    @ApiModelProperty("内网ip")
    private String intranetIp;

    @ApiModelProperty("域名")
    private String domainName;

    @ApiModelProperty("排序字段")
    private Integer sort;
}
