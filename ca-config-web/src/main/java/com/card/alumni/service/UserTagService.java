package com.card.alumni.service;

import com.card.alumni.entity.CaUserTag;
import com.card.alumni.exception.CaConfigException;

/**
 * @author liumingyu
 * @date 2019-12-29 10:58 AM
 */
public interface UserTagService {

    Integer save(CaUserTag userTag) throws CaConfigException;

    void update(CaUserTag userTag) throws CaConfigException;

    CaUserTag findByUserId(Integer userId) throws CaConfigException;
}
