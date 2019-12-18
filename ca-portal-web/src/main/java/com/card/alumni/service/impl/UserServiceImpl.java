package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.User;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.dao.CaUserRoleRelationMapper;
import com.card.alumni.dao.CaUserTagMapper;
import com.card.alumni.entity.*;
import com.card.alumni.exception.CaException;
import com.card.alumni.exception.ResultCodeEnum;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.utils.VerificationCodeUtils;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.enums.StatusEnum;
import com.card.alumni.vo.enums.UserTagEnum;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    private CaUserTagMapper caUserTagMapper;
    
    @Resource
    private RedisUtils redisUtils;

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
    @Transactional(rollbackFor = Exception.class)
    public void submitUserInfo(UserVO userVO) throws Exception {
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        caUser.setUpdateTime(new Date(System.currentTimeMillis()));
        caUser.setCreateTime(new Date(System.currentTimeMillis()));
        int count = caUserMapper.updateByPrimaryKey(caUser);
        if (count != 1) {
            throw new CaException("填写信息失败");
        }
        caUserTagMapper.insert(convert2CaUserTag(userVO));
        if (count != 1) {
            throw new CaException("填写信息失败");
        }
    }

    private CaUserTag convert2CaUserTag(UserVO userVO) {
        CaUserTag caUserTag = new CaUserTag();
        caUserTag.setStudentId(userVO.getId());
        List<Integer> userTagId = userVO.getUserTagId();
        if (CollectionUtils.isNotEmpty(userTagId)) {
            userTagId.stream().forEach(s -> {
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
        caUserTag.setCreateTime(new Date(System.currentTimeMillis()));
        caUserTag.setUpdateTime(new Date(System.currentTimeMillis()));
        return caUserTag;
    }

    @Override
    public PageData<UserVO> queryUserVO(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(), userQuery.getPageSize());
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
        if (!validateVerificatioCode(userVO, verificatioCode)) {
            throw new CaException("验证码错误");
        }
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        int count = caUserMapper.insert(caUser);
        if (count != 1) {
            throw new CaException("注册失败");
        }
    }

    @Override
    public void sendValidateCode(String phone) throws Exception {
        String code = VerificationCodeUtils.getVerificationCode(phone);
        redisUtils.set("CA_VALIDATE_CODE_KEY_" + phone, code);
    }

    private boolean validateVerificatioCode(UserVO userVO, String verificatioCode) {
        boolean flag = false;
        Object validateCodeCache = redisUtils.get("CA_VALIDATE_CODE_KEY_" + userVO.getPhone());
        if (Objects.nonNull(validateCodeCache)) {
            String code = (String) validateCodeCache;
            if (code.equals(verificatioCode)) {
                flag = true;
            }
        }
        return flag;
    }

}
