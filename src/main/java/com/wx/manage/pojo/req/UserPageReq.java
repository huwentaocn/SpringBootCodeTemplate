package com.wx.manage.pojo.req;

import com.wx.manage.pojo.page.PageParam;
import com.wx.manage.until.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.wx.manage.until.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @Description 用户分页请求体
 * @Date 2023/11/10 10:38 星期五
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "UserPageReq", description = "用户分页请求体")
public class UserPageReq extends PageParam {

    @ApiModelProperty(value = "用户账号,模糊匹配", example = "yudao")
    private String username;

    @ApiModelProperty(value = "手机号码,模糊匹配", example = "yudao")
    private String mobile;

    @ApiModelProperty(value = "展示状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @ApiModelProperty(value = "部门编号,同时筛选子部门", example = "1024")
    private Long deptId;
}
