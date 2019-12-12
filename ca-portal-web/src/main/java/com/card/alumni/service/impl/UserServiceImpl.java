package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.User;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.dao.CaUserRoleRelationMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserExample;
import com.card.alumni.entity.CaUserRoleRelation;
import com.card.alumni.entity.CaUserRoleRelationExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.exception.ResultCodeEnum;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.VerificationCodeUtils;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 3:31 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private CaUserMapper caUserMapper;

    @Resource
    private CaUserRoleRelationMapper caUserRoleRelationMapper;

    @Override
    public void login(UserVO userVO, String verificatioCode) throws Exception  {
        if (validateVerificatioCode(userVO, verificatioCode)) {
            throw new CaException("验证码错误");
        }
    }

    @Override
    public User queryUserById(Integer userId){
        try {
            if (Objects.isNull(userId)) {
                throw new CaException("参数为空");
            }
            CaUser caUser = caUserMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(caUser)) {
                User user = convertUser(caUser);
                user.setRoleIds(queryUserRole(userId));
                return user;
            }
        } catch (Exception e) {
            LOGGER.error("查询用户异常, 入参:{}", userId, e);
        }
        return null;
    }

    private User convertUser(CaUser caUser) {
        User user = new User();
        BeanUtils.copyProperties(caUser, user);
        return user;
    }

    @Override
    public void submitUserInfo(UserVO userVO) throws Exception {
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        int count = caUserMapper.updateByPrimaryKey(caUser);
        if (count != 1) {
            throw new CaException("填写信息失败");
        }
    }

    @Override
    public PageData<UserVO> queryUserVO(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getCurrentPage(), userQuery.getPageSize());
        List<CaUser> caUsers = caUserMapper.selectByExample(buildCaUserExample(userQuery));
        PageInfo<CaUser> pageInfo = new PageInfo<>(caUsers);
        return new PageData<>(pageInfo.getTotal(), convertUserVOList(caUsers));
    }

    private List<UserVO> convertUserVOList(List<CaUser> caUsers) {
        if (CollectionUtils.isEmpty(caUsers)) {
            return null;
        }
        return caUsers.stream().filter(Objects::nonNull).map(s -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(s, userVO);
            return userVO;
        }).collect(Collectors.toList());
    }

    private  CaUserExample buildCaUserExample(UserQuery userQuery) {
        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();
        if (Objects.isNull(userQuery)) {
            return example;
        }
        if (Objects.nonNull(userQuery.getId())) {
            criteria.andIdEqualTo(userQuery.getId());
        }
        if (Objects.nonNull(userQuery.getPhone())) {
            criteria.andPhoneEqualTo(userQuery.getPhone());
        }
        if (Objects.nonNull(userQuery.getName())) {
            criteria.andNameEqualTo(userQuery.getName());
        }
        if (Objects.nonNull(userQuery.getSex())) {
            criteria.andSexEqualTo(userQuery.getSex());
        }
        if (Objects.nonNull(userQuery.getClassId())) {
            criteria.andIdEqualTo(userQuery.getClassId());
        }
        if (Objects.nonNull(userQuery.getCollegeId())) {
            criteria.andCollegeIdEqualTo(userQuery.getCollegeId());
        }
        if (Objects.nonNull(userQuery.getFacultyId())) {
            criteria.andFacultyIdEqualTo(userQuery.getFacultyId());
        }
        if (Objects.nonNull(userQuery.getAlumniId())) {
            criteria.andAlumniIdEqualTo(userQuery.getAlumniId());
        }
        if (Objects.nonNull(userQuery.getSchoolId())) {
            criteria.andSchoolIdEqualTo(userQuery.getSchoolId());
        }
        if (CollectionUtils.isNotEmpty(userQuery.getIdList())) {
            criteria.andIdCardIn(userQuery.getIdList().stream().map(s -> s.toString()).collect(Collectors.toList()));
        }
        return example;
    }

    @Override
    public void register(UserVO userVO, String verificatioCode) throws Exception {
        if (Objects.isNull(userVO)) {
            throw new CaException("用户信息为空");
        }
        if (Objects.isNull(userVO.getPhone())) {
            throw new CaException("手机号码为空");
        }
        if (validateVerificatioCode(userVO, verificatioCode)) {
            throw new CaException("验证码错误");
        }
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        int count = caUserMapper.insert(caUser);
        if (count != 1) {
            throw new CaException("注册失败");
        }
    }

    private boolean validateVerificatioCode(UserVO userVO, String verificatioCode) {
        boolean flag = false;
        String code = VerificationCodeUtils.getVerificationCode(userVO.getPhone());
        if (code.equals(verificatioCode)) {
            flag = true;
        }
        return flag;
    }

    private List<Integer> queryUserRole(Integer userId) throws CaException {
        CaUserRoleRelationExample example = new CaUserRoleRelationExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CaUserRoleRelation> caUserRoleRelations = caUserRoleRelationMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(caUserRoleRelations)) {
            throw new CaException("用户角色为空");
        }
        return caUserRoleRelations.stream().map(CaUserRoleRelation::getRoleId).collect(Collectors.toList());
    }

}
