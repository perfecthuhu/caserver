package com.card.alumni.vo;

import java.util.List;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 8:42 PM
 */
public class Page<T> {
    /**
     * 初始化size
     */
    public final static int DEFAULT_PAGE_SIZE = 10;
    /**
     * 页码
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 总数
     */
    private int totalCount;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 数据
     */
    private List<T> data;

    public Page() {
    }

    public Page(int currentPage) {
        this.currentPage = currentPage;
    }

    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    /**
     * 获取开始索引
     *
     * @return
     */
    public int getStartIndex() {
        return (getCurrentPage() - 1) * this.pageSize;
    }

    /**
     * 获取结束索引
     *
     * @return
     */
    public int getEndIndex() {
        return getCurrentPage() * this.pageSize;
    }

    /**
     * 是否第一页
     *
     * @return
     */
    public boolean isFirstPage() {
        return getCurrentPage() <= 1;
    }

    /**
     * 是否末页
     *
     * @return
     */
    public boolean isLastPage() {
        return getCurrentPage() >= getPageCount();
    }

    /**
     * 获取下一页页码
     *
     * @return
     */
    public int getNextPage() {
        if (isLastPage()) {
            return getCurrentPage();
        }
        return getCurrentPage() + 1;
    }

    /**
     * 获取上一页页码
     *
     * @return
     */
    public int getPreviousPage() {
        if (isFirstPage()) {
            return 1;
        }
        return getCurrentPage() - 1;
    }

    /**
     * 获取当前页页码
     *
     * @return
     */
    public int getCurrentPage() {
        if (currentPage <= 0) {
            currentPage = 1;
        }
        return currentPage;
    }

    /**
     * 取得总页数
     *
     * @return
     */
    public int getPageCount() {
        if (totalCount % pageSize == 0) {
            return totalCount / pageSize;
        } else {
            return totalCount / pageSize + 1;
        }
    }



    /**
     * 该页是否有下一页.
     *
     * @return
     */
    public boolean hasNextPage() {
        return getCurrentPage() < getPageCount();
    }

    /**
     * 该页是否有上一页.
     *
     * @return
     */
    public boolean hasPreviousPage() {
        return getCurrentPage() > 1;
    }

    /**
     * 获取数据集
     *
     * @return
     */
    public List<T> getResult() {
        return data;
    }

    /**
     * 设置数据集
     *
     * @param data
     */
    public void setResult(List<T> data) {
        this.data = data;
    }
}
