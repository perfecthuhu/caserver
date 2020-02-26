package com.card.alumni.service.impl;

import com.alibaba.fastjson.JSON;
import com.card.alumni.common.PageData;
import com.card.alumni.dao.CaAlumniAuditLogMapper;
import com.card.alumni.dao.CaAlumniMapper;
import com.card.alumni.dao.CaAlumniRoleMapper;
import com.card.alumni.entity.*;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.AlumniAuditModel;
import com.card.alumni.model.AlumniModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.model.enums.AlumniAuditStatusEnum;
import com.card.alumni.model.enums.AlumniRoleEnum;
import com.card.alumni.model.enums.AlumniTypeEnum;
import com.card.alumni.request.AlumniRequest;
import com.card.alumni.request.UserQueryRequest;
import com.card.alumni.service.AlumniService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 组织服务
 *
 * @author liumingyu
 * @date 2019-12-29 11:34 AM
 */
@Service
public class AlumniServiceImpl implements AlumniService {

    private final Logger LOGGER = LoggerFactory.getLogger(AlumniServiceImpl.class);

    @Resource
    private CaAlumniMapper caAlumniMapper;

    @Resource
    private CaAlumniAuditLogMapper caAlumniAuditLogMapper;

    @Resource
    private CaAlumniRoleMapper caAlumniRoleMapper;

    @Resource
    private UserService userService;

    @Override
    public PageData<AlumniModel> queryAlumniService(AlumniRequest alumniRequest) {
        LOGGER.info("查询群组 param:{}", JSON.toJSONString(alumniRequest));
        CaAlumniExample example = buildCaAlumniExample(alumniRequest);
        PageHelper.startPage(alumniRequest.getPage(), alumniRequest.getSize());
        List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(example);
        PageInfo<CaAlumni> pageInfo = new PageInfo<>(caAlumni);
        PageData<AlumniModel> alumniVOPageData = new PageData<>(pageInfo.getTotal(), convertAlumniVOList(caAlumni));
        LOGGER.info("查询群组 result:{}", JSON.toJSONString(alumniVOPageData));
        return alumniVOPageData;
    }

    private List<AlumniModel> convertAlumniVOList(List<CaAlumni> caAlumni) {

        return caAlumni.stream().map(this::convertAlumniModel).collect(Collectors.toList());
    }

    private AlumniModel convertAlumniModel(CaAlumni caAlumni) {
        AlumniModel alumniModel = new AlumniModel();
        BeanUtils.copyProperties(caAlumni, alumniModel);
        return alumniModel;
    }

    private CaAlumniExample buildCaAlumniExample(AlumniRequest alumniRequest) {

        CaAlumniExample example = new CaAlumniExample();
        CaAlumniExample.Criteria criteria = example.createCriteria();

        if (Objects.isNull(alumniRequest)) {
            return example;
        }

        if (Objects.nonNull(alumniRequest.getId())) {
            criteria.andIdEqualTo(alumniRequest.getId());
        }

        if (Objects.nonNull(alumniRequest.getPartentId())) {
            criteria.andPartentIdEqualTo(alumniRequest.getPartentId());
        }

        if (Objects.nonNull(alumniRequest.getName())) {
            criteria.andNameLike(alumniRequest.getName());
        }

        if (Objects.nonNull(alumniRequest.getType())) {
            criteria.andTypeEqualTo(alumniRequest.getType());
        }

        return example;
    }

    @Override
    public AlumniModel queryAlumniDetail(Integer alumniId) throws CaConfigException {
        LOGGER.info("查询协会细节 param:{}", alumniId);

        CaAlumni caAlumni = caAlumniMapper.selectByPrimaryKey(alumniId);
        if (Objects.isNull(caAlumni)) {
            throw new CaConfigException("协会不存在");
        }

        AlumniModel alumniVO = convertAlumniModel(caAlumni);
        Map<Integer, List<UserModel>> userMap = queryAllAlumniPerson(alumniId);
        alumniVO.setLeader(userMap.get(3) == null ? null : userMap.get(0).get(0));
        alumniVO.setAdminVO(userMap.get(2));
        alumniVO.setUserVOList(userMap.get(1));
        LOGGER.info("查询协会细节 result:{}", alumniVO);
        return alumniVO;
    }

