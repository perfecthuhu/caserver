package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.dao.CaUserRoleRelationMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserExample;
import com.card.alumni.entity.CaUserRoleRelation;
import com.card.alumni.entity.CaUserRoleRelationExample;
import com.card.alumni.entity.CaUserTag;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.enums.UserTagEnum;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.UserModel;
import com.card.alumni.request.UserQueryRequest;
import com.card.alumni.request.UserRequest;
import com.card.alumni.service.UserFriendService;
import com.card.alumni.service.UserService;
import com.card.alumni.service.UserTagService;
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

import javax.annotation.Resource;
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

    @Resource
    private CaUserMapper caUserMapper;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private UserFriendService userFriendService;

    @Resource
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

        userTagService.save(convert2CaUserTag(user.getId(), request));

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

        userTagService.update(convert2CaUserTag(user.getId(), request));
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
    public void review(Integer id, Integer yn) throws CaConfigException {
        CaUser user = findById(id);
        if (Objects.isNull(user)) {
            throw new CaConfigException("用户不存在");
        }

        yn = Objects.isNull(yn) ? 1 : yn;
        user.setYn(yn);
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

        UserModel model = convert2Model(user);

        if (Objects.nonNull(model)) {
            model.setUserTagIdList(populateUserTag(id));
        }
        return model;
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
    public List<UserModel> listModelByIdList(List<Integer> idList) throws CaConfigException {
        List<CaUser> userList = listByIdList(idList);
        if (CollectionUtils.isEmpty(userList)) {
            return Lists.newArrayList();
        }

        return convert2ModelList(userList);
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

        CaUserExample example = buildCaUserExample(request);
        example.setOrderByClause(orderField + CaConstants.BLANK + orderType);
        List<CaUser> userList = caUserMapper.selectByExample(example);
        PageInfo<CaUser> pageInfo = new PageInfo<>(userList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(userList));
    }

    private CaUserExample buildCaUserExample(UserQueryRequest request) {
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
        if (Objects.nonNull(request.getSex())) {
            criteria.andSexEqualTo(request.getSex());
        }
        if (Objects.nonNull(request.getClassId())) {
            criteria.andIdEqualTo(request.getClassId());
        }
        if (Objects.nonNull(request.getCollegeId())) {
            criteria.andCollegeIdEqualTo(request.getCollegeId());
        }
        if (Objects.nonNull(request.getFacultyId())) {
            criteria.andFacultyIdEqualTo(request.getFacultyId());
        }
        if (Objects.nonNull(request.getAlumniId())) {
            criteria.andAlumniIdEqualTo(request.getAlumniId());
        }
        if (Objects.nonNull(request.getSchoolId())) {
            criteria.andSchoolIdEqualTo(request.getSchoolId());
        }

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(request.getUserIdList())) {
            criteria.andIdIn(request.getUserIdList());
        }

        criteria.andNameIsNotNull();
        return example;
    }

    @Override
    public void batchSaveUserRoleRel(Integer userId, List<Integer> roleIdList) throws CaConfigException {
        if (CollectionUtils.isEmpty(roleIdList)) {
            deleteUserRoleRelByUserId(userId);
            return;
        }

        List<Integer> hasExistRoleIdList = listRoleIdsByUserId(userId);

        List<Integer> roleIds = roleIdList.stream().filter(Objects::nonNull).filter(roleId -> !hasExistRoleIdList.contains(roleId)).collect(Collectors.toList());

        Date now = new Date();
        Integer operatorId = RequestUtil.getUserId();
        List<CaUserRoleRelation> relationList = Lists.newLinkedList();
        for (Integer roleId : roleIds) {
            CaUserRoleRelation relation = new CaUserRoleRelation();
            relation.setRoleId(roleId);
            relation.setUserId(userId);
            relation.setCreateTime(now);
            relation.setUpdateTime(now);
            relation.setCreator(operatorId);
            relation.setUpdater(operatorId);
            relation.setIsDelete(Boolean.FALSE);
            relationList.add(relation);
        }

        if (CollectionUtils.isEmpty(relationList)) {
            return;
        }

        caUserRoleRelationMapper.batchInsert(relationList);
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

    @Override
    public List<CaUserRoleRelation> listUserRoleRelByRoleId(Integer roleId) throws CaConfigException {
        if (Objects.isNull(roleId)) {
            throw new CaConfigException("角色ID不能为空");
        }
        CaUserRoleRelationExample example = new CaUserRoleRelationExample();
        CaUserRoleRelationExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andRoleIdEqualTo(roleId);

        return caUserRoleRelationMapper.selectByExample(example);
    }

    private void checkParam(UserRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("用户信息不能为空");
        }
        if (StringUtils.isBlank(request.getPhone())) {
            throw new CaConfigException("手机号不能为空");
        }
    }

    @Override
    public List<UserModel> convert2ModelList(List<CaUser> userList) {
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

    private CaUserTag convert2CaUserTag(Integer userId, UserRequest request) {
        CaUserTag caUserTag = new CaUserTag();
        caUserTag.setUserId(userId);
        List<Integer> userTagId = request.getUserTagIdList();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userTagId)) {
            userTagId.forEach(s -> {
                UserTagEnum userTagEnum = UserTagEnum.getUserTagEnum(s);
                switch (userTagEnum) {
                    case GIRL_FRIEND:
                        caUserTag.setGirlFriend(StatusEnum.YES.getCode());
                        break;
                    case RESOURCE:
                        caUserTag.setResource(StatusEnum.YES.getCode());
                        break;
                    case FOOD:
                        caUserTag.setFood(StatusEnum.YES.getCode());
                        break;
                    case JOB:
                        caUserTag.setJob(StatusEnum.YES.getCode());
                        break;
                    default:
                }
            });
        }
        return caUserTag;
    }

    private List<Integer> populateUserTag(Integer userId) throws CaConfigException {
        CaUserTag userTag = userTagService.findByUserId(userId);

        List<Integer> userTagIdList = Lists.newArrayList();

        if (StatusEnum.YES.getCode().equals(userTag.getGirlFriend())) {
            userTagIdList.add(UserTagEnum.GIRL_FRIEND.getCode());
        }
        if (StatusEnum.YES.getCode().equals(userTag.getResource())) {
            userTagIdList.add(UserTagEnum.RESOURCE.getCode());
        }
        if (StatusEnum.YES.getCode().equals(userTag.getFood())) {
            userTagIdList.add(UserTagEnum.FOOD.getCode());
        }
        if (StatusEnum.YES.getCode().equals(userTag.getJob())) {
            userTagIdList.add(UserTagEnum.JOB.getCode());
        }

        return userTagIdList;
    }
}
