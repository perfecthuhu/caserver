package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.UserContext;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.entity.CaRole;
import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.exception.ResultCodeEnum;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.CookieUtils;
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

    @Override
    public UnifiedResponse login(UserVO userVO, String verificatioCode) throws Exception  {
        if (validateVerificatioCode(userVO, verificatioCode)) {
            throw new CaException("验证码错误");
        }
        //TODO 增加cookie
        return new UnifiedResponse();
    }

    @Override
    public UnifiedResponse queryUserById(Integer userId){
        try {
            if (Objects.isNull(userId)) {
                return new UnifiedResponse(ResultCodeEnum.PARAM_EMPTY);
            }
            CaUser caUser = caUserMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(caUser)) {
                return new UnifiedResponse(caUser);
            }
        } catch (Exception e) {
            LOGGER.error("查询用户异常, 入参:{}", userId, e);
        }
        return null;
    }

    @Override
    public UnifiedResponse submitUserInfo(UserVO userVO) throws Exception {
        CaUser caUser = new CaUser();
        BeanUtils.copyProperties(userVO, caUser);
        int count = caUserMapper.updateByPrimaryKey(caUser);
        if (count != 1) {
            throw new CaException("填写信息失败");
        }
        return new UnifiedResponse();
    }

    @Override
    public UnifiedResponse queryUserVO(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getCurrentPage(), userQuery.getPageSize());
        List<CaUser> caUsers = caUserMapper.selectByExample(buildCaUserExample(userQuery));
        PageInfo<CaUser> pageInfo = new PageInfo<>(caUsers);
        return new UnifiedResponse(new PageData<>(pageInfo.getTotal(), convertUserVOList(caUsers)));
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
        if (Objects.isNull(userQuery.getId())) {
            criteria.andIdEqualTo(userQuery.getId());
        }
        if (Objects.isNull(userQuery.getPhone())) {
            criteria.andPhoneEqualTo(userQuery.getPhone());
        }
        if (Objects.isNull(userQuery.getName())) {
            criteria.andNameEqualTo(userQuery.getName());
        }
        if (Objects.isNull(userQuery.getSex())) {
            criteria.andSexEqualTo(userQuery.getSex());
        }
        if (Objects.isNull(userQuery.getClassId())) {
            criteria.andIdEqualTo(userQuery.getClassId());
        }
        if (Objects.isNull(userQuery.getCollegeId())) {
            criteria.andCollegeIdEqualTo(userQuery.getCollegeId());
        }
        if (Objects.isNull(userQuery.getFacultyId())) {
            criteria.andFacultyIdEqualTo(userQuery.getFacultyId());
        }
        if (Objects.isNull(userQuery.getAlumniId())) {
            criteria.andAlumniIdEqualTo(userQuery.getAlumniId());
        }
        if (Objects.isNull(userQuery.getSchoolId())) {
            criteria.andSchoolIdEqualTo(userQuery.getSchoolId());
        }
        return example;
    }

    @Override
    public UnifiedResponse register(UserVO userVO, String verificatioCode) throws Exception {
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
        return new UnifiedResponse();
    }

    private boolean validateVerificatioCode(UserVO userVO, String verificatioCode) {
        boolean flag = false;
        String code = VerificationCodeUtils.getVerificationCode(userVO.getPhone());
        if (code.equals(verificatioCode)) {
            flag = true;
        }
        return flag;
    }
}
