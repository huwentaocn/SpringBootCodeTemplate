package com.wx.manage.pojo.resp;

import com.wx.manage.pojo.vo.DataSourceConfigBaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 数据源配置响应体
 * @Date 2023/11/8 16:59 星期三
 * @Author Hu Wentao
 */

@Data
@ApiModel(value = "DataSourceConfigResp", description = "数据源配置响应体")
public class DataSourceConfigResp extends DataSourceConfigBaseVo {

    @ApiModelProperty(name = "主键编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(name = "创建时间", required = true)
    private LocalDateTime createTime;
}
