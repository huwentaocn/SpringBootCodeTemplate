package com.wx.manage.pojo.resp;

import com.wx.manage.pojo.vo.RoleBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Description 角色响应对象
 * @Date 2023/11/13 10:11 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "RoleResp", description = "角色响应对象")
public class RoleResp extends RoleBaseVo {

    @ApiModelProperty(value = "角色编号", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "数据范围,参见 DataScopeEnum 枚举类", required = true, example = "1")
    private Integer dataScope;

    @ApiModelProperty(value = "数据范围(指定部门数组)", example = "1")
    private Set<Long> dataScopeDeptIds;

    @ApiModelProperty(value = "状态,参见 CommonStatusEnum 枚举类", required = true, example = "1")
    private Integer status;

    @ApiModelProperty(value = "角色类型,参见 RoleTypeEnum 枚举类", required = true, example = "1")
    private Integer type;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
