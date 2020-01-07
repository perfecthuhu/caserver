package com.card.alumni.service.impl;

import com.alibaba.fastjson.JSON;
import com.card.alumni.common.PageData;
import com.card.alumni.context.User;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.dao.CaUserTagMapper;
import com.card.alumni.entity.*;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.EncryptUtils;
import com.card.alumni.utils.PinyinUtils;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.utils.VerificationCodeUtils;
import com.card.alumni.vo.UserVO;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.enums.UserTagEnum;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    public CaUser login(UserVO userVO, String verificatioCode) throws Exception  {
        if (!validateVerificatioCode(userVO, verificatioCode)) {
            throw new CaException("验证码错误");
        }
        CaUser caUser = queryUserByPhone(userVO.getPhone());

        if (Objects.isNull(caUser)) {
            caUser = register(userVO);
        }
        LOGGER.info("用户登陆param:userVO:{},verificatioCode:{}, result:{}", JSON.toJSONString(userVO), verificatioCode, JSON.toJSON(caUser));
        return caUser;
    }

    @Override
    public User queryUserById(Integer userId){
        User user = null;
        try {
            if (Objects.isNull(userId)) {
                throw new CaException("参数为空");
            }
            CaUser caUser = caUserMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(caUser)) {
                user = convertUser(caUser);
            }
        } catch (Exception e) {
            LOGGER.error("查询用户异常, 入参:{}", userId, e);
        }
        LOGGER.info("查询用户 param:{}, result:{}", userId, JSON.toJSON(user));
        return user;
    }

    private User convertUser(CaUser caUser) {
        User user = new User();
        BeanUtils.copyProperties(caUser, user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitUserInfo(UserVO userVO) throws Exception {
        if (Objects.isNull(userVO) || Objects.isNull(userVO.getId())) {
            userVO.setId(RequestUtil.getUserId());
        }
        CaUser caUser = convert2CaUser(userVO);
        int count = caUserMapper.updateByPrimaryKeySelective(caUser);
        if (count != 1) {
            throw new CaException("填写信息失败");
        }
        LOGGER.info("更新用户信息入参:{}", JSON.toJSONString(userVO));
        caUserTagMapper.insert(convert2CaUserTag(userVO));
    }

    private CaUser convert2CaUser(UserVO userVO) {
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        caUser.setNamePy(PinyinUtils.getFirstLetter(userVO.getName()));
        caUser.setYn(1);
        caUser.setUpdateTime(new Date(System.currentTimeMillis()));
        caUser.setCreateTime(new Date(System.currentTimeMillis()));
        return caUser;
    }

    private CaUserTag convert2CaUserTag(UserVO userVO) {
        CaUserTag caUserTag = new CaUserTag();
        caUserTag.setUserId(userVO.getId());
        List<Integer> userTagId = userVO.getUserTagId();
        if (CollectionUtils.isNotEmpty(userTagId)) {
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
        caUserTag.setCreateTime(new Date(System.currentTimeMillis()));
        caUserTag.setUpdateTime(new Date(System.currentTimeMillis()));
        return caUserTag;
    }

    @Override
    public PageData<UserVO> queryUserVO(UserQuery userQuery) {
        LOGGER.info("查询用户列表 param:{}", JSON.toJSONString(userQuery));
        PageHelper.startPage(userQuery.getPage(), userQuery.getPageSize());
        List<CaUser> caUsers = caUserMapper.selectByExample(buildCaUserExample(userQuery));
        PageInfo<CaUser> pageInfo = new PageInfo<>(caUsers);
        PageData<UserVO> userVOPageData = new PageData<>(pageInfo.getTotal(), convertUserVOList(caUsers));
        LOGGER.info("查询用户列表 param:{}", JSON.toJSONString(userVOPageData));
        return userVOPageData;
    }

    private List<UserVO> convertUserVOList(List<CaUser> caUsers) {
        if (CollectionUtils.isEmpty(caUsers)) {
            return Lists.newArrayList();
        }
        return caUsers.stream().filter(Objects::nonNull).map(s -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(s, userVO);
            userVO.setPwd(null);
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

    private CaUser register(UserVO userVO) throws Exception {
        if (Objects.isNull(userVO)) {
            throw new CaException("用户信息为空");
        }
        if (Objects.isNull(userVO.getPhone())) {
            throw new CaException("手机号码为空");
        }
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        caUser.setYn(0);
        caUser.setPwd(StringUtils.isBlank(userVO.getPwd()) ?
                EncryptUtils.encryptPassword(userVO.getPhone()) : EncryptUtils.encryptPassword(userVO.getPwd()));
        caUser.setPwdLastResetTime(new Date());
        int count = caUserMapper.insert(caUser);
        if (count != 1) {
            throw new CaException("登陆失败");
        }
        return caUser;
    }

    @Override
    public CaUser queryUserByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        CaUserExample example = new CaUserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<CaUser> caUser = caUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(caUser)) {
            return null;
        }
        CaUser user = caUser.get(0);
        user.setPwd(null);
        return user;
    }

    @Override
    public void sendValidateCode(String phone) throws Exception {
        String code = VerificationCodeUtils.getVerificationCode(phone);
        redisUtils.set("CA_VALIDATE_CODE_KEY_" + phone, Integer.valueOf(code), 60 * 30);
    }

    @Override
    public SimpleUserModel findUserById(Integer userId) throws CaException {
        if (Objects.isNull(userId)) {
            throw new CaException("用户ID不能为空");
        }

        CaUser user = caUserMapper.selectByPrimaryKey(userId);
        SimpleUserModel userModel = new SimpleUserModel();
        BeanUtils.copyProperties(user, userModel);

        return userModel;
    }

    private boolean validateVerificatioCode(UserVO userVO, String verificatioCode) {
        boolean flag = false;
        Object validateCodeCache = redisUtils.get("CA_VALIDATE_CODE_KEY_" + userVO.getPhone());
        if (Objects.nonNull(validateCodeCache)) {
            String code = String.valueOf(validateCodeCache);
            if (code.equals(verificatioCode)) {
                flag = true;
            }
        }
        return flag;
    }

}
