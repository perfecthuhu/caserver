package com.card.alumni.dao;

import com.card.alumni.entity.CaUserRoleRelation;
import com.card.alumni.entity.CaUserRoleRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaUserRoleRelationMapper {
    int countByExample(CaUserRoleRelationExample example);

    int deleteByExample(CaUserRoleRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaUserRoleRelation record);

    int insertSelective(CaUserRoleRelation record);

    List<CaUserRoleRelation> selectByExample(CaUserRoleRelationExample example);

    CaUserRoleRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaUserRoleRelation record, @Param("example") CaUserRoleRelationExample example);

    int updateByExample(@Param("record") CaUserRoleRelation record, @Param("example") CaUserRoleRelationExample example);

    int updateByPrimaryKeySelective(CaUserRoleRelation record);

    int updateByPrimaryKey(CaUserRoleRelation record);

    int batchInsert(@Param("list") List<CaUserRoleRelation> list);

    int batchUpdate(List<CaUserRoleRelation> list);
}