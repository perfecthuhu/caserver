package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaRoleMapper;
import com.card.alumni.dao.CaRoleMenuRelationMapper;
import com.card.alumni.dao.CaUserRoleRelationMapper;
import com.card.alumni.entity.CaRole;
import com.card.alumni.entity.CaRoleExample;
import com.card.alumni.entity.CaRoleMenuRelation;
import com.card.alumni.entity.CaRoleMenuRelationExample;
import com.card.alumni.entity.CaUserRoleRelation;
import com.card.alumni.entity.CaUserRoleRelationExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.MenuModel;
import com.card.alumni.model.RoleModel;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.request.RoleRequest;
import com.card.alumni.service.MenuService;
import com.card.alumni.service.RoleService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liumingyu
 * @date 2019-12-09 9:37 PM
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final int ROLE_NAME_MAX_LEN = 50;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CaRoleMapper caRoleMapper;

    @Autowired
    private CaRoleMenuRelationMapper caRoleMenuRelationMapper;

    @Autowired
    private CaUserRoleRelationMapper caUserRoleRelationMapper;

    @Override
    public Integer save(RoleRequest request) throws CaConfigException {

        checkParam(request);

        if (checkExist(request)) {
            throw new CaConfigException("角色名称已存在");
        }

        CaRole role = convert(request);

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        role.setCreator(userId);
        role.setUpdater(userId);
        role.setCreateTime(now);
        role.setUpdateTime(now);
        role.setIsDelete(Boolean.FALSE);

        caRoleMapper.insert(role);

        return role.getId();
    }

    @Override
    public void update(RoleRequest request) throws CaConfigException {

        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("角色ID不能为空");
        }

        if (checkExist(request)) {
            throw new CaConfigException("角色名称已存在");
        }

        CaRole role = convert(request);

        role.setUpdateTime(new Date());
        role.setUpdater(RequestUtil.getUserId());

        caRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaRole role = findById(id);

        if (Objects.isNull(role) || role.getIsDelete()) {
            throw new CaConfigException("当前角色不存在或已删除");
        }

        role.setIsDelete(Boolean.TRUE);
        role.setUpdateTime(new Date());
        role.setUpdater(RequestUtil.getUserId());
    }

    @Override
    public CaRole findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("角色ID不能为空");
        }

        return caRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public RoleModel findModelById(Integer id) throws CaConfigException {

        CaRole role = findById(id);

        return convert2Model(role);
    }

    @Override
    public List<CaRole> listByIdList(List<Integer> idList) throws CaConfigException {
        if (CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayList();
        }

        CaRoleExample example = new CaRoleExample();
        CaRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idList);

        return caRoleMapper.selectByExample(example);
    }

    @Override
    public PageData<RoleModel> pageByRequest(RoleQueryRequest request) throws CaConfigException {
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaRoleExample example = new CaRoleExample();
        CaRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaRole> roleList = caRoleMapper.selectByExample(example);
        PageInfo<CaRole> pageInfo = new PageInfo<>(roleList);

        List<RoleModel> roleModelList = convert2ModelList(roleList);
        populateMenuList(roleModelList);

        return new PageData<>(pageInfo.getTotal(), roleModelList);
    }

    @Override
    public void deleteRoleMenuRelByRoleId(Integer roleId) throws CaConfigException {
        List<CaRoleMenuRelation> relationList = listRoleMenuRelByRoleId(roleId);
        if (CollectionUtils.isEmpty(relationList)) {
            return;
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        relationList.forEach(relation -> {
            relation.setUpdateTime(now);
            relation.setUpdater(userId);
            relation.setIsDelete(Boolean.TRUE);
        });

        caRoleMenuRelationMapper.batchUpdate(relationList);
    }

    private List<CaRoleMenuRelation> listRoleMenuRelByRoleId(Integer roleId) throws CaConfigException {
        if (Objects.isNull(roleId)) {
            throw new CaConfigException("角色ID不能为空");
        }

        CaRoleMenuRelationExample example = new CaRoleMenuRelationExample();
        CaRoleMenuRelationExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caRoleMenuRelationMapper.selectByExample(example);
    }

    @Override
    public List<Integer> listMenuIdsByRoleId(Integer roleId) throws CaConfigException {

        List<CaRoleMenuRelation> relationList = listRoleMenuRelByRoleId(roleId);

        return relationList.stream().filter(Objects::nonNull)
                .map(CaRoleMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Override
    public List<Integer> listMenuIdsByRoleIdList(List<Integer> roleIdList) throws CaConfigException {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }

        CaRoleMenuRelationExample example = new CaRoleMenuRelationExample();
        CaRoleMenuRelationExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdIn(roleIdList);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        List<CaRoleMenuRelation> relationList = caRoleMenuRelationMapper.selectByExample(example);

        return relationList.stream().filter(Objects::nonNull)
                .map(CaRoleMenuRelation::getMenuId).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<CaRoleMenuRelation>> mapByRoleIdList(List<Integer> roleIdList) throws CaConfigException {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Maps.newHashMap();
        }

        CaRoleMenuRelationExample example = new CaRoleMenuRelationExample();
        CaRoleMenuRelationExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdIn(roleIdList);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        List<CaRoleMenuRelation> relationList = caRoleMenuRelationMapper.selectByExample(example);

        return relationList.stream().filter(Objects::nonNull)
                .collect(Collectors.groupingBy(CaRoleMenuRelation::getRoleId));
    }

    @Override
    public void deleteUserRoleRelByRoleId(Integer roleId) throws CaConfigException {
        List<CaUserRoleRelation> relationList = listUserRoleRelByRoleId(roleId);
        if (CollectionUtils.isEmpty(relationList)) {
            return;
        }

        Date now = new Date();
        Integer userId = RequestUtil.getUserId();
        relationList.forEach(relation -> {
            relation.setUpdateTime(now);
            relation.setUpdater(userId);
            relation.setIsDelete(Boolean.TRUE);
        });

        caUserRoleRelationMapper.batchUpdate(relationList);
    }

    private List<CaUserRoleRelation> listUserRoleRelByRoleId(Integer roleId) throws CaConfigException {
        if (Objects.isNull(roleId)) {
            throw new CaConfigException("角色ID不能为空");
        }

        CaUserRoleRelationExample example = new CaUserRoleRelationExample();
        CaUserRoleRelationExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);

        return caUserRoleRelationMapper.selectByExample(example);
    }

    @Override
    public List<Integer> listUserIdsByRoleId(Integer roleId) throws CaConfigException {
        if (Objects.isNull(roleId)) {
            throw new CaConfigException("角色ID不能为空");
        }

        List<CaUserRoleRelation> relationList = listUserRoleRelByRoleId(roleId);

        return relationList.stream().filter(Objects::nonNull)
                .map(CaUserRoleRelation::getUserId).collect(Collectors.toList());
    }

    private void populateMenuList(List<RoleModel> roleModelList) {
        if (CollectionUtils.isEmpty(roleModelList)) {
            return;
        }

        List<Integer> roleIdList = roleModelList.stream().filter(Objects::nonNull)
                .map(RoleModel::getId).collect(Collectors.toList());

        Map<Integer, List<CaRoleMenuRelation>> relationMap = mapByRoleIdList(roleIdList);

        for (RoleModel role : roleModelList) {
            List<CaRoleMenuRelation> relationList = relationMap.get(role.getId());
            List<Integer> menuIdList = relationList.stream().filter(Objects::nonNull).map(CaRoleMenuRelation::getMenuId).collect(Collectors.toList());
            role.setMenuIdList(menuIdList);

            List<MenuModel> menuModelList = menuService.listRankModelByIdList(menuIdList);
            role.setMenuList(menuModelList);
        }
    }

    /**
     * 校验角色名称是否存在
     * 刨除了当前ID的名称
     *
     * @param request 角色请求
     * @return true-存在 false-不存在
     */
    private boolean checkExist(RoleRequest request) {
        CaRoleExample example = new CaRoleExample();
        CaRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andNameEqualTo(request.getName());
        if (Objects.nonNull(request.getId())) {
            criteria.andIdNotEqualTo(request.getId());
        }
        List<CaRole> roleList = caRoleMapper.selectByExample(example);

        return CollectionUtils.isNotEmpty(roleList);
    }

    private void checkParam(RoleRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("角色请求不能为空");
        }
        if (Objects.isNull(request.getName())) {
            throw new CaConfigException("角色名称不能为空");
        }
        if (request.getName().length() > ROLE_NAME_MAX_LEN) {
            throw new CaConfigException("角色名称长度不能超过" + ROLE_NAME_MAX_LEN);
        }
    }

    private List<RoleModel> convert2ModelList(List<CaRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }

        return roleList.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private RoleModel convert2Model(CaRole role) {
        if (Objects.isNull(role)) {
            return null;
        }
        RoleModel model = new RoleModel();
        model.setId(role.getId());
        model.setName(role.getName());
        model.setRemark(role.getRemark());
        model.setDataScope(role.getDataScope());
        model.setPermission(role.getPermission());
        model.setCreator(role.getCreator());
        model.setCreateTime(role.getCreateTime());
        model.setUpdater(role.getUpdater());
        model.setUpdateTime(role.getUpdateTime());
        return model;
    }

    private CaRole convert(RoleRequest request) {
        CaRole role = new CaRole();
        role.setId(request.getId());
        role.setName(request.getName());
        role.setRemark(request.getRemark());
        role.setDataScope(request.getDataScope());
        role.setPermission(request.getPermission());
        return role;
    }

}
