package com.card.alumni.service;

import com.card.alumni.entity.CaUserTag;
import com.card.alumni.exception.CaException;

/**
 * @author liumingyu
 * @date 2019-12-29 10:58 AM
 */
public interface UserTagService {

    CaUserTag findByUserId(Integer userId) throws CaException;
}
