package com.hwt.sbct.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

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

    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("性别：1男，2女， 默认1")
    private Integer sex;

    @ApiModelProperty("头像url")
    private String headUrl;

    @ApiModelProperty("账号状态（0正常，1停用），默认0")
    private Integer status;

    @ApiModelProperty("电话号码")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("最后登录ip")
    private String lastLoginIp;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("排序")
    private Integer sort;
}