    public Map<Integer, List<UserModel>> queryAllAlumniPerson(Integer id) throws CaConfigException {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andAlumniIdEqualTo(id);
        List<CaAlumniRole> caAlumniRoles = caAlumniRoleMapper.selectByExample(example);
        Map<Integer, List<UserModel>> resultMap = new HashMap<>();
        List<Integer> userIdList = caAlumniRoles.stream().filter(Objects::nonNull).map(CaAlumniRole::getStudentId).collect(Collectors.toList());
        List<UserModel> userModelList = queryByIdList(userIdList);
        if (!CollectionUtils.isEmpty(userModelList)) {
            Map<Integer, UserModel> userModelMap = userModelList.stream().collect(Collectors.toMap(UserModel::getId, Function.identity()));
            caAlumniRoles.stream().filter(Objects::nonNull).forEach(s -> {
                UserModel userVO = userModelMap.get(s.getStudentId());
                resultMap.merge(s.getRole(), Lists.newArrayList(userVO), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
            });
            resultMap.put(1, userModelList);
        }

        return resultMap;
    }

    private List<UserModel> queryByIdList(List<Integer> idList) throws CaConfigException {

        UserQueryRequest userQuery = new UserQueryRequest();
        userQuery.setUserIdList(idList);
        PageData<UserModel> userModelPageData = userService.pageByRequest(userQuery);
        List<UserModel> userVOList = null;
        if (Objects.nonNull(userModelPageData)) {
            userVOList = userModelPageData.getItems();
        }
        return userVOList;
    }

    @Override
    public List<UserModel> queryAlumniAudit(Integer alumniId) throws CaConfigException {
        LOGGER.info("查询协会代审信息 param:{}", alumniId);
        CaAlumniAuditLogExample example = new CaAlumniAuditLogExample();
        example.createCriteria()
                .andAlumniIdEqualTo(alumniId)
                .andAuditStatusEqualTo(AlumniAuditStatusEnum.APPLY.getCode());
        List<CaAlumniAuditLog> caAlumniAuditLogs = caAlumniAuditLogMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(caAlumniAuditLogs)) {
            return null;
        }
        List<Integer> userIdList = caAlumniAuditLogs.stream().map(CaAlumniAuditLog::getStudentId).collect(Collectors.toList());
        List<UserModel> userVOList = queryByIdList(userIdList);
        LOGGER.info("查询协会代审信息 result:{}", userVOList);
        return userVOList;
    }

    @Override
    public Boolean auidtAlumniRecord(Integer alumniId, AlumniAuditStatusEnum status) throws CaException {

        CaAlumniAuditLog caAlumniAuditLog = caAlumniAuditLogMapper.selectByPrimaryKey(alumniId);

        switch (status) {
            case PASS:
                insertAlumniRole(caAlumniAuditLog);
                break;
            case EXIT:
                deleteAlumniRole(caAlumniAuditLog);
                break;
            default:
        }
        CaAlumniAuditLog alumniAuditLog = new CaAlumniAuditLog();
        alumniAuditLog.setId(alumniId);
        alumniAuditLog.setAuditStatus(status.getCode());
        int count = caAlumniAuditLogMapper.updateByPrimaryKeySelective(alumniAuditLog);
        if (count != 1) {
            throw new CaConfigException("审核失败");
        }
        return true;
    }

    private void deleteAlumniRole(CaAlumniAuditLog caAlumniAuditLog) {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria()
                .andAlumniIdEqualTo(caAlumniAuditLog.getAlumniId())
                .andStudentIdEqualTo(caAlumniAuditLog.getStudentId());
        int count = caAlumniRoleMapper.deleteByExample(example);
        if (count != 1) {
            throw new CaConfigException("审核失败");
        }
    }

    private void insertAlumniRole(CaAlumniAuditLog caAlumniAuditLog) throws CaException  {
        CaAlumniRole alumniRole = convertCaAlumniRole(caAlumniAuditLog);
        int count = caAlumniRoleMapper.insert(alumniRole);
        if (count != 1) {
            throw new CaConfigException("审核失败");
        }
    }

