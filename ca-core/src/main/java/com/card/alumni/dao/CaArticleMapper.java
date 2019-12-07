package com.card.alumni.dao;

import com.card.alumni.entity.CaArticle;
import com.card.alumni.entity.CaArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaArticleMapper {
    int countByExample(CaArticleExample example);

    int deleteByExample(CaArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaArticle record);

    int insertSelective(CaArticle record);

    List<CaArticle> selectByExampleWithBLOBs(CaArticleExample example);

    List<CaArticle> selectByExample(CaArticleExample example);

    CaArticle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaArticle record, @Param("example") CaArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") CaArticle record, @Param("example") CaArticleExample example);

    int updateByExample(@Param("record") CaArticle record, @Param("example") CaArticleExample example);

    int updateByPrimaryKeySelective(CaArticle record);

    int updateByPrimaryKeyWithBLOBs(CaArticle record);

    int updateByPrimaryKey(CaArticle record);
}