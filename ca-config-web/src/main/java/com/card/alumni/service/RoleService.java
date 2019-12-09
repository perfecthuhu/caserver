package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaRole;
import com.card.alumni.model.RoleModel;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.request.RoleRequest;

/**
 * @author liumingyu
 * @date 2019-12-09 9:36 PM
 */
public interface RoleService {

    Integer save(RoleRequest request);

    void update(RoleRequest request);

    void deleteById(Integer id);

    CaRole findById(Integer id);

    PageData<RoleModel> pageByKeyword(RoleQueryRequest request);
}
