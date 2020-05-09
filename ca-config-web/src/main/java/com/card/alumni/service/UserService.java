package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserRoleRelation;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.UserModel;
import com.card.alumni.request.UserQueryRequest;
import com.card.alumni.request.UserRequest;

import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @date 2019-12-15 1:43 PM
 */
public interface UserService {

    Integer save(UserRequest request) throws CaConfigException;

    void update(UserRequest request) throws CaConfigException;

    void updatePwd(Integer id, String password) throws CaConfigException;

    void review(Integer id, Integer yn) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    CaUser findById(Integer id) throws CaConfigException;

    CaUser findByPhone(String phone) throws CaConfigException;

    UserModel findModelById(Integer id) throws CaConfigException;

    List<CaUser> listByIdList(List<Integer> idList) throws CaConfigException;

    List<UserModel> listModelByIdList(List<Integer> idList) throws CaConfigException;

    Map<Integer, CaUser> mapByIdList(List<Integer> idList) throws CaConfigException;

    PageData<UserModel> pageByRequest(UserQueryRequest request) throws CaConfigException;


    void batchSaveUserRoleRel(Integer userId, List<Integer> roleIdList) throws CaConfigException;

    void deleteUserRoleRelByUserId(Integer userId) throws CaConfigException;

    List<CaUserRoleRelation> listUserRoleRelByUserId(Integer userId) throws CaConfigException;

    List<Integer> listRoleIdsByUserId(Integer userId) throws CaConfigException;

    List<CaUserRoleRelation> listUserRoleRelByRoleId(Integer roleId) throws CaConfigException;

    List<UserModel> convert2ModelList(List<CaUser> userList);
}
