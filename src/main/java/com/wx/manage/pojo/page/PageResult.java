package com.wx.manage.pojo.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "分页结果")
@Data
public final class PageResult<T> implements Serializable {
    @ApiModelProperty("总条数")
    private long total;

    @ApiModelProperty("每页条数")
    private long pageNum;

    @ApiModelProperty("当前页")
    private long pageSize;

    @ApiModelProperty("总页数")
    private long pages;


    @ApiModelProperty("查询数据列表")
    private List<T> list;

    @ApiModelProperty("是否存在上一页")
    private boolean hasPreviousPage;

    @ApiModelProperty("是否存在下一页")
    private boolean hasNextPage;

    /**
     * 结果赋值
     */
    public PageResult(Page<T> page) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.list = page.getRecords();
        this.hasPreviousPage = page.hasPrevious();
        this.hasNextPage = page.hasNext();
    }

    /**
     * 结果赋值
     */
    public PageResult(Page page, List<T> list) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.list = list;
        this.hasPreviousPage = page.hasPrevious();
        this.hasNextPage = page.hasNext();
    }

}
