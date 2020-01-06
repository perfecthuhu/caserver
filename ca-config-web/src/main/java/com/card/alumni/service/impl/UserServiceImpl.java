package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.dao.CaUserRoleRelationMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserExample;
import com.card.alumni.entity.CaUserRoleRelation;
import com.card.alumni.entity.CaUserRoleRelationExample;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.UserModel;
import com.card.alumni.request.UserQueryRequest;
import com.card.alumni.request.UserRequest;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.EncryptUtils;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户服务
 *
 * @author liumingyu
 * @date 2019-12-15 1:43 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CaUserMapper caUserMapper;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private CaUserRoleRelationMapper caUserRoleRelationMapper;

    @Override
    public Integer save(UserRequest request) throws CaConfigException {
        checkParam(request);

        CaUser history = findByPhone(request.getPhone());
        if (Objects.nonNull(history)) {
            throw new CaConfigException("手机号已经存在");
        }

        CaUser user = convert(request);

        user.setPwd(StringUtils.isBlank(request.getPwd()) ?
                EncryptUtils.encryptPassword(request.getPhone()) : EncryptUtils.encryptPassword(request.getPwd()));

        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setPwdLastResetTime(now);
        user.setYn(StatusEnum.YES.getCode());

        caUserMapper.insert(user);

        return user.getId();
    }

    @Override
    public void update(UserRequest request) throws CaConfigException {
        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("用户ID不能为空");
        }
        CaUser user = convert(request);
        user.setUpdateTime(new Date());

        caUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updatePwd(Integer id, String password) throws CaConfigException {
        CaUser user = findById(id);
        if (Objects.isNull(user)) {
            throw new CaConfigException("用户不存在");
        }

        user.setPwd(EncryptUtils.encryptPassword(password));
        user.setPwdLastResetTime(new Date());
        user.setUpdateTime(new Date());

        caUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaUser user = findById(id);
        if (Objects.isNull(user)) {
            throw new CaConfigException("用户不存在");
        }

        caUserMapper.deleteByPrimaryKey(id);

        userFriendService.deleteByFriendId(id);
    }

    @Override
    public CaUser findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("用户ID不能为空");
        }
        return caUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public CaUser findByPhone(String phone) throws CaConfigException {
        if (StringUtils.isBlank(phone)) {
            throw new CaConfigException("手机号不能为空");
        }
        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<CaUser> userList = caUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public UserModel findModelById(Integer id) throws CaConfigException {

        CaUser user = findById(id);

        return convert2Model(user);
    }

    @Override
    public List<CaUser> listByIdList(List<Integer> idList) throws CaConfigException {
        if (CollectionUtils.isEmpty(idList)) {
            return Lists.newArrayList();
        }

        List<Integer> ids = idList.stream().distinct().collect(Collectors.toList());

        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        return caUserMapper.selectByExample(example);
    }

    @Override
    public Map<Integer, CaUser> mapByIdList(List<Integer> idList) throws CaConfigException {

        List<CaUser> userList = listByIdList(idList);

        return userList.stream().filter(Objects::nonNull)
                .collect(Collectors.toMap(CaUser::getId, Function.identity(), (k1, k2) -> k2));
    }

    @Override
    public PageData<UserModel> pageByRequest(UserQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("查询请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) || request.getPage() <= 0 ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) || request.getSize() <= 0 ? 20 : request.getSize();
        String orderField = StringUtils.isBlank(request.getOrderField()) ? "create_time" : request.getOrderField();
        String orderType = StringUtils.isBlank(request.getOrderType()) ? "desc" : request.getOrderType();

        PageHelper.startPage(page, size);
        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(request.getId())) {
            criteria.andIdEqualTo(request.getId());
        }
        if (StringUtils.isNotBlank(request.getPhone())) {
            criteria.andPhoneEqualTo(request.getPhone());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        if (Objects.nonNull(request.getStatus())) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        if (Objects.nonNull(request.getYn())) {
            criteria.andYnEqualTo(request.getYn());
        }
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUser> userList = caUserMapper.selectByExample(example);
        PageInfo<CaUser> pageInfo = new PageInfo<>(userList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(userList));
    }


    @Override
    public void deleteUserRoleRelByUserId(Integer userId) throws CaConfigException {
        List<CaUserRoleRelation> relationList = listUserRoleRelByUserId(userId);
        if (CollectionUtils.isEmpty(relationList)) {
            return;
        }

        Date now = new Date();
        Integer operatorId = RequestUtil.getUserId();
        relationList.forEach(relation -> {
            relation.setUpdateTime(now);
            relation.setUpdater(operatorId);
            relation.setIsDelete(Boolean.TRUE);
        });

        caUserRoleRelationMapper.batchUpdate(relationList);
    }

    @Override
    public List<CaUserRoleRelation> listUserRoleRelByUserId(Integer userId) throws CaConfigException {
        if (Objects.isNull(userId)) {
            throw new CaConfigException("用户ID不能为空");
        }
        CaUserRoleRelationExample example = new CaUserRoleRelationExample();
        CaUserRoleRelationExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andUserIdEqualTo(userId);

        return caUserRoleRelationMapper.selectByExample(example);
    }

    @Override
    public List<Integer> listRoleIdsByUserId(Integer userId) throws CaConfigException {

        List<CaUserRoleRelation> relations = listUserRoleRelByUserId(userId);

        return relations.stream().filter(Objects::nonNull)
                .map(CaUserRoleRelation::getRoleId).collect(Collectors.toList());
    }


    private void checkParam(UserRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("用户信息不能为空");
        }
        if (StringUtils.isBlank(request.getPhone())) {
            throw new CaConfigException("手机号不能为空");
        }
    }

    private List<UserModel> convert2ModelList(List<CaUser> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return Lists.newArrayList();
        }

        return userList.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private UserModel convert2Model(CaUser user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserModel model = new UserModel();
        BeanUtils.copyProperties(user, model);
        return model;
    }

    private CaUser convert(UserRequest request) {
        CaUser user = new CaUser();
        BeanUtils.copyProperties(request, user);
        return user;
    }
}