    private CaAlumniRole convertCaAlumniRole(CaAlumniAuditLog caAlumniAuditLog) {
        CaAlumniRole alumniRole = new CaAlumniRole();
        alumniRole.setAlumniId(caAlumniAuditLog.getAlumniId());
        alumniRole.setStudentId(caAlumniAuditLog.getStudentId());
        alumniRole.setCreateTime(new Date(System.currentTimeMillis()));
        alumniRole.setUpdateTime(new Date(System.currentTimeMillis()));
        alumniRole.setRole(AlumniRoleEnum.MEMBER.getCode());

        return alumniRole;

    }

    private void validateUserLimit(Integer userId, Integer id) throws CaException {
        CaAlumniRole alumniRole = getCaAlumniRole(userId, id);
        if (Objects.isNull(alumniRole) || (!AlumniRoleEnum.LEADER.getCode().equals(alumniRole.getRole())
                && !AlumniRoleEnum.ADMIN.getCode().equals(alumniRole.getRole()))) {
            throw new CaException("无权限");
        }
    }

    private CaAlumniRole getCaAlumniRole(Integer userId, Integer alumniId) {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria()
                .andAlumniIdEqualTo(alumniId)
                .andStudentIdEqualTo(userId);
        List<CaAlumniRole> caAlumniRoles = caAlumniRoleMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(caAlumniRoles)) {
            return null;
        }
        return caAlumniRoles.get(0);
    }

    @Override
    public Boolean appointAdmin(Integer alumniId, Integer userId) throws CaException {
        //validateAppointAdmin(RequestUtil.getUserId(), alumniId);
        CaAlumniRole role = new CaAlumniRole();
        role.setRole(AlumniRoleEnum.ADMIN.getCode());
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andAlumniIdEqualTo(alumniId).andStudentIdEqualTo(userId);
        int count = caAlumniRoleMapper.updateByExampleSelective(role, example);
        if (count != 1) {
            throw new CaConfigException("分配管理员失败");
        }
        return true;
    }

    private void validateAppointAdmin(Integer userId, Integer id) throws CaException {
        CaAlumniRole caAlumniRole = getCaAlumniRole(userId, id);
        if (Objects.isNull(caAlumniRole) || !AlumniRoleEnum.LEADER.getCode().equals(caAlumniRole.getRole())) {
            throw new CaException("无权限");
        }
    }

    @Override
    public Boolean createAlumni(AlumniModel alumniModel) throws CaException {
        LOGGER.info("创建协会 param:{}", JSON.toJSONString(alumniModel));
        validateAlimniVO(alumniModel);
        CaAlumni alumni = buildCaAlumni(alumniModel);
        int count = caAlumniMapper.insert(alumni);
        if (count != 1) {
            throw new CaException("创建协会失败");
        }
        CaAlumniRole caAlumniRole = new CaAlumniRole();
        caAlumniRole.setStudentId(RequestUtil.getUserId());
        caAlumniRole.setAlumniId(alumni.getId());
        caAlumniRole.setRole(AlumniRoleEnum.LEADER.getCode());
        caAlumniRole.setCreateTime(new Date(System.currentTimeMillis()));
        caAlumniRole.setUpdateTime(new Date(System.currentTimeMillis()));
        count = caAlumniRoleMapper.insert(caAlumniRole);
        if (count != 1) {
            throw new CaConfigException("创建协会失败");
        }
        return true;
    }

    private void validateAlimniVO(AlumniModel alumniVO) throws CaException {
        if (Objects.isNull(alumniVO)) {
            throw new CaConfigException("协会信息为空");
        }
        if (Objects.isNull(alumniVO.getName()))  {
            throw new CaConfigException("协会名称为空");
        }
    }

    private CaAlumni buildCaAlumni(AlumniModel alumniVO) {
        CaAlumni alumni = new CaAlumni();
        BeanUtils.copyProperties(alumniVO, alumni);
        return alumni;
    }

    @Override
    public Boolean updateAlimni(AlumniModel alumniModel) throws CaException {
        LOGGER.info("更想协会信息 param:{}", JSON.toJSONString(alumniModel));
        //validateUserLimit(RequestUtil.getUserId(), alumniModel.getId());
        CaAlumni alumni = convertAlumniVO(alumniModel);
        int count = caAlumniMapper.updateByPrimaryKeySelective(alumni);
        if (count != 1) {
            throw new CaConfigException("协会信息保存失败");
        }
        return true;
    }

    @Override
    public PageData<AlumniAuditModel> queryAllAlumniAudit(AlumniRequest queryRequest) {
        LOGGER.info("查询所有协会审核信息 param:{}", JSON.toJSONString(queryRequest));
        CaAlumniAuditLogExample example = new CaAlumniAuditLogExample();

        example.createCriteria()
                .andAuditStatusEqualTo(queryRequest.getAuditStatus());

        PageHelper.startPage(queryRequest.getPage(), queryRequest.getSize());
        List<CaAlumniAuditLog> caAlumniAuditLogs = caAlumniAuditLogMapper.selectByExample(example);
        PageInfo<CaAlumniAuditLog> pageInfo = new PageInfo<>(caAlumniAuditLogs);

        if (CollectionUtils.isEmpty(caAlumniAuditLogs)) {
            return null;
        }

        List<Integer> userIdList = caAlumniAuditLogs.stream().map(CaAlumniAuditLog::getStudentId).collect(Collectors.toList());
        List<UserModel> userVOList = queryByIdList(userIdList);

        if (Objects.isNull(userVOList)) {
            throw new CaConfigException("查询用户信息失败");
        }

        Set<Integer> alumniIdList = caAlumniAuditLogs.stream().map(CaAlumniAuditLog::getAlumniId).collect(Collectors.toSet());
        List<AlumniModel> alumniModelList = queryAlumniByIdList(alumniIdList);
        if (alumniModelList == null) {
            throw new CaConfigException("查询协会失败");
        }

        Map<Integer, UserModel> userModelMap = userVOList.stream().collect(Collectors.toMap(UserModel::getId, Function.identity()));

        Map<Integer, AlumniModel> alumniModelMap = alumniModelList.stream().collect(Collectors.toMap(AlumniModel::getId, Function.identity()));

        List<AlumniAuditModel> resultList = caAlumniAuditLogs.stream().map(s -> {
            AlumniAuditModel alumniAuditModel = new AlumniAuditModel();
            alumniAuditModel.setId(s.getId());
            alumniAuditModel.setAlumniModel(alumniModelMap.get(s.getAlumniId()));
            alumniAuditModel.setUserModel(userModelMap.get(s.getStudentId()));
            return alumniAuditModel;
        }).collect(Collectors.toList());

        PageData<AlumniAuditModel> userModelPageData = new PageData<>(pageInfo.getTotal(), resultList);

        LOGGER.info("查询所有协会审核信息 result:{}", userModelPageData);

        return userModelPageData;
    }

    private List<AlumniModel> queryAlumniByIdList(Set<Integer> alumniIdList) {
        CaAlumniExample example = new CaAlumniExample();
        example.createCriteria().andIdIn(Lists.newArrayList(alumniIdList));
        List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(caAlumni)) {
            return null;
        }
        return convertAlumniVOList(caAlumni);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAlumni(Integer alumniId) {
        caAlumniMapper.deleteByPrimaryKey(alumniId);

        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andAlumniIdEqualTo(alumniId);
        caAlumniRoleMapper.deleteByExample(example);

        CaAlumniAuditLogExample example1 = new CaAlumniAuditLogExample();
        example1.createCriteria().andAlumniIdEqualTo(alumniId);
        caAlumniAuditLogMapper.deleteByExample(example1);

        return true;
    }

    @Override
    public Boolean exitAlumni(Integer alumniId, Integer id, AlumniAuditStatusEnum exit) {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria()
                .andStudentIdEqualTo(id)
                .andAlumniIdEqualTo(alumniId);
        caAlumniRoleMapper.deleteByExample(example);
        return true;
    }

    private CaAlumni convertAlumniVO(AlumniModel alumniVO) {
        CaAlumni caAlumni = new CaAlumni();
        BeanUtils.copyProperties(alumniVO, caAlumni);
        return caAlumni;
    }
}
