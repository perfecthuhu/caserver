package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaActivity;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ActivityModel;
import com.card.alumni.request.ActivityQueryRequest;
import com.card.alumni.request.ActivityRequest;

/**
 * @author liumingyu
 * @date 2020-01-16 11:15 PM
 */
public interface ActivityService {

    Integer save(ActivityRequest request) throws CaException;

    void update(ActivityRequest request) throws CaException;

    void deleteById(Integer id) throws CaException;

    void publish(Integer id) throws CaException;

    Integer saveAndPublish(ActivityRequest request) throws CaException;

    void retract(Integer id) throws CaException;

    CaActivity findById(Integer id) throws CaException;

    ActivityModel findModelById(Integer id) throws CaException;

    PageData<ActivityModel> pageByRequest(ActivityQueryRequest request) throws CaException;
}
