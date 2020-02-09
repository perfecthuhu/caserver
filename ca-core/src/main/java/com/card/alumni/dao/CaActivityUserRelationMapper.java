package com.card.alumni.dao;

import com.card.alumni.entity.CaActivityUserRelation;
import com.card.alumni.entity.CaActivityUserRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaActivityUserRelationMapper {
    int countByExample(CaActivityUserRelationExample example);

    int deleteByExample(CaActivityUserRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaActivityUserRelation record);

    int insertSelective(CaActivityUserRelation record);

    List<CaActivityUserRelation> selectByExample(CaActivityUserRelationExample example);

    CaActivityUserRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaActivityUserRelation record, @Param("example") CaActivityUserRelationExample example);

    int updateByExample(@Param("record") CaActivityUserRelation record, @Param("example") CaActivityUserRelationExample example);

    int updateByPrimaryKeySelective(CaActivityUserRelation record);

    int updateByPrimaryKey(CaActivityUserRelation record);
}