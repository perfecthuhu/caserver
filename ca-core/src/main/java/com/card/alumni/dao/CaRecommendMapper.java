package com.card.alumni.dao;

import com.card.alumni.entity.CaRecommend;
import com.card.alumni.entity.CaRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaRecommendMapper {
    int countByExample(CaRecommendExample example);

    int deleteByExample(CaRecommendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaRecommend record);

    int insertSelective(CaRecommend record);

    List<CaRecommend> selectByExample(CaRecommendExample example);

    CaRecommend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaRecommend record, @Param("example") CaRecommendExample example);

    int updateByExample(@Param("record") CaRecommend record, @Param("example") CaRecommendExample example);

    int updateByPrimaryKeySelective(CaRecommend record);

    int updateByPrimaryKey(CaRecommend record);
}