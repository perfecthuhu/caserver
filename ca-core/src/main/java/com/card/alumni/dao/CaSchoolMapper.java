package com.card.alumni.dao;

import com.card.alumni.entity.CaSchool;
import com.card.alumni.entity.CaSchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaSchoolMapper {
    int countByExample(CaSchoolExample example);

    int deleteByExample(CaSchoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaSchool record);

    int insertSelective(CaSchool record);

    List<CaSchool> selectByExample(CaSchoolExample example);

    CaSchool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaSchool record, @Param("example") CaSchoolExample example);

    int updateByExample(@Param("record") CaSchool record, @Param("example") CaSchoolExample example);

    int updateByPrimaryKeySelective(CaSchool record);

    int updateByPrimaryKey(CaSchool record);
}