package com.card.alumni.dao;

import com.card.alumni.entity.CaProject;
import com.card.alumni.entity.CaProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaProjectMapper {
    int countByExample(CaProjectExample example);

    int deleteByExample(CaProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaProject record);

    int insertSelective(CaProject record);

    List<CaProject> selectByExampleWithBLOBs(CaProjectExample example);

    List<CaProject> selectByExample(CaProjectExample example);

    CaProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaProject record, @Param("example") CaProjectExample example);

    int updateByExampleWithBLOBs(@Param("record") CaProject record, @Param("example") CaProjectExample example);

    int updateByExample(@Param("record") CaProject record, @Param("example") CaProjectExample example);

    int updateByPrimaryKeySelective(CaProject record);

    int updateByPrimaryKeyWithBLOBs(CaProject record);

    int updateByPrimaryKey(CaProject record);
}