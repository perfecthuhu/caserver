package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaAlumniAuditLogMapper;
import com.card.alumni.dao.CaAlumniMapper;
import com.card.alumni.dao.CaAlumniRoleMapper;
import com.card.alumni.entity.*;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.AlumniService;
import com.card.alumni.service.UserService;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.AlumniQuery;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
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
    public UnifiedResponse queryAlumniService(AlumniQuery alumniQuery) {
        CaAlumniExample example = buildCaAlumniExample(alumniQuery);
        PageHelper.startPage(alumniQuery.getCurrentPage(), alumniQuery.getPageSize());
        List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(example);
        PageInfo<CaAlumni> pageInfo = new PageInfo<>(caAlumni);
        return new UnifiedResponse(new PageData<>(pageInfo.getTotal(), convertAlumniVOList(caAlumni)));
    }

    @Override
    public UnifiedResponse queryAlumniDetail(Integer id) throws CaException {
        CaAlumni caAlumni = caAlumniMapper.selectByPrimaryKey(id);
        if (Objects.isNull(caAlumni)) {
            throw new CaException("协会不存在");
        }
        AlumniVO alumniVO = convertAlumniVO(caAlumni);
        Map<Integer, List<UserVO>> userMap = queryAllAlumniPerson(id);
        alumniVO.setLeader(userMap.get(3) == null ? null : userMap.get(0).get(0));
        alumniVO.setAdminVO(userMap.get(2));
        alumniVO.setUserVOList(userMap.get(1));
        return new UnifiedResponse();
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
                UserVO userVO = userVOMap.get(s.getStudentId();
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
        userQuery.setIdList();
        UnifiedResponse response = userService.queryUserVO(userQuery);
        List<UserVO> userVOList = null;
        if (response.getStatus() == 1) {
            PageData<UserVO> data = (PageData<UserVO>) response.getData();
            userVOList = data.getItems();
        }
        return userVOList;
    }

    @Override
    public UnifiedResponse queryAlumniAudit(Integer id) {
        CaAlumniAuditLogExample example = new CaAlumniAuditLogExample();
        example.createCriteria()
        caAlumniAuditLogMapper.selectByExample()
    }

    @Override
    public UnifiedResponse auidtAlumniRecord(Integer id, Integer status) {
        return null;
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
