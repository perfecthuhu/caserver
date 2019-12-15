package com.card.alumni.dao;

import com.card.alumni.entity.CaHomeGuide;
import com.card.alumni.entity.CaHomeGuideExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaHomeGuideMapper {
    int countByExample(CaHomeGuideExample example);

    int deleteByExample(CaHomeGuideExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaHomeGuide record);

    int insertSelective(CaHomeGuide record);

    List<CaHomeGuide> selectByExample(CaHomeGuideExample example);

    CaHomeGuide selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaHomeGuide record, @Param("example") CaHomeGuideExample example);

    int updateByExample(@Param("record") CaHomeGuide record, @Param("example") CaHomeGuideExample example);

    int updateByPrimaryKeySelective(CaHomeGuide record);

    int updateByPrimaryKey(CaHomeGuide record);

    int getMaxRankByHomePageId(Integer homePageId);

    int batchUpdate(List<CaHomeGuide> list);
}