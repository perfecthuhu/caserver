package com.card.alumni.service;

import com.card.alumni.entity.CaRecommend;
import com.card.alumni.exception.CaException;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-21 1:08 AM
 */
public interface RecommendService {

    Integer save(CaRecommend recommend) throws CaException;

    void batchSave(List<CaRecommend> recommendList) throws CaException;

    void batchSave(Integer userId, List<Integer> refIdList) throws CaException;

    void update(CaRecommend recommend) throws CaException;

    void browse(Integer id) throws CaException;

    CaRecommend findById(Integer id) throws CaException;

    List<CaRecommend> listTodayByUserId(Integer userId) throws CaException;

    List<CaRecommend> listBeforeDaysByUserId(Integer userId, Integer days) throws CaException;
}
