package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaAlumniMapper;
import com.card.alumni.entity.CaAlumni;
import com.card.alumni.entity.CaAlumniExample;
import com.card.alumni.entity.CaUser;
import com.card.alumni.service.AlumniService;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.query.AlumniQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:08 PM
 */
@Service
public class AlumniServiceImpl implements AlumniService {

    @Resource
    private CaAlumniMapper caAlumniMapper;

    @Override
    public UnifiedResponse queryAlumniService(AlumniQuery alumniQuery) {
        CaAlumniExample example = buildCaAlumniExample(alumniQuery);
        PageHelper.startPage(alumniQuery.getCurrentPage(), alumniQuery.getPageSize());
        List<CaAlumni> caAlumni = caAlumniMapper.selectByExample(example);
        PageInfo<CaAlumni> pageInfo = new PageInfo<>(caAlumni);
        return new UnifiedResponse(new PageData<>(pageInfo.getTotal(), convertAlumniVOList(caAlumni)));
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
        if (Objects.isNull(alumniQuery.getId())) {
            criteria.andIdEqualTo(alumniQuery.getId());
        }
        if (Objects.isNull(alumniQuery.getPartentId())) {
            criteria.andPartentIdEqualTo(alumniQuery.getPartentId());
        }
        if (Objects.isNull(alumniQuery.getName())) {
            criteria.andNameLike(alumniQuery.getName());
        }
        if (Objects.isNull(alumniQuery.getType())) {
            criteria.andTypeEqualTo(alumniQuery.getType());
        }
        return example;
    }
}
