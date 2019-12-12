package com.card.alumni.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 9:37 PM
 */
@Getter
@Setter
public class ArticleVO implements Serializable {

    private Integer id;

    private String title;

    private String subTitle;

    private Integer type;

    private Integer publisher;

    private Date publishTime;

    private String content;
}
