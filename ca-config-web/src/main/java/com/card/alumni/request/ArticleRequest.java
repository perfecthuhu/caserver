package com.card.alumni.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liumingyu
 * @date 2019-12-10 8:00 PM
 */
@Data
public class ArticleRequest implements Serializable {

    private Integer id;

    private String title;

    private String subTitle;

    private Integer type;

    private String content;
}
