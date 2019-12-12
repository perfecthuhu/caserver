package com.card.alumni.vo.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 8:45 PM
 */
@Getter
@Setter
public class PageParam {
    /**
     * 当前页码
     */
    private Integer currentPage = 1;
    /**
     * 页面大小
     */
    private Integer pageSize = 10;
}
