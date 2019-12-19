package com.card.alumni.service.impl;

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
        PageHelper.startPage(alumniQuery.getPage(), alumniQuery.getPageSize());
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean auidtAlumniRecord(Integer id, AlumniAuditStatusEnum statusEnum) throws CaException {
        validateUserLimit(RequestUtil.getUserId(), id);

        CaAlumniAuditLog caAlumniAuditLog = caAlumniAuditLogMapper.selectByPrimaryKey(id);

        switch (statusEnum) {
            case PASS:
                insertAlumniRole(caAlumniAuditLog);
                break;
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

    private void insertAlumniRole(CaAlumniAuditLog caAlumniAuditLog) throws CaException  {
        CaAlumniRole alumniRole = convertCaAlumniRole(caAlumniAuditLog);
        int count = caAlumniRoleMapper.insert(alumniRole);
        if (count != 1) {
            throw new CaException("审核失败");
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

    @Override
    public Boolean appointAdmin(Integer id, Integer userId) throws CaException {
        validateAppointAdmin(RequestUtil.getUserId(), id);
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
        int count = caAlumniAuditLogMapper.insert(buildCaAlumniAuditLog(alumniId));
        if (count == 1) {
            throw new CaException("申请失败");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createAlumni(AlumniVO alumniVO) throws CaException {
        validateAlimniVO(alumniVO);
        CaAlumni alumni = buildCaAlumni(alumniVO);
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
            throw new CaException("创建协会失败");
        }
        return true;
    }

    @Override
    public Boolean updateAlimni(AlumniVO alumniVO) throws CaException {
        validateUserLimit(RequestUtil.getUserId(), alumniVO.getId());
        CaAlumni alumni = convertAlumniVO(alumniVO);
        int count = caAlumniMapper.updateByPrimaryKeySelective(alumni);
        if (count != 1) {
            throw new CaException("协会信息保存失败");
        }
        return true;
    }

    private void validateAlimniVO(AlumniVO alumniVO) throws CaException {
        if (Objects.isNull(alumniVO)) {
            throw new CaException("协会信息为空");
        }
        if (Objects.isNull(alumniVO.getName()))  {
            throw new CaException("协会名称为空");
        }
    }

    private CaAlumni buildCaAlumni(AlumniVO alumniVO) {
        CaAlumni alumni = new CaAlumni();
        BeanUtils.copyProperties(alumniVO, alumni);
        alumni.setPartentId(AlumniTypeEnum.ASSOCIATION.getCode());
        alumni.setType(AlumniTypeEnum.ASSOCIATION.getCode());
        return alumni;
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

    private CaAlumni convertAlumniVO(AlumniVO alumniVO) {
        CaAlumni caAlumni = new CaAlumni();
        BeanUtils.copyProperties(alumniVO, caAlumni);
        return caAlumni;
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
