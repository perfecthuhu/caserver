package com.card.alumni.service;

import com.card.alumni.entity.CaUserTag;
import com.card.alumni.exception.CaException;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-20 10:39 PM
 */
public interface UserTagService {

    Integer save(CaUserTag userTag) throws CaException;

    void update(CaUserTag userTag) throws CaException;

    CaUserTag findById(Integer id) throws CaException;

    List<CaUserTag> listByUserIdList(List<Integer> userIdList) throws CaException;

    List<CaUserTag> listByTags(CaUserTag userTag) throws CaException;
}
