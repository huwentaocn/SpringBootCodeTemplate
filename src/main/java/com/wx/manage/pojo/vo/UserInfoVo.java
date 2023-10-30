package com.wx.manage.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 用户信息响应体
 * @Date 2023/8/28 15:46 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserInfoVo", description = "用户信息响应体")
public class UserInfoVo {

    @ApiModelProperty("唯一id")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("微信openid")
    private String openId;

    @ApiModelProperty("性别：1男，2女，默认1")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("头像")
    private String headUrl;

    @ApiModelProperty("排序")
    private Integer sort;
}
