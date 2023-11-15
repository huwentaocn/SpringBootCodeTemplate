package com.wx.manage.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 租户excel导出对象
 * @Date 2023/11/10 10:47 星期五
 * @Author Hu Wentao
 */
@Data
@ApiModel(value = "TenantExcelVo", description = "租户excel导出对象")
public class TenantExcelVo {

    @ExcelProperty("租户编号")
    private Long id;

    @ExcelProperty("租户名")
    private String name;

    @ExcelProperty("联系人")
    private String contactName;

    @ExcelProperty("联系手机")
    private String contactMobile;

    @ExcelProperty(value = "状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}
