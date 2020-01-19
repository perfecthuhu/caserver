package com.card.alumni.vo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunxiaodong10 2020/1/19
 * @date 8:58 PM
 */
@AllArgsConstructor
@Getter
public enum ArticleTypeEnum {

    NEWS(1, "新闻"),
    BULLETIN(2, "公告")
    ;

    private Integer code;
    private String desc;
}
