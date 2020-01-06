package com.card.alumni.service;

import com.card.alumni.entity.CaMenu;
import com.card.alumni.entity.CaRoleMenuRelation;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.MenuModel;
import com.card.alumni.request.MenuQueryRequest;
import com.card.alumni.request.MenuRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-10 7:57 PM
 */
public interface MenuService {

    Integer save(MenuRequest request) throws CaConfigException;

    void update(MenuRequest request) throws CaConfigException;

    void update(CaMenu menu) throws CaConfigException;

    void rankByParentId(Integer pid, List<Integer> children) throws CaConfigException;

    void deleteById(Integer id) throws CaConfigException;

    CaMenu findById(Integer id) throws CaConfigException;

    MenuModel findModelById(Integer id) throws CaConfigException;

    int getMaxRankByParentId(Integer pid) throws CaConfigException;

    List<CaMenu> listRankByParentId(Integer parentId) throws CaConfigException;

    List<CaMenu> listRankByIdList(List<Integer> idList) throws CaConfigException;

    List<MenuModel> listRankModelByIdList(List<Integer> idList) throws CaConfigException;

    List<MenuModel> listRankModelByParentId(Integer parentId) throws CaConfigException;

    List<MenuModel> listRankModelByRequest(MenuQueryRequest request) throws CaConfigException;

    List<MenuModel> buildMenuTree(List<MenuModel> menuList) throws CaConfigException;

    List<MenuModel> listAll() throws CaConfigException;


    void deleteRoleMenuRelByMenuId(Integer menuId) throws CaConfigException;

    List<CaRoleMenuRelation> listRoleMenuRelByMenuId(Integer menuId) throws CaConfigException;

    List<Integer> listRoleIdsByMenuId(Integer menuId) throws CaConfigException;

}
