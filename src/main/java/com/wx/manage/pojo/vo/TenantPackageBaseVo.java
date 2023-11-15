package com.wx.manage.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Description 租户套餐包基础对象
 * @Date 2023/11/10 10:03 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "TenantPackageBaseVo", description = "租户套餐包基础对象")
public class TenantPackageBaseVo {

    @ApiModelProperty(value = "套餐名", required = true, example = "VIP")
    @NotNull(message = "套餐名不能为空")
    private String name;

    @ApiModelProperty(value = "状态,参见 CommonStatusEnum 枚举", required = true, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "备注", example = "好")
    private String remark;

    @ApiModelProperty(value = "关联的菜单编号", required = true)
    @NotNull(message = "关联的菜单编号不能为空")
    private Set<Long> menuIds;
}
