package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaAlumniAuditLogMapper;
import com.card.alumni.dao.CaAlumniMapper;
import com.card.alumni.dao.CaAlumniRoleMapper;
import com.card.alumni.entity.CaAlumni;
import com.card.alumni.entity.CaAlumniExample;
import com.card.alumni.entity.CaAlumniRole;
import com.card.alumni.entity.CaAlumniRoleExample;
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

import javax.annotation.Resource;
import java.util.*;
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

        return new UnifiedResponse();
    }

    public Map<Integer, List<UserVO>> queryAllAlumniPerson(Integer id) {
        CaAlumniRoleExample example = new CaAlumniRoleExample();
        example.createCriteria().andAlumniIdEqualTo(id);
        List<CaAlumniRole> caAlumniRoles = caAlumniRoleMapper.selectByExample(example);
        Map<Integer, List<UserVO>> resultMap = new HashMap<>();
        UserQuery userQuery = new UserQuery();
        userQuery.setIdList(caAlumniRoles.stream().filter(Objects::nonNull).map(CaAlumniRole::getStudentId).collect(Collectors.toList()));
        userQuery.setPageSize(500);
        UnifiedResponse response = userService.queryUserVO(userQuery);
        if (response.getStatus() == 1) {
            //TODO
        }
        return resultMap;
    }

    @Override
    public UnifiedResponse queryAlumniAudit(Integer id) {
        return null;
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
