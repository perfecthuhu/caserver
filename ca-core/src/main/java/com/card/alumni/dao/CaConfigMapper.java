package com.card.alumni.dao;

import com.card.alumni.entity.CaConfig;
import com.card.alumni.entity.CaConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaConfigMapper {
    int countByExample(CaConfigExample example);

    int deleteByExample(CaConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaConfig record);

    int insertSelective(CaConfig record);

    List<CaConfig> selectByExample(CaConfigExample example);

    CaConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaConfig record, @Param("example") CaConfigExample example);

    int updateByExample(@Param("record") CaConfig record, @Param("example") CaConfigExample example);

    int updateByPrimaryKeySelective(CaConfig record);

    int updateByPrimaryKey(CaConfig record);
}