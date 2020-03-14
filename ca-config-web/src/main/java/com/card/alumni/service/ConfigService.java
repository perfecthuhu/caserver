package com.card.alumni.service;

import com.card.alumni.entity.CaConfig;
import com.card.alumni.model.ConfigModel;
import com.card.alumni.request.ConfigRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2020-03-14 2:00 PM
 */
public interface ConfigService {

    Integer save(ConfigRequest request);

    Integer update(ConfigRequest request);

    void deleteById(Integer id);

    CaConfig findById(Integer id);

    CaConfig findByKey(String key);

    ConfigModel findModelById(Integer id);

    ConfigModel findModelByKey(String key);

    List<ConfigModel> listModelByKey(String key);
}
