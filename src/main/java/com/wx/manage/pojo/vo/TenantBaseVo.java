package com.wx.manage.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Description 租户基础对象
 * @Date 2023/11/10 10:03 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "TenantBaseVo", description = "租户基础对象")
public class TenantBaseVo {

    @ApiModelProperty(value = "租户名", required = true, example = "芋道")
    @NotNull(message = "租户名不能为空")
    private String name;

    @ApiModelProperty(value = "联系人", required = true, example = "芋艿")
    @NotNull(message = "联系人不能为空")
    private String contactName;

    @ApiModelProperty(value = "联系手机", example = "15601691300")
    private String contactMobile;

    @ApiModelProperty(value = "租户状态", required = true, example = "1")
    @NotNull(message = "租户状态")
    private Integer status;

    @ApiModelProperty(value = "绑定域名", example = "https://www.iocoder.cn")
    private String domain;

    @ApiModelProperty(value = "租户套餐编号", required = true, example = "1024")
    @NotNull(message = "租户套餐编号不能为空")
    private Long packageId;

    @ApiModelProperty(value = "过期时间", required = true)
    @NotNull(message = "过期时间不能为空")
    private LocalDateTime expireTime;

    @ApiModelProperty(value = "账号数量", required = true, example = "1024")
    @NotNull(message = "账号数量不能为空")
    private Integer accountCount;

    @ApiModelProperty(value = "数据源配置编号", required = true, example = "4096")
    @NotNull(message = "数据源配置编号不能为空")
    private Long dataSourceConfigId;
}
