package com.card.alumni.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author liumingyu
 * @date 2019-11-19 10:32 PM
 */
@Accessors(chain = true)
@ApiModel(value = "分页结果", description = "分页结果")
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 99532487290231271L;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private long totalCount = 0;

    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集")
    private List<T> items;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public PageData() {
    }

    public PageData(long totalCount, List<T> items) {
        this.totalCount = totalCount;
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "totalCount=" + totalCount +
                ", items=" + items +
                '}';
    }
}
