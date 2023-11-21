package com.wx.manage.pojo.resp;

import com.wx.manage.pojo.vo.MenuBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 菜单响应对象
 * @Date 2023/11/21 13:21 星期二
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "MenuResp", description = "菜单响应对象")
public class MenuResp extends MenuBaseVo {


    @ApiModelProperty(value = "菜单编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "状态,参见 CommonStatusEnum 枚举类", required = true, example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;
}
