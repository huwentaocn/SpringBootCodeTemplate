package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.UserBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @Description 创建用户请求对象
 * @Date 2023/11/13 11:06 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserCreateReq", description = "创建用户请求对象")
public class UserCreateReq extends UserBaseVo {

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;
}
