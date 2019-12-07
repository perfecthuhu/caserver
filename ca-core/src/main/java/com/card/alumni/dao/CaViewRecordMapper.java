package com.card.alumni.dao;

import com.card.alumni.entity.CaViewRecord;
import com.card.alumni.entity.CaViewRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaViewRecordMapper {
    int countByExample(CaViewRecordExample example);

    int deleteByExample(CaViewRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaViewRecord record);

    int insertSelective(CaViewRecord record);

    List<CaViewRecord> selectByExample(CaViewRecordExample example);

    CaViewRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaViewRecord record, @Param("example") CaViewRecordExample example);

    int updateByExample(@Param("record") CaViewRecord record, @Param("example") CaViewRecordExample example);

    int updateByPrimaryKeySelective(CaViewRecord record);

    int updateByPrimaryKey(CaViewRecord record);
}