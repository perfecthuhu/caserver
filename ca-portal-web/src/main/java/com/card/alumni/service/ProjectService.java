package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaProject;
import com.card.alumni.model.ProjectModel;
import com.card.alumni.request.ProjectQueryRequest;
import com.card.alumni.request.ProjectRequest;

/**
 * @author liumingyu
 * @date 2019-12-10 7:57 PM
 */
public interface ProjectService {

    Integer save(ProjectRequest request);

    void update(ProjectRequest request);

    void update(CaProject article);

    void deleteById(Integer id);

    void publish(Integer id);

    void unPublish(Integer id);

    Long increaseViewCount(Integer id);

    CaProject findById(Integer id);

    ProjectModel findModelById(Integer id);

    PageData<ProjectModel> pageByRequest(ProjectQueryRequest request);

    PageData<ProjectModel> pageByMyRelease(ProjectQueryRequest request);
}
