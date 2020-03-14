package com.card.alumni.service;

import com.card.alumni.entity.CaConfig;
import com.card.alumni.model.ConfigModel;

import java.util.List;

/**
 * @author liumingyu
 * @date 2020-03-14 2:00 PM
 */
public interface ConfigService {

    CaConfig findById(Integer id);

    CaConfig findByKey(String key);

    ConfigModel findModelById(Integer id);

    ConfigModel findModelByKey(String key);

    List<ConfigModel> listModelByKey(String key);
}
