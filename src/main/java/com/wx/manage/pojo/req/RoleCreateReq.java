package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.RoleBaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description 角色创建请求体
 * @Date 2023/11/13 10:07 星期一
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "RoleCreateReq", description = "角色创建请求体")
public class RoleCreateReq extends RoleBaseVo {
}
