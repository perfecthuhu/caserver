package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.constant.CaConstants;
import com.card.alumni.dao.CaSchoolMapper;
import com.card.alumni.entity.CaSchool;
import com.card.alumni.entity.CaSchoolExample;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.model.SchoolModel;
import com.card.alumni.request.SchoolQueryRequest;
import com.card.alumni.request.SchoolRequest;
import com.card.alumni.service.SchoolService;
import com.card.alumni.utils.RequestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 学校服务
 *
 * @author liumingyu
 * @date 2019-12-15 1:45 PM
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private CaSchoolMapper caSchoolMapper;

    @Override
    public Integer save(SchoolRequest request) throws CaConfigException {

        checkParam(request);

        CaSchool entity = convert(request);

        Date now = new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        Integer userId = RequestUtil.getUserId();
        entity.setCreator(userId);
        entity.setUpdater(userId);

        entity.setIsDelete(Boolean.FALSE);

        caSchoolMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public void update(SchoolRequest request) throws CaConfigException {

        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaConfigException("主键ID不能为空");
        }

        CaSchool entity = convert(request);

        entity.setUpdateTime(new Date());
        entity.setUpdater(RequestUtil.getUserId());

        caSchoolMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public void deleteById(Integer id) throws CaConfigException {
        CaSchool school = findById(id);
        if (Objects.isNull(school) || school.getIsDelete()) {
            throw new CaConfigException("当前学校不存在或已被删除");
        }

        school.setUpdateTime(new Date());
        school.setUpdater(RequestUtil.getUserId());
        school.setIsDelete(Boolean.TRUE);

        caSchoolMapper.updateByPrimaryKeySelective(school);
    }

    @Override
    public CaSchool findById(Integer id) throws CaConfigException {
        if (Objects.isNull(id)) {
            throw new CaConfigException("主键ID不能为空");
        }

        return caSchoolMapper.selectByPrimaryKey(id);
    }

    @Override
    public SchoolModel findModelById(Integer id) throws CaConfigException {

        CaSchool school = findById(id);

        return convert2Model(school);
    }

    @Override
    public PageData<SchoolModel> pageByRequest(SchoolQueryRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        int page = Objects.isNull(request.getPage()) ? 1 : request.getPage();
        int size = Objects.isNull(request.getSize()) ? 20 : request.getSize();

        PageHelper.startPage(page, size);

        CaSchoolExample example = new CaSchoolExample();
        CaSchoolExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        if (StringUtils.isNotBlank(request.getKeyword())) {
            criteria.andNameLike(CaConstants.LIKE + request.getKeyword() + CaConstants.LIKE);
        }
        List<CaSchool> schoolList = caSchoolMapper.selectByExample(example);
        PageInfo<CaSchool> pageInfo = new PageInfo<>(schoolList);

        return new PageData<>(pageInfo.getTotal(), convert2ModelList(schoolList));
    }

    private void checkParam(SchoolRequest request) throws CaConfigException {
        if (Objects.isNull(request)) {
            throw new CaConfigException("请求不能为空");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new CaConfigException("名称不能为空");
        }
    }

    private List<SchoolModel> convert2ModelList(List<CaSchool> entries) {
        if (CollectionUtils.isEmpty(entries)) {
            return Lists.newArrayList();
        }

        return entries.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private SchoolModel convert2Model(CaSchool entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        SchoolModel model = new SchoolModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAddress(entity.getAddress());
        model.setWebSite(entity.getWebSite());
        model.setTel(entity.getTel());
        model.setEmail(entity.getEmail());
        model.setPhotoImg(entity.getPhotoImg());
        model.setDesc(entity.getDesc());
        model.setCreateTime(entity.getCreateTime());
        model.setUpdateTime(entity.getUpdateTime());
        return model;
    }

    private CaSchool convert(SchoolRequest request) {
        CaSchool entity = new CaSchool();
        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        entity.setWebSite(request.getWebSite());
        entity.setTel(request.getTel());
        entity.setEmail(request.getEmail());
        entity.setPhotoImg(request.getPhotoImg());
        entity.setDesc(request.getDesc());
        return entity;
    }
}
