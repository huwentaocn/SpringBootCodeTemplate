package com.wx.manage.pojo.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@ApiModel(description="分页参数")
@Data
public class PageParam implements Serializable {

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * 页码
     */
    @Max(Integer.MAX_VALUE)
    @Min(Integer.MIN_VALUE)
    @ApiModelProperty(name = "pageNum",value ="页码(默认1)",position = 1)
    private Integer pageNo = PAGE_NO;

    /**
     * 每页条数
     */
    @Max(Integer.MAX_VALUE)
    @Min(Integer.MIN_VALUE)
    @ApiModelProperty(name = "pageSize",value ="每页条数(默认10)",position = 3)
    private Integer pageSize = PAGE_SIZE;


    /**
     * 模糊查询字段
     */
    @ApiModelProperty(name = "queryName",value ="模糊查询字段",position = 5)
    private String queryName;


}
