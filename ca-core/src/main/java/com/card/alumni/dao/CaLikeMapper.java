package com.card.alumni.dao;

import com.card.alumni.entity.CaLike;
import com.card.alumni.entity.CaLikeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaLikeMapper {
    int countByExample(CaLikeExample example);

    int deleteByExample(CaLikeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CaLike record);

    int insertSelective(CaLike record);

    List<CaLike> selectByExample(CaLikeExample example);

    CaLike selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CaLike record, @Param("example") CaLikeExample example);

    int updateByExample(@Param("record") CaLike record, @Param("example") CaLikeExample example);

    int updateByPrimaryKeySelective(CaLike record);

    int updateByPrimaryKey(CaLike record);
}