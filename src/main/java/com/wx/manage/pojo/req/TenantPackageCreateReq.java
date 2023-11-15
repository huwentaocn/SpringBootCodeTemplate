package com.wx.manage.pojo.req;

import com.wx.manage.pojo.vo.TenantBaseVo;
import com.wx.manage.pojo.vo.TenantPackageBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Description 创建租户套餐请求体
 * @Date 2023/11/10 10:10 星期五
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "TenantPackageCreateReq", description = "创建租户套餐请求体")
public class TenantPackageCreateReq extends TenantPackageBaseVo {

}
