package com.hwt.sbct.pojo.resp;

import com.hwt.sbct.pojo.vo.UserInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 用户登录响应体
 * @Date 2023/8/28 15:43 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserLoginResp", description = "用户登录响应体")
public class UserLoginResp {

    @ApiModelProperty(name = "userResp", value = "用户信息")
    private UserInfoVo userInfoVo;

    @ApiModelProperty(name = "token", value = "token")
    private String token;

}
