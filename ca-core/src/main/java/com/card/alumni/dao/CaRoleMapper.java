package com.card.alumni.dao;

import com.card.alumni.entity.CaRole;
import com.card.alumni.entity.CaRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaRoleMapper {
    int countByExample(CaRoleExample example);

    int deleteByExample(CaRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaRole record);

    int insertSelective(CaRole record);

    List<CaRole> selectByExample(CaRoleExample example);

    CaRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaRole record, @Param("example") CaRoleExample example);

    int updateByExample(@Param("record") CaRole record, @Param("example") CaRoleExample example);

    int updateByPrimaryKeySelective(CaRole record);

    int updateByPrimaryKey(CaRole record);
}