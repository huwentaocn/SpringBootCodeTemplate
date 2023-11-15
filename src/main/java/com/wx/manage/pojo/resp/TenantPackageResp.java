package com.wx.manage.pojo.resp;

import com.wx.manage.pojo.vo.TenantPackageBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 租户套餐响应对象
 * @Date 2023/11/10 10:17 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "TenantPackageResp", description = "租户套餐响应对象")
public class TenantPackageResp extends TenantPackageBaseVo {

    @ApiModelProperty(value = "租户套餐编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;
}
