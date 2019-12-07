package com.card.alumni.dao;

import com.card.alumni.entity.CaUserTag;
import com.card.alumni.entity.CaUserTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaUserTagMapper {
    int countByExample(CaUserTagExample example);

    int deleteByExample(CaUserTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaUserTag record);

    int insertSelective(CaUserTag record);

    List<CaUserTag> selectByExample(CaUserTagExample example);

    CaUserTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaUserTag record, @Param("example") CaUserTagExample example);

    int updateByExample(@Param("record") CaUserTag record, @Param("example") CaUserTagExample example);

    int updateByPrimaryKeySelective(CaUserTag record);

    int updateByPrimaryKey(CaUserTag record);
}