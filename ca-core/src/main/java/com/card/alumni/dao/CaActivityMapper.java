package com.card.alumni.dao;

import com.card.alumni.entity.CaActivity;
import com.card.alumni.entity.CaActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaActivityMapper {
    int countByExample(CaActivityExample example);

    int deleteByExample(CaActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaActivity record);

    int insertSelective(CaActivity record);

    List<CaActivity> selectByExampleWithBLOBs(CaActivityExample example);

    List<CaActivity> selectByExample(CaActivityExample example);

    CaActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaActivity record, @Param("example") CaActivityExample example);

    int updateByExampleWithBLOBs(@Param("record") CaActivity record, @Param("example") CaActivityExample example);

    int updateByExample(@Param("record") CaActivity record, @Param("example") CaActivityExample example);

    int updateByPrimaryKeySelective(CaActivity record);

    int updateByPrimaryKeyWithBLOBs(CaActivity record);

    int updateByPrimaryKey(CaActivity record);
}