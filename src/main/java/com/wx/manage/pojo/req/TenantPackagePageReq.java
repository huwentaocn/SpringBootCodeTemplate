package com.wx.manage.pojo.req;

import com.wx.manage.pojo.page.PageParam;
import com.wx.manage.until.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Description 租户套餐分页请求体
 * @Date 2023/11/10 10:38 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "TenantPackagePageReq", description = "租户套餐分页请求体")
public class TenantPackagePageReq extends PageParam {

    @ApiModelProperty(value = "套餐名", example = "VIP")
    private String name;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "备注", example = "好")
    private String remark;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime[] createTime;
}
