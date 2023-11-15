package com.wx.manage.pojo.resp;

import com.wx.manage.pojo.vo.UserBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 用户响应对象
 * @Date 2023/11/13 11:09 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserResp", description = "用户响应对象")
public class UserResp extends UserBaseVo {

    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "状态,参见 CommonStatusEnum 枚举类", required = true, example = "1")
    private Integer status;

    @ApiModelProperty(value = "最后登录 IP", required = true, example = "192.168.1.1")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间", required = true, example = "时间戳格式")
    private LocalDateTime loginDate;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;
}
