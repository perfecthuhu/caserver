package com.card.alumni.dao;

import com.card.alumni.entity.CaHomeBanner;
import com.card.alumni.entity.CaHomeBannerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaHomeBannerMapper {
    int countByExample(CaHomeBannerExample example);

    int deleteByExample(CaHomeBannerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CaHomeBanner record);

    int insertSelective(CaHomeBanner record);

    List<CaHomeBanner> selectByExample(CaHomeBannerExample example);

    CaHomeBanner selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CaHomeBanner record, @Param("example") CaHomeBannerExample example);

    int updateByExample(@Param("record") CaHomeBanner record, @Param("example") CaHomeBannerExample example);

    int updateByPrimaryKeySelective(CaHomeBanner record);

    int updateByPrimaryKey(CaHomeBanner record);
}