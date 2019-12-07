package com.card.alumni.dao;

import com.card.alumni.entity.CaAlumniRole;
import com.card.alumni.entity.CaAlumniRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaAlumniRoleMapper {
    int countByExample(CaAlumniRoleExample example);

    int deleteByExample(CaAlumniRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaAlumniRole record);

    int insertSelective(CaAlumniRole record);

    List<CaAlumniRole> selectByExample(CaAlumniRoleExample example);

    CaAlumniRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaAlumniRole record, @Param("example") CaAlumniRoleExample example);

    int updateByExample(@Param("record") CaAlumniRole record, @Param("example") CaAlumniRoleExample example);

    int updateByPrimaryKeySelective(CaAlumniRole record);

    int updateByPrimaryKey(CaAlumniRole record);
}