package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.TenantBaseVo;
import com.wx.manage.pojo.vo.TenantPackageBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 更新租户套餐请求体
 * @Date 2023/11/10 10:11 星期五
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "TenantPackageUpdateReq", description = "更新租户套餐请求体")
public class TenantPackageUpdateReq extends TenantPackageBaseVo {

    @ApiModelProperty(value = "租户套餐编号", required = true, example = "1024")
    @NotNull(message = "租户套餐编号不能为空")
    private Long id;

}
