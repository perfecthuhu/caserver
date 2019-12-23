package com.card.alumni.vo.query;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 7:54 PM
 */
@Getter
@Setter
public class ArticleQuery {

    private Integer id;

    private Integer type;

    private Boolean isPublish;

    private Integer publisher;

    private Date publishTime;

    private Integer sortType;

}
