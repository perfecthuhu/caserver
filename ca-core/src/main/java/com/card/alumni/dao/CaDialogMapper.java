package com.card.alumni.dao;

import com.card.alumni.entity.CaDialog;
import com.card.alumni.entity.CaDialogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaDialogMapper {
    int countByExample(CaDialogExample example);

    int deleteByExample(CaDialogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaDialog record);

    int insertSelective(CaDialog record);

    List<CaDialog> selectByExample(CaDialogExample example);

    CaDialog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaDialog record, @Param("example") CaDialogExample example);

    int updateByExample(@Param("record") CaDialog record, @Param("example") CaDialogExample example);

    int updateByPrimaryKeySelective(CaDialog record);

    int updateByPrimaryKey(CaDialog record);
}