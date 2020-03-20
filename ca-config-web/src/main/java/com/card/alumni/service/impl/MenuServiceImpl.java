package com.card.alumni.service.impl;

import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaMenuMapper;
import com.card.alumni.dao.CaRoleMenuRelationMapper;
import com.card.alumni.entity.CaMenu;
import com.card.alumni.entity.CaMenuExample;
import com.card.alumni.entity.CaRoleMenuRelation;
import com.card.alumni.entity.CaRoleMenuRelationExample;
import com.card.alumni.enums.MenuTargetEnum;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.MenuModel;
import com.card.alumni.request.MenuQueryRequest;
import com.card.alumni.request.MenuRequest;
import com.card.alumni.service.MenuService;
import com.card.alumni.utils.RequestUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单服务
 *
 * @author liumingyu
 * @date 2019-12-10 7:57 PM
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final int MENU_NAME_MAX_LEN = 50;

    private static final int DEFAULT_PARENT_ID = 0;

    @Autowired
    private CaMenuMapper caMenuMapper;

    @Autowired
    private CaRoleMenuRelationMapper caRoleMenuRelationMapper;

    @Override
    public Integer save(MenuRequest request) throws CaConfigException {
        checkParam(request);

        if (checkNameExist(request)) {
            throw new CaConfigException("菜单名称已经存在");
        }

        CaMenu menu = convert(request);

        //新增子层级的特殊校验
        if (menu.getPid() != DEFAULT_PARENT_ID) {
            CaMenu parentMenu = findById(menu.getPid());

            //是否已删除
            if (Objects.isNull(parentMenu) || parentMenu.getIsDelete()) {
                throw new CaConfigException("父层级不存在或已删除");
            }
        }

        int rank = getMaxRankByParentId(menu.getPid());

        menu.setSort(rank);

        Date now = new Date();
        menu.setCreateTime(now);
        menu.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        menu.setCreator(userId);
        menu.setUpdater(userId);
        menu.setIsDelete(Boolean.FALSE);

        caMenuMapper.insert(menu);

        return menu.getId();
    }

    @Override
    public void update(MenuRequest request) throws CaConfigException {
        checkParam(request);

        if (checkNameExist(request)) {
            throw new CaConfigException("菜单名称已经存在");
        }

        CaMenu menu = convert(request);

        update(menu);
    }

    @Override
    public void update(CaMenu menu) throws CaConfigException {
        if (Objects.isNull(menu.getId())) {
            throw new CaConfigException("菜单ID不能为空");
        }

        menu.setUpdateTime(new Date());
        menu.setUpdater(RequestUtil.getUserId());

        caMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void rankByParentId(Integer pid, List<Integer> children) throws CaConfigException {
        if (Objects.isNull(pid)) {
            throw new CaConfigException("父ID不能为空");
        }
        if (CollectionUtils.isEmpty(children)) {
            throw new CaConfigException("列表不能为空");
        }

        List<Integer> menuIdList = children.stream().distinct()
                .filter(Objects::nonNull).collect(Collectors.toList());

        List<CaMenu> menuList = listRankByParentId(pid);
        if (children.size() != menuList.size()) {
            throw new CaConfigException("当前列表数据已过期，请刷新后重试");
        }

        List<CaMenu> sortMenuList = sortMenus(menuList, menuIdList);

        final Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        for (int i = 0; i < menuList.size(); i++) {
            CaMenu menu = sortMenuList.get(i);

            menu.setSort(i + 1);
            menu.setUpdater(userId);
            menu.setUpdateTime(now);
        }

        caMenuMapper.batchUpdate(sortMenuList);
    }

    private List<CaMenu> sortMenus(List<CaMenu> menuList, List<Integer> children) throws CaConfigException {
        if (CollectionUtils.isEmpty(menuList) || CollectionUtils.isEmpty(children)) {
            return Lists.newArrayList();
        }

        List<Integer> tempMenuIds = Lists.newArrayList(children);
        for (CaMenu menu : menuList) {
            if (Objects.isNull(menu)) {
                continue;
            }
            if (!children.contains(menu.getId())) {
                throw new CaConfigException("当前列表数据已过期，请刷新后重试");
            }
            tempMenuIds.remove(menu.getId());
        }

        if (CollectionUtils.isNotEmpty(tempMenuIds)) {
            throw new CaConfigException("当前列表数据已过期，请刷新后重试");
        }

        Map<Integer, CaMenu> menuMap = menuList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(CaMenu::getId, Function.identity(), (k1, k2) -> k2));

        return children.stream().filter(Objects::nonNull)
                .filter(menuId -> Objects.nonNull(menuMap.get(menuId)))
                .map(menuMap::get).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaMenu menu = findById(id);

        if (Objects.isNull(menu) || menu.getIsDelete()) {
            throw new CaConfigException("当前菜单不存在或已被删除");
        }

        menu.setIsDelete(Boolean.TRUE);
        menu.setUpdateTime(new Date());
        menu.setUpdater(RequestUtil.getUserId());

        caMenuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public CaMenu findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("菜单ID不能为空");
        }

        return caMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public MenuModel findModelById(Integer id) throws CaConfigException {

        CaMenu menu = findById(id);

        return convert2Model(menu);
    }

    @Override
    public int getMaxRankByParentId(Integer pid) throws CaConfigException {
        Integer maxRank = caMenuMapper.getMaxRankByParentId(pid);
        return maxRank < CaConstants.ZERO ? CaConstants.ZERO : maxRank;
    }

    @Override
    public List<CaMenu> listRankByParentId(Integer parentId) throws CaConfigException {
        if (Objects.isNull(parentId) || parentId < 0) {
            parentId = 0;
        }

        CaMenuExample example = new CaMenuExample();
        CaMenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(parentId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        example.setOrderByClause("sort asc");

        return caMenuMapper.selectByExample(example);
    }

    @Override
    public List<CaMenu> listRankByIdList(List<Integer> idList) throws CaConfigException {
        if (CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayList();
        }

        CaMenuExample example = new CaMenuExample();
        CaMenuExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idList);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        example.setOrderByClause("sort asc");

        return caMenuMapper.selectByExample(example);
    }

    @Override
    public List<MenuModel> listRankModelByIdList(List<Integer> idList) throws CaConfigException {

        List<CaMenu> menuList = listRankByIdList(idList);

        return convert2ModelList(menuList);
    }

    @Override
    public List<MenuModel> listRankModelByParentId(Integer parentId) throws CaConfigException {

        List<CaMenu> menuList = listRankByParentId(parentId);

        return convert2ModelList(menuList);
    }

    @Override
    public List<MenuModel> listRankModelByRequest(MenuQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("菜单查询请求不能为空");
        }
        CaMenuExample example = new CaMenuExample();
        CaMenuExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getType())) {
            criteria.andTypeEqualTo(request.getType());
        }
        if (Objects.nonNull(request.getPid())) {
            criteria.andPidEqualTo(request.getPid());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        example.setOrderByClause("sort asc");

        List<CaMenu> menuList = caMenuMapper.selectByExample(example);

        return convert2ModelList(menuList);
    }

    @Override
    public List<MenuModel> buildMenuTree(List<MenuModel> menuList) throws CaConfigException {
        List<MenuModel> trees = new LinkedList<>();
        for (MenuModel menu : menuList) {
            if (menu.getPid() == 0) {
                trees.add(menu);
            }
            for (MenuModel it : menuList) {
                if (it.getPid().equals(menu.getId())) {
                    if (menu.getChildren() == null) {
                        menu.setChildren(new LinkedList<>());
                    }
                    menu.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    @Override
    public List<MenuModel> listAll() throws CaConfigException {
        CaMenuExample example = new CaMenuExample();
        CaMenuExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        List<CaMenu> menuList = caMenuMapper.selectByExample(example);

        return convert2ModelList(menuList);
    }


    @Override
    public void deleteRoleMenuRelByMenuId(Integer menuId) throws CaConfigException {
        List<CaRoleMenuRelation> relationList = listRoleMenuRelByMenuId(menuId);
        if (CollectionUtils.isEmpty(relationList)) {
            return;
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        relationList.forEach(relation -> {
            relation.setUpdater(userId);
            relation.setUpdateTime(now);
            relation.setIsDelete(Boolean.TRUE);
        });

        caRoleMenuRelationMapper.batchUpdate(relationList);
    }

    @Override
    public List<CaRoleMenuRelation> listRoleMenuRelByMenuId(Integer menuId) throws CaConfigException {
        if (Objects.isNull(menuId)) {
            throw new CaConfigException("菜单ID不能为空");
        }

        CaRoleMenuRelationExample example = new CaRoleMenuRelationExample();
        CaRoleMenuRelationExample.Criteria criteria = example.createCriteria();
        criteria.andMenuIdEqualTo(menuId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caRoleMenuRelationMapper.selectByExample(example);
    }

    @Override
    public List<Integer> listRoleIdsByMenuId(Integer menuId) throws CaConfigException {

        List<CaRoleMenuRelation> relationList = listRoleMenuRelByMenuId(menuId);
        if (CollectionUtils.isEmpty(relationList)) {
            return Lists.newArrayList();
        }

        return relationList.stream().filter(Objects::nonNull)
                .map(CaRoleMenuRelation::getRoleId).collect(Collectors.toList());
    }

    /**
     * 校验菜单名称是否存在
     * 刨除了当前ID的名称
     *
     * @param request 菜单请求
     * @return true-存在 false-不存在
     */
    private boolean checkNameExist(MenuRequest request) {
        CaMenuExample example = new CaMenuExample();
        CaMenuExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andNameEqualTo(request.getName());
        if (Objects.nonNull(request.getId())) {
            criteria.andIdNotEqualTo(request.getId());
        }
        List<CaMenu> roleList = caMenuMapper.selectByExample(example);

        return CollectionUtils.isNotEmpty(roleList);
    }

    private void checkParam(MenuRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("菜单请求不能为空");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new CaConfigException("菜单名称不能为空");
        }
        if (request.getName().length() > MENU_NAME_MAX_LEN) {
            throw new CaConfigException("菜单名称长度不能超过" + MENU_NAME_MAX_LEN);
        }
        if (Objects.isNull(request.getType())) {
            throw new CaConfigException("菜单类型不能为空");
        }
        if (StringUtils.isBlank(request.getPermission())) {
            throw new CaConfigException("菜单权限不能为空");
        }
        if (Objects.isNull(request.getVisible())) {
            throw new CaConfigException("菜单是否可见不能为空");
        }
        if (StringUtils.isBlank(request.getTarget())) {
            throw new CaConfigException("菜单打开方式不能为空");
        }
        MenuTargetEnum targetEnum = MenuTargetEnum.getEnumByType(request.getTarget());
        if (Objects.isNull(targetEnum)) {
            throw new CaConfigException("非法的菜单打开方式");
        }
        if (Objects.isNull(request.getPid()) || request.getPid() < DEFAULT_PARENT_ID) {
            request.setPid(DEFAULT_PARENT_ID);
        }
    }

    private List<MenuModel> convert2ModelList(List<CaMenu> menuList) {
        if (CollectionUtils.isEmpty(menuList)) {
            return Lists.newArrayList();
        }

        return menuList.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private MenuModel convert2Model(CaMenu menu) {
        if (Objects.isNull(menu)) {
            return null;
        }
        MenuModel model = new MenuModel();
        model.setId(menu.getId());
        model.setName(menu.getName());
        model.setType(menu.getType());
        model.setIcon(menu.getIcon());
        model.setPath(menu.getPath());
        model.setPid(menu.getPid());
        model.setSort(menu.getSort());
        model.setTarget(menu.getTarget());
        model.setVisible(menu.getVisible());
        model.setPermission(menu.getPermission());
        model.setCreator(menu.getCreator());
        model.setCreateTime(menu.getCreateTime());
        model.setUpdater(menu.getUpdater());
        model.setUpdateTime(menu.getUpdateTime());
        return model;
    }

    private CaMenu convert(MenuRequest request) {
        CaMenu entity = new CaMenu();
        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setType(request.getType());
        entity.setIcon(request.getIcon());
        entity.setPath(request.getPath());
        entity.setPid(request.getPid());
        entity.setTarget(request.getTarget());
        entity.setVisible(request.getVisible());
        entity.setPermission(request.getPermission());
        return entity;
    }

}
