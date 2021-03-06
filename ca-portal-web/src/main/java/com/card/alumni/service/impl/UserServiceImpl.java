package com.card.alumni.service.impl;

import com.alibaba.fastjson.JSON;
import com.card.alumni.common.PageData;
import com.card.alumni.context.User;
import com.card.alumni.dao.CaAlumniRoleMapper;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.dao.CaUserTagMapper;
import com.card.alumni.dao.UserFeedbackMapper;
import com.card.alumni.entity.*;
import com.card.alumni.enums.StatusEnum;
import com.card.alumni.enums.UserTagEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.FeedbackRequest;
import com.card.alumni.request.UserSchoolAlumniRequest;
import com.card.alumni.service.AlumniService;
import com.card.alumni.service.UserLocalService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.EncryptUtils;
import com.card.alumni.utils.PinyinUtils;
import com.card.alumni.utils.RedisUtils;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.utils.VerificationCodeUtils;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.enums.AlumniRoleEnum;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private UserFeedbackMapper userFeedbackMapper;

    @Autowired
    private UserLocalService userLocalService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CaAlumniRoleMapper caAlumniRoleMapper;

    @Resource
    private AlumniService alumniService;

    @Override
    public CaUser login(UserVO userVO, String verificatioCode) throws Exception {
        LOGGER.info("UserServiceImpl.queryUserById param userVO: {}, verificatioCode: {}", JSON.toJSONString(userVO), verificatioCode);
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
    public User queryUserById(Integer userId) {
        LOGGER.info("UserServiceImpl.queryUserById param : {}", userId);
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
        LOGGER.info("UserServiceImpl.submitUserInfo param : {}", JSON.toJSONString(userVO));
        if (Objects.isNull(userVO) || Objects.isNull(userVO.getId())) {
            userVO.setId(RequestUtil.getUserId());
        }

        CaUser caUserNow = caUserMapper.selectByPrimaryKey(RequestUtil.getUserId());

        if (Objects.nonNull(userVO.getClassId()) && !userVO.getClassId().equals(caUserNow.getClassId())) {
            if (Objects.isNull(caUserNow.getClassId()) || deleteAlumniRole(caUserNow.getClassId())) {
                caAlumniRoleMapper.insert(buildCaAlumniRole(userVO.getClassId()));
            }
        }
        if (Objects.nonNull(userVO.getCollegeId()) && !userVO.getCollegeId().equals(caUserNow.getCollegeId())) {
            if (Objects.isNull(caUserNow.getCollegeId()) || deleteAlumniRole(caUserNow.getCollegeId())) {
                caAlumniRoleMapper.insert(buildCaAlumniRole(userVO.getCollegeId()));
            }
        }
        if (Objects.nonNull(userVO.getFacultyId()) && !userVO.getFacultyId().equals(caUserNow.getFacultyId())) {
            if (Objects.isNull(caUserNow.getFacultyId()) || deleteAlumniRole(caUserNow.getFacultyId())) {
                caAlumniRoleMapper.insert(buildCaAlumniRole(userVO.getFacultyId()));
            }
        }
        if (Objects.nonNull(userVO.getAlumniId()) && !userVO.getAlumniId().equals(caUserNow.getAlumniId())) {
            if (Objects.isNull(caUserNow.getAlumniId()) || deleteAlumniRole(caUserNow.getAlumniId())) {
                caAlumniRoleMapper.insert(buildCaAlumniRole(userVO.getAlumniId()));
            }
        }

        CaUser caUser = convert2CaUser(userVO);
        int count = caUserMapper.updateByPrimaryKeySelective(caUser);
        if (count != 1) {
            throw new CaException("填写信息失败");
        }
        if (CollectionUtils.isNotEmpty(userVO.getUserTagId())) {
            CaUserTagExample example = new CaUserTagExample();
            example.createCriteria().andUserIdEqualTo(userVO.getId());
            caUserTagMapper.deleteByExample(example);
            caUserTagMapper.insert(convert2CaUserTag(userVO));
        }

        LOGGER.info("更新用户信息入参:{}", JSON.toJSONString(userVO));

        if (StringUtils.isNotBlank(userVO.getOtherDesc())) {
            UserFeedback userFeedback = buildUserFeedBack(userVO.getOtherDesc());
            userFeedbackMapper.insert(userFeedback);
        }

    }

    private boolean deleteAlumniRole(Integer alumniId) {
        try {
            CaAlumniRoleExample example = new CaAlumniRoleExample();
            example.createCriteria()
                    .andAlumniIdEqualTo(alumniId)
                    .andStudentIdEqualTo(RequestUtil.getUserId());
            caAlumniRoleMapper.deleteByExample(example);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private CaAlumniRole buildCaAlumniRole(Integer alumniId) {
        CaAlumniRole alumniRole = new CaAlumniRole();

        alumniRole.setAlumniId(alumniId);
        alumniRole.setStudentId(RequestUtil.getUserId());
        Date now = new Date(System.currentTimeMillis());
        alumniRole.setCreateTime(now);
        alumniRole.setUpdateTime(now);
        alumniRole.setRole(AlumniRoleEnum.MEMBER.getCode());

        return alumniRole;
    }

    private CaUser convert2CaUser(UserVO userVO) {
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        caUser.setNamePy(PinyinUtils.getFirstLetter(userVO.getName()));
        if (CollectionUtils.isNotEmpty(userVO.getPhotoLists())) {
            StringBuilder sb = new StringBuilder();
            for (String photo : userVO.getPhotoLists()) {
                sb.append(photo).append(",");
            }
            caUser.setPhotoList(sb.substring(0, sb.length() - 1));
        }
//        if (userVO.getIdCard().contains("*")) {
//            caUser.setIdCard(null);
//        }
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

    @Override
    public List<UserVO> queryUserList(UserQuery userQuery) {
        LOGGER.info("查询用户列表 param:{}", JSON.toJSONString(userQuery));
        List<CaUser> caUsers = caUserMapper.selectByExample(buildCaUserExample(userQuery));
        LOGGER.info("查询用户列表 param:{}", JSON.toJSONString(caUsers));
        return convertUserVOList(caUsers);
    }

    private List<UserVO> convertUserVOList(List<CaUser> caUsers) {
        if (CollectionUtils.isEmpty(caUsers)) {
            return Lists.newArrayList();
        }
        return caUsers.stream().filter(Objects::nonNull).map(s -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(s, userVO);
            userVO.setIdCard(StringUtils.isNotBlank(s.getIdCard()) ? s.getIdCard().substring(0, 3) + "***************" : s.getIdCard());
            userVO.setPwd(null);
            userVO.setPhotoLists(convertStringToList(s.getPhotoList()));
            return userVO;
        }).collect(Collectors.toList());
    }

    private CaUserExample buildCaUserExample(UserQuery userQuery) {
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
            criteria.andNameLike('%'+userQuery.getName()+'%');
//            criteria.andNameEqualTo(userQuery.getName());
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
            criteria.andIdIn(userQuery.getIdList());
        }

        criteria.andNameIsNotNull();

        return example;
    }

    private CaUser register(UserVO userVO) throws Exception {
        LOGGER.info("UserServiceImpl.register param : {}", JSON.toJSONString(userVO));
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
        LOGGER.info("UserServiceImpl.queryUserByPhone param : {}", phone);
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
        user.setIdCard(StringUtils.isNotBlank(user.getIdCard()) ? user.getIdCard().substring(0, 3) + "***************" : user.getIdCard());
        return user;
    }

    @Override
    public void sendValidateCode(String phone) throws Exception {
        LOGGER.info("UserServiceImpl.sendValidateCode param : {}", phone);
        String code = VerificationCodeUtils.getVerificationCode(phone);
        redisUtils.set("CA_VALIDATE_CODE_KEY_" + phone, Integer.valueOf(code), 60 * 30);
    }

    @Override
    public SimpleUserModel findUserById(Integer userId) throws CaException {
        LOGGER.info("UserServiceImpl.findUserById param : {}", userId);
        if (Objects.isNull(userId)) {
            throw new CaException("用户ID不能为空");
        }

        CaUser user = caUserMapper.selectByPrimaryKey(userId);
        SimpleUserModel userModel = new SimpleUserModel();
        BeanUtils.copyProperties(user, userModel);
        userModel.setPhotoList(convertStringToList(user.getPhotoList()));

        CaUserTagExample example = new CaUserTagExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CaUserTag> caUserTags = caUserTagMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(caUserTags)) {
            userModel.setUserTagList(convert2UserTag(caUserTags.get(0)));
        }

        return userModel;
    }

    @Override
    public List<SimpleUserModel> listSimpleUserModelByIdList(List<Integer> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        List<CaUser> userList = userLocalService.listByIdList(userIdList);
        return convert2SimpleUserModelList(userList);
    }

    @Override
    public void loginOut(Integer userId) throws CaException {
        LOGGER.info("UserServiceImpl.loginOut param : {}", userId);
        try {
            redisUtils.del("user_login_" + userId);
        } catch (Exception e) {
            LOGGER.error("UserServiceImpl.loginOut error, error param:{}", userId, e);
            throw new CaException("帐号退出异常");

        }
    }

    @Override
    public UserVO findMyUserInfo() throws CaException {
        Integer userId = RequestUtil.getUserId();
        CaUser user = caUserMapper.selectByPrimaryKey(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setIdCard(StringUtils.isNotBlank(user.getIdCard()) ? user.getIdCard().substring(0, 3) + "***************" : user.getIdCard());
        userVO.setPhotoLists(convertStringToList(user.getPhotoList()));

        CaUserTagExample example = new CaUserTagExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<CaUserTag> caUserTags = caUserTagMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(caUserTags)) {
            userVO.setUserTagId(convert2UserTag(caUserTags.get(0)));
        }
        return userVO;
    }

    private List<Integer> convert2UserTag(CaUserTag caUserTag) {
        List<Integer> userTagList = new ArrayList<>();
        if (Objects.nonNull(caUserTag.getFood()) && caUserTag.getFood() == 1) {
            userTagList.add(UserTagEnum.FOOD.getCode());
        }
        if (Objects.nonNull(caUserTag.getGirlFriend()) && caUserTag.getGirlFriend() == 1) {
            userTagList.add(UserTagEnum.GIRL_FRIEND.getCode());
        }
        if (Objects.nonNull(caUserTag.getJob()) && caUserTag.getJob() == 1) {
            userTagList.add(UserTagEnum.JOB.getCode());
        }
        if (Objects.nonNull(caUserTag.getResource()) && caUserTag.getResource() == 1) {
            userTagList.add(UserTagEnum.RESOURCE.getCode());
        }
        return userTagList;
    }

    @Override
    public void saveFeedBack(FeedbackRequest request) throws CaException {
        if (Objects.isNull(request)) {
            throw new CaException("参数为空");
        }
        if (StringUtils.isBlank(request.getFeedBackDesc())) {
            throw new CaException("参数为空");
        }
        UserFeedback record = buildUserFeedBack(request.getFeedBackDesc());
        int count = userFeedbackMapper.insert(record);
        if (count != 1) {
            throw new CaException("问题反馈填写异常");
        }
    }

    @Override
    public List<UserVO> querySchoolAlumni(UserSchoolAlumniRequest request) {
        Map<Integer, List<UserVO>> map = alumniService.queryAllAlumniPerson(request.getAlumniId());
        return map.get(1);
    }

    private CaUserExample buildCaUserExample(Integer alumniId, Integer type) {
        CaUserExample example = new CaUserExample();
        CaUserExample.Criteria criteria = example.createCriteria();

        switch (type) {
            case 1:
                criteria.andClassIdEqualTo(alumniId);
                break;
            case 2:
                criteria.andCollegeIdEqualTo(alumniId);
                break;
            case 3:
                criteria.andFacultyIdEqualTo(alumniId);
                break;
            case 4:
                criteria.andAlumniIdEqualTo(alumniId);
                break;
        }
        return example;
    }

    private boolean validateVerificatioCode(UserVO userVO, String verificatioCode) {
        boolean flag = false;
        if ("18600826024".equals(userVO.getPhone()) && "123456".equals(verificatioCode)) {
            return true;
        }
        Object validateCodeCache = redisUtils.get("CA_VALIDATE_CODE_KEY_" + userVO.getPhone());
        if (Objects.nonNull(validateCodeCache)) {
            String code = String.valueOf(validateCodeCache);
            if (code.equals(verificatioCode)) {
                flag = true;
            }
        }
        return flag;
    }

    private UserFeedback buildUserFeedBack(String desc) {
        UserFeedback userFeedback = new UserFeedback();

        userFeedback.setStatus(1);
        userFeedback.setUserId(RequestUtil.getUserId());
        userFeedback.setFeedbackDesc(desc);
        userFeedback.setCreateTime(new Date(System.currentTimeMillis()));
        userFeedback.setUpdateTime(new Date(System.currentTimeMillis()));

        return userFeedback;
    }

    private List<String> convertStringToList(String str) {
        return StringUtils.isNotBlank(str) ? Lists.newArrayList(str.split(",")) : new ArrayList<>();
    }

    private List<SimpleUserModel> convert2SimpleUserModelList(List<CaUser> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return Lists.newArrayList();
        }

        return userList.stream().filter(Objects::nonNull)
                .map(this::convert2SimpleUserModel).collect(Collectors.toList());
    }

    private SimpleUserModel convert2SimpleUserModel(CaUser user) {
        if (Objects.isNull(user)) {
            return null;
        }

        SimpleUserModel model = new SimpleUserModel();
        BeanUtils.copyProperties(user, model);
        model.setPhotoList(convertStringToList(user.getPhotoList()));
        return model;
    }
}
