package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaArticle;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.ArticleModel;
import com.card.alumni.request.ArticleQueryRequest;
import com.card.alumni.request.ArticleRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-10 7:57 PM
 */
public interface ArticleService {

    Integer save(ArticleRequest request) throws CaConfigException;

    void update(ArticleRequest request) throws CaConfigException;

    void update(CaArticle article) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    void publish(Integer id) throws CaConfigException;

    void unPublish(Integer id) throws CaConfigException;

    CaArticle findById(Integer id) throws CaConfigException;

    ArticleModel findModelById(Integer id) throws CaConfigException;

    PageData<ArticleModel> pageByRequest(ArticleQueryRequest request) throws CaConfigException;

    void topping(Integer id);

    void unpin(Integer id);

    List<ArticleModel> listTopByRequest(ArticleQueryRequest request);
}
