package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.RoleBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 角色更新请求体
 * @Date 2023/11/13 10:07 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "RoleUpdateReq", description = "角色更新请求体")
public class RoleUpdateReq extends RoleBaseVo {
    
    @ApiModelProperty(value = "角色编号", required = true, example = "1024")
    @NotNull(message = "角色编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态,见 CommonStatusEnum 枚举", required = true, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;
}
