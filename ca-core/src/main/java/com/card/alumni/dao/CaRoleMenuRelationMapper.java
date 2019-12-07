package com.card.alumni.dao;

import com.card.alumni.entity.CaRoleMenuRelation;
import com.card.alumni.entity.CaRoleMenuRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaRoleMenuRelationMapper {
    int countByExample(CaRoleMenuRelationExample example);

    int deleteByExample(CaRoleMenuRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaRoleMenuRelation record);

    int insertSelective(CaRoleMenuRelation record);

    List<CaRoleMenuRelation> selectByExample(CaRoleMenuRelationExample example);

    CaRoleMenuRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaRoleMenuRelation record, @Param("example") CaRoleMenuRelationExample example);

    int updateByExample(@Param("record") CaRoleMenuRelation record, @Param("example") CaRoleMenuRelationExample example);

    int updateByPrimaryKeySelective(CaRoleMenuRelation record);

    int updateByPrimaryKey(CaRoleMenuRelation record);
}