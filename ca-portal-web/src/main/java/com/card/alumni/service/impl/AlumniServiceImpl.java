package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaAlumniAuditLogMapper;
import com.card.alumni.dao.CaAlumniMapper;
import com.card.alumni.dao.CaAlumniRoleMapper;
import com.card.alumni.entity.*;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.AlumniService;
import com.card.alumni.service.BaseService;
import com.card.alumni.service.UserService;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.enums.AlumniRoleEnum;
import com.card.alumni.vo.query.AlumniQuery;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:08 PM
 */
@Service
public class AlumniServiceImpl extends BaseService implements AlumniService {

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
        CaAlumniExample example = buildCaAlumniExample(alumniQuery);
        PageHelper.startPage(alumniQuery.getCurrentPage(), alumniQuery.getPageSize());
        List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(example);
        PageInfo<CaAlumni> pageInfo = new PageInfo<>(caAlumni);
        return new PageData<>(pageInfo.getTotal(), convertAlumniVOList(caAlumni));
    }

    @Override
    public AlumniVO queryAlumniDetail(Integer id) throws CaException {
        CaAlumni caAlumni = caAlumniMapper.selectByPrimaryKey(id);
        if (Objects.isNull(caAlumni)) {
            throw new CaException("协会不存在");
        }
        AlumniVO alumniVO = convertAlumniVO(caAlumni);
        Map<Integer, List<UserVO>> userMap = queryAllAlumniPerson(id);
        alumniVO.setLeader(userMap.get(3) == null ? null : userMap.get(0).get(0));
        alumniVO.setAdminVO(userMap.get(2));
        alumniVO.setUserVOList(userMap.get(1));
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
                resultMap.merge(s.getRole(), Arrays.asList(userVO), (o, n) -> {
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
    public List<UserVO> queryAlumniAudit(Integer id) {
        CaAlumniAuditLogExample example = new CaAlumniAuditLogExample();
        example.createCriteria()
                .andAlumniIdEqualTo(id)
                .andAuditStatusEqualTo(AlumniAuditStatusEnum.APPLY.getCode());
        List<CaAlumniAuditLog> caAlumniAuditLogs = caAlumniAuditLogMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(caAlumniAuditLogs)) {
            return null;
        }
        List<Integer> userIdList = caAlumniAuditLogs.stream().map(CaAlumniAuditLog::getStudentId).collect(Collectors.toList());
        return queryByIdList(userIdList);
    }

    @Override
    public Boolean auidtAlumniRecord(Integer id, AlumniAuditStatusEnum statusEnum) throws CaException {
        validateUserLimit(getUserId(), id);
        CaAlumniAuditLog alumniAuditLog = new CaAlumniAuditLog();
        alumniAuditLog.setId(id);
        alumniAuditLog.setAuditStatus(statusEnum.getCode());
        int count = caAlumniAuditLogMapper.updateByPrimaryKeySelective(alumniAuditLog);
        if (count != 1) {
            throw new CaException("审核失败");
        }
        return true;
    }

    private void validateUserLimit(Integer userId, Integer id) throws CaException {
        CaAlumniRole alumniRole = getCaAlumniRole(userId, id);
        if (Objects.isNull(alumniRole) || (!AlumniRoleEnum.LEADER.getCode().equals(alumniRole.getRole())
                && !AlumniRoleEnum.ADMIN.getCode().equals(alumniRole.getRole()))) {
            throw new CaException("无权限");
        }
    }

    @Override
    public Boolean appointAdmin(Integer id, Integer userId) throws CaException {
        validateAppointAdmin(getUserId(), id);
        CaAlumniRole role = new CaAlumniRole();
        role.setRole(AlumniRoleEnum.ADMIN.getCode());
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andAlumniIdEqualTo(id).andStudentIdEqualTo(userId);
        int count = caAlumniRoleMapper.updateByExampleSelective(role, example);
        if (count != 1) {
            throw new CaException("分配管理员失败");
        }
        return true;
    }

    @Override
    public Boolean applyAlumni(Integer alumniId) throws CaException {
        //TODO
        return null;
    }

    private void validateAppointAdmin(Integer userId, Integer id) throws CaException {
        CaAlumniRole caAlumniRole = getCaAlumniRole(userId, id);
        if (Objects.isNull(caAlumniRole) || !AlumniRoleEnum.LEADER.getCode().equals(caAlumniRole.getRole())) {
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
            criteria.andNameLike(alumniQuery.getName());
        }
        if (Objects.nonNull(alumniQuery.getType())) {
            criteria.andTypeEqualTo(alumniQuery.getType());
        }
        return example;
    }
}
