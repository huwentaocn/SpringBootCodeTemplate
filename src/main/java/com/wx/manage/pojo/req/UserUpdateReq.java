package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.UserBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 更新用户请求体
 * @Date 2023/11/13 11:07 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserUpdateReq", description = "更新用户请求体")
public class UserUpdateReq extends UserBaseVo {


    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态,见 CommonStatusEnum 枚举", required = true, example = "1")
    @NotNull(message = "状态不能为空")
//    @InEnum(value = CommonStatusEnum.class, message = "修改状态必须是 {value}")
    private Integer status;

}
