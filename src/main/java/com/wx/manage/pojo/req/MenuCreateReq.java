package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.MenuBaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description 创建菜单请求体
 * @Date 2023/11/21 13:19 星期二
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "MenuCreateReq", description = "创建菜单请求体")
public class MenuCreateReq extends MenuBaseVo {
}
