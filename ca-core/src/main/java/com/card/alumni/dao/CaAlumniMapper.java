package com.card.alumni.dao;

import com.card.alumni.entity.CaAlumni;
import com.card.alumni.entity.CaAlumniExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaAlumniMapper {
    int countByExample(CaAlumniExample example);

    int deleteByExample(CaAlumniExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaAlumni record);

    int insertSelective(CaAlumni record);

    List<CaAlumni> selectByExample(CaAlumniExample example);

    CaAlumni selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaAlumni record, @Param("example") CaAlumniExample example);

    int updateByExample(@Param("record") CaAlumni record, @Param("example") CaAlumniExample example);

    int updateByPrimaryKeySelective(CaAlumni record);

    int updateByPrimaryKey(CaAlumni record);
}