package com.card.alumni.service.impl;

import com.card.alumni.dao.CaConfigMapper;
import com.card.alumni.entity.CaConfig;
import com.card.alumni.entity.CaConfigExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.ConfigModel;
import com.card.alumni.request.ConfigRequest;
import com.card.alumni.service.ConfigService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 配置服务
 *
 * @author liumingyu
 * @date 2020-03-14 2:00 PM
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private CaConfigMapper caConfigMapper;

    @Override
    public Integer save(ConfigRequest request) {
        checkParam(request);

        CaConfig exist = findByKey(request.getKey());
        if (Objects.nonNull(exist)) {
            throw new CaException("key已存在");
        }

        CaConfig entity = convert(request);

        caConfigMapper.insert(entity);

        return entity.getId();
    }

    @Override
    public Integer update(ConfigRequest request) {
        checkParam(request);

        if (Objects.isNull(request.getId())) {
            throw new CaException("主键ID不能为空");
        }

        CaConfig exist = findByKey(request.getKey());
        if (Objects.nonNull(exist) && !exist.getId().equals(request.getId())) {
            throw new CaException("key已存在");
        }

        CaConfig entity = convert(request);

        caConfigMapper.updateByPrimaryKeySelective(entity);

        return entity.getId();
    }

    @Override
    public void deleteById(Integer id) {
        if (Objects.isNull(id)) {
            throw new CaException("主键ID不能为空");
        }

        caConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public CaConfig findById(Integer id) {
        if (Objects.isNull(id)) {
            throw new CaException("主键ID不能为空");
        }

        return caConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public CaConfig findByKey(String key) {
        if (StringUtils.isBlank(key)) {
            throw new CaException("key不能为空");
        }
        CaConfigExample example = new CaConfigExample();
        CaConfigExample.Criteria criteria = example.createCriteria();
        criteria.andConfigKeyEqualTo(key);
        List<CaConfig> configList = caConfigMapper.selectByExample(example);

        return CollectionUtils.isEmpty(configList) ? null : configList.get(0);
    }

    @Override
    public ConfigModel findModelById(Integer id) {

        CaConfig entity = findById(id);

        return convert2Model(entity);
    }

    @Override
    public ConfigModel findModelByKey(String key) {

        CaConfig entity = findByKey(key);

        return convert2Model(entity);
    }

    @Override
    public List<ConfigModel> listModelByKey(String key) {
        CaConfigExample example = new CaConfigExample();
        CaConfigExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andConfigKeyEqualTo(key);
        }

        List<CaConfig> configList = caConfigMapper.selectByExample(example);

        return convert2ModelList(configList);
    }

    private void checkParam(ConfigRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("请求参数不能为空");
        }
        if (StringUtils.isBlank(request.getKey())) {
            throw new CaException("key不能为空");
        }
        if (StringUtils.isBlank(request.getValue())) {
            throw new CaException("value不能为空");
        }
    }

    private CaConfig convert(ConfigRequest request) {
        CaConfig entity = new CaConfig();
        entity.setId(request.getId());
        entity.setConfigKey(request.getKey());
        entity.setConfigValue(request.getValue());
        entity.setConfigInfo(request.getInfo());
        return entity;
    }

    private List<ConfigModel> convert2ModelList(List<CaConfig> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }

        return entities.stream().filter(Objects::nonNull)
                .map(this::convert2Model).collect(Collectors.toList());
    }

    private ConfigModel convert2Model(CaConfig entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        ConfigModel model = new ConfigModel();
        model.setId(entity.getId());
        model.setKey(entity.getConfigKey());
        model.setValue(entity.getConfigValue());
        model.setInfo(entity.getConfigInfo());
        return model;
    }
}
