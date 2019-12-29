package com.card.alumni.service;

import com.card.alumni.entity.CaRecommend;
import com.card.alumni.exception.CaException;
import com.card.alumni.vo.UserVO;

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

    /**
     * 推荐N个用户
     *
     * @param size 推荐数量
     * @return 用户列表
     * @throws CaException e
     */
    List<UserVO> recommend(Integer size) throws CaException;

    List<UserVO> recommendByRandom(Integer size) throws CaException;

    CaRecommend findById(Integer id) throws CaException;

    List<CaRecommend> listTodayByUserId(Integer userId) throws CaException;

    List<CaRecommend> listBeforeDaysByUserId(Integer userId, Integer days) throws CaException;
}
