package com.card.alumni.dao;

import com.card.alumni.entity.CaUserAlumniRelation;
import com.card.alumni.entity.CaUserAlumniRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaUserAlumniRelationMapper {
    int countByExample(CaUserAlumniRelationExample example);

    int deleteByExample(CaUserAlumniRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaUserAlumniRelation record);

    int insertSelective(CaUserAlumniRelation record);

    List<CaUserAlumniRelation> selectByExample(CaUserAlumniRelationExample example);

    CaUserAlumniRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaUserAlumniRelation record, @Param("example") CaUserAlumniRelationExample example);

    int updateByExample(@Param("record") CaUserAlumniRelation record, @Param("example") CaUserAlumniRelationExample example);

    int updateByPrimaryKeySelective(CaUserAlumniRelation record);

    int updateByPrimaryKey(CaUserAlumniRelation record);
}