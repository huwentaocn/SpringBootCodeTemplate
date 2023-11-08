package com.wx.manage.pojo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "管理后台 - 用户精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResp {

    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value  = "用户昵称", required = true, example = "昵称")
    private String nickname;

}