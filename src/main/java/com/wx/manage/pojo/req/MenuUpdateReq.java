package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.MenuBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 更新菜单请求体
 * @Date 2023/11/21 13:20 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "MenuUpdateReq", description = "更新菜单请求体")
public class MenuUpdateReq extends MenuBaseVo {

    @ApiModelProperty(value = "菜单编号", required = true, example = "1024")
    @NotNull(message = "菜单编号不能为空")
    private Long id;
}
