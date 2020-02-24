package com.card.alumni.service.impl;

import com.alibaba.fastjson.JSON;
import com.card.alumni.common.PageData;
import com.card.alumni.dao.CaAlumniAuditLogMapper;
import com.card.alumni.dao.CaAlumniMapper;
import com.card.alumni.dao.CaAlumniRoleMapper;
import com.card.alumni.entity.CaAlumni;
import com.card.alumni.entity.CaAlumniAuditLog;
import com.card.alumni.entity.CaAlumniAuditLogExample;
import com.card.alumni.entity.CaAlumniExample;
import com.card.alumni.entity.CaAlumniRole;
import com.card.alumni.entity.CaAlumniRoleExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.AlumniService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.enums.AlumniRoleEnum;
import com.card.alumni.vo.enums.AlumniTypeEnum;
import com.card.alumni.vo.query.AlumniQuery;
import com.card.alumni.vo.query.UserQuery;
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
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:08 PM
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
    public PageData<AlumniVO> queryAlumniService(AlumniQuery alumniQuery) {
        LOGGER.info("查询群组 param:{}", JSON.toJSONString(alumniQuery));
        CaAlumniExample example = buildCaAlumniExample(alumniQuery);

        PageHelper.startPage(alumniQuery.getPage(), alumniQuery.getPageSize());

        List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(example);

        PageInfo<CaAlumni> pageInfo = new PageInfo<>(caAlumni);
        List<AlumniVO> alumniVOS = convertAlumniVOList(caAlumni);
        queryUserAndAlumniRelation(alumniVOS);

        PageData<AlumniVO> alumniVOPageData = new PageData<>(pageInfo.getTotal(), alumniVOS);

        LOGGER.info("查询群组 result:{}", JSON.toJSONString(alumniVOPageData));
        return alumniVOPageData;
    }

    private void queryUserAndAlumniRelation(List<AlumniVO> alumniVOS) {
        CaAlumniAuditLogExample example = new CaAlumniAuditLogExample();
        example.createCriteria().andStudentIdEqualTo(RequestUtil.getUserId());
        List<CaAlumniAuditLog> caAlumniAuditLogs = caAlumniAuditLogMapper.selectByExample(example);
        Map<Integer, CaAlumniAuditLog> caAlumniAuditLogMap = new HashMap<>();

        caAlumniAuditLogs.stream().forEach(s -> {
            CaAlumniAuditLog caAlumniAuditLog = caAlumniAuditLogMap.get(s.getAlumniId());
            if (Objects.isNull(caAlumniAuditLog)) {
                caAlumniAuditLogMap.put(s.getAlumniId(), s);
            } else {
                if (s.getCreateTime().compareTo(caAlumniAuditLog.getCreateTime()) == 1) {
                    caAlumniAuditLogMap.put(s.getAlumniId(), s);
                }
            }
        });

        Map<Integer, Integer> userAlumniMap = caAlumniAuditLogMap.values().stream().collect(Collectors.toMap(CaAlumniAuditLog::getAlumniId, CaAlumniAuditLog::getAuditStatus));

        alumniVOS.stream().forEach(s -> {
            Integer status = userAlumniMap.get(s.getId());
            if (Objects.nonNull(status)) {
                s.setRelation(status);
            }
        });
    }

    @Override
    public AlumniVO queryAlumniDetail(Integer id) throws CaException {
        LOGGER.info("查询协会细节 param:{}", id);
        CaAlumni caAlumni = caAlumniMapper.selectByPrimaryKey(id);
        if (Objects.isNull(caAlumni)) {
            throw new CaException("协会不存在");
        }
        AlumniVO alumniVO = convertAlumniVO(caAlumni);
        Map<Integer, List<UserVO>> userMap = queryAllAlumniPerson(id);
        alumniVO.setLeader(userMap.get(3) == null ? null : userMap.get(0).get(0));
        alumniVO.setAdminVO(userMap.get(2));
        alumniVO.setUserVOList(userMap.get(1));
        LOGGER.info("查询协会细节 result:{}", alumniVO);
        return alumniVO;
    }

    public Map<Integer, List<UserVO>> queryAllAlumniPerson(Integer id) {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andAlumniIdEqualTo(id);
        List<CaAlumniRole> caAlumniRoles = caAlumniRoleMapper.selectByExample(example);
        Map<Integer, List<UserVO>> resultMap = new HashMap<>();
        List<Integer> userIdList = caAlumniRoles.stream().filter(Objects::nonNull).map(CaAlumniRole::getStudentId).collect(Collectors.toList());
        List<UserVO> userVOList = queryByIdList(userIdList);
        if (!CollectionUtils.isEmpty(userVOList)) {
            Map<Integer, UserVO> userVOMap = userVOList.stream().collect(Collectors.toMap(UserVO::getId, Function.identity()));
            caAlumniRoles.stream().filter(Objects::nonNull).forEach(s -> {
                UserVO userVO = userVOMap.get(s.getStudentId());
                resultMap.merge(s.getRole(), Lists.newArrayList(userVO), (o, n) -> {
                    o.addAll(n);
                    return o;
                });
            });
            resultMap.put(1, userVOList);
        }

        return resultMap;
    }

    private List<UserVO> queryByIdList(List<Integer> idList) {
        UserQuery userQuery = new UserQuery();
        userQuery.setIdList(idList);
        PageData<UserVO> userVOPageData = userService.queryUserVO(userQuery);
        List<UserVO> userVOList = null;
        if (Objects.nonNull(userVOPageData)) {
            userVOList = userVOPageData.getItems();
        }
        return userVOList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean auidtAlumniRecord(Integer id, AlumniAuditStatusEnum statusEnum) throws CaException {
        validateUserLimit(RequestUtil.getUserId(), id);

        CaAlumniAuditLog caAlumniAuditLog = caAlumniAuditLogMapper.selectByPrimaryKey(id);

        switch (statusEnum) {
            case EXIT:
                deleteAlumniRole(caAlumniAuditLog);
                break;
            default:
        }
        CaAlumniAuditLog alumniAuditLog = new CaAlumniAuditLog();
        alumniAuditLog.setId(id);
        alumniAuditLog.setAuditStatus(statusEnum.getCode());
        int count = caAlumniAuditLogMapper.updateByPrimaryKeySelective(alumniAuditLog);
        if (count != 1) {
            throw new CaException("审核失败");
        }
        return true;
    }

    @Override
    public List<AlumniVO> queryMyAlumni(Integer userId) {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andStudentIdEqualTo(userId);

        List<CaAlumniRole> caAlumniRoles = caAlumniRoleMapper.selectByExample(example);
        List<AlumniVO> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(caAlumniRoles)) {
            List<Integer> alumniIds = caAlumniRoles.stream().map(CaAlumniRole::getAlumniId).collect(Collectors.toList());
            CaAlumniExample alumniExample  = new CaAlumniExample();
            alumniExample.createCriteria().andIdIn(alumniIds);

            List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(alumniExample);
            resultList = convertAlumniVOList(caAlumni);
        }

        return resultList;
    }

    private void deleteAlumniRole(CaAlumniAuditLog caAlumniAuditLog) throws CaException {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andStudentIdEqualTo(caAlumniAuditLog.getStudentId())
                .andAlumniIdEqualTo(caAlumniAuditLog.getStudentId());
        List<CaAlumniRole> caAlumniRoles = caAlumniRoleMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(caAlumniRoles) || caAlumniRoles.size() != 1) {
            throw new CaException("审核异常");
        }
        CaAlumniRole alumniRole = caAlumniRoles.get(0);
        if (AlumniRoleEnum.LEADER.getCode().equals(alumniRole.getRole())) {
            throw new CaException("会长不能退出协会");
        }
        int count = caAlumniRoleMapper.deleteByPrimaryKey(alumniRole.getId());
        if (count == 0) {
            throw new CaException("审核失败");
        }
    }

    private void validateUserLimit(Integer userId, Integer id) throws CaException {
        CaAlumniRole alumniRole = getCaAlumniRole(userId, id);
        if (Objects.isNull(alumniRole) || (!AlumniRoleEnum.LEADER.getCode().equals(alumniRole.getRole())
                && !AlumniRoleEnum.ADMIN.getCode().equals(alumniRole.getRole()))) {
            throw new CaException("无权限");
        }
    }

    @Override
    public Boolean applyAlumni(Integer alumniId) throws CaException {
        int count = caAlumniAuditLogMapper.insert(buildCaAlumniAuditLog(alumniId));
        if (count != 1) {
            throw new CaException("申请失败");
        }

        return true;
    }

    private CaAlumniAuditLog buildCaAlumniAuditLog(Integer alumniId) {
        CaAlumniAuditLog caAlumniAuditLog = new CaAlumniAuditLog();
        caAlumniAuditLog.setAlumniId(alumniId);
        caAlumniAuditLog.setStudentId(RequestUtil.getUserId());
        caAlumniAuditLog.setAuditStatus(AlumniAuditStatusEnum.APPLY.getCode());
        caAlumniAuditLog.setCreateTime(new Date(System.currentTimeMillis()));
        caAlumniAuditLog.setUpdateTime(new Date(System.currentTimeMillis()));
        return caAlumniAuditLog;
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

    private List<AlumniVO> convertAlumniVOList(List<CaAlumni> caAlumnis) {
        return caAlumnis.stream().map(s -> convertAlumniVO(s)).collect(Collectors.toList());
    }

    private AlumniVO convertAlumniVO(CaAlumni caAlumni) {
        AlumniVO alumniVO = new AlumniVO();
        BeanUtils.copyProperties(caAlumni, alumniVO);
        return alumniVO;
    }

    private CaAlumniExample buildCaAlumniExample(AlumniQuery alumniQuery) {
        CaAlumniExample example = new CaAlumniExample();
        CaAlumniExample.Criteria criteria = example.createCriteria();
        if (Objects.isNull(alumniQuery)) {
            return example;
        }
        if (Objects.nonNull(alumniQuery.getId())) {
            criteria.andIdEqualTo(alumniQuery.getId());
        }
        if (Objects.nonNull(alumniQuery.getPartentId())) {
            criteria.andPartentIdEqualTo(alumniQuery.getPartentId());
        }
        if (Objects.nonNull(alumniQuery.getName())) {
            criteria.andNameLike("%" + alumniQuery.getName() + "%");
        }
        if (Objects.nonNull(alumniQuery.getType())) {
            criteria.andTypeEqualTo(alumniQuery.getType());
        }
        return example;
    }
}
