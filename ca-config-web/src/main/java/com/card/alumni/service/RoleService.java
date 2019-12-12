package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaRole;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.RoleModel;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.request.RoleRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-09 9:36 PM
 */
public interface RoleService {

    Integer save(RoleRequest request) throws CaConfigException;

    void update(RoleRequest request) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    CaRole findById(Integer id) throws CaConfigException;

    RoleModel findModelById(Integer id) throws CaConfigException;

    List<CaRole> listByIdList(List<Integer> idList) throws CaConfigException;

    PageData<RoleModel> pageByKeyword(RoleQueryRequest request) throws CaConfigException;


    void deleteRoleMenuRelByRoleId(Integer roleId) throws CaConfigException;

    List<Integer> listMenuIdsByRoleId(Integer roleId) throws CaConfigException;


    void deleteUserRoleRelByRoleId(Integer roleId) throws CaConfigException;

    List<Integer> listUserIdsByRoleId(Integer roleId) throws CaConfigException;

}
