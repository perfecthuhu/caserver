package com.card.alumni.service;

import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;

import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @date 2019-12-21 12:24 PM
 */
public interface UserLocalService {

    Integer save(CaUser user) throws CaException;

    void update(CaUser user) throws CaException;

    CaUser findById(Integer id) throws CaException;

    CaUser findByPhone(String phone) throws CaException;

    List<CaUser> listByIdList(List<Integer> idList) throws CaException;

    Map<Integer, CaUser> mapByIdList(List<Integer> idList) throws CaException;

    List<CaUser> listByNotIsUserId(Integer userId) throws CaException;
}
