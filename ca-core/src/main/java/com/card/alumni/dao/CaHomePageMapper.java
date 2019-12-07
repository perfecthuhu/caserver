package com.card.alumni.dao;

import com.card.alumni.entity.CaHomePage;
import com.card.alumni.entity.CaHomePageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaHomePageMapper {
    int countByExample(CaHomePageExample example);

    int deleteByExample(CaHomePageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaHomePage record);

    int insertSelective(CaHomePage record);

    List<CaHomePage> selectByExample(CaHomePageExample example);

    CaHomePage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaHomePage record, @Param("example") CaHomePageExample example);

    int updateByExample(@Param("record") CaHomePage record, @Param("example") CaHomePageExample example);

    int updateByPrimaryKeySelective(CaHomePage record);

    int updateByPrimaryKey(CaHomePage record);
}