package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaSchool;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.SchoolModel;
import com.card.alumni.request.SchoolRequest;
import com.card.alumni.request.SchoolQueryRequest;

/**
 * @author liumingyu
 * @date 2019-12-15 1:45 PM
 */
public interface SchoolService {

    Integer save(SchoolRequest request) throws CaConfigException;

    void update(SchoolRequest request) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    CaSchool findById(Integer id) throws CaConfigException;

    SchoolModel findModelById(Integer id) throws CaConfigException;

    PageData<SchoolModel> pageByRequest(SchoolQueryRequest request) throws CaConfigException;
}
