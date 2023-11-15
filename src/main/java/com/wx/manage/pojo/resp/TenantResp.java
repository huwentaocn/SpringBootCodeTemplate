package com.wx.manage.pojo.resp;

import com.wx.manage.pojo.vo.TenantBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 租户响应对象
 * @Date 2023/11/10 10:17 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "TenantResp", description = "租户响应对象")
public class TenantResp extends TenantBaseVo {

    @ApiModelProperty(value = "租户编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;
}
