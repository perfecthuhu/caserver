package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.dao.CaRoleMapper;
import com.card.alumni.entity.CaRole;
import com.card.alumni.entity.CaRoleExample;
import com.card.alumni.model.RoleModel;
import com.card.alumni.request.RoleQueryRequest;
import com.card.alumni.request.RoleRequest;
import com.card.alumni.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liumingyu
 * @date 2019-12-09 9:37 PM
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private CaRoleMapper caRoleMapper;

    @Override
    public Integer save(RoleRequest request) {
        return null;
    }

    @Override
    public void update(RoleRequest request) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public CaRole findById(Integer id) {
        return null;
    }

    @Override
    public PageData<RoleModel> pageByKeyword(RoleQueryRequest request) {
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaRoleExample example = new CaRoleExample();
        CaRoleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike("%" + request.getKeyword() + "%");
        }
        example.setOrderByClause(orderField + " " + orderType);
        List<CaRole> roleList = caRoleMapper.selectByExample(example);
        PageInfo<CaRole> pageInfo = new PageInfo<>(roleList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(roleList));
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

}
