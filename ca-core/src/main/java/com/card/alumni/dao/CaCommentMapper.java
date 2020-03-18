package com.card.alumni.dao;

import com.card.alumni.entity.CaComment;
import com.card.alumni.entity.CaCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaCommentMapper {
    int countByExample(CaCommentExample example);

    int deleteByExample(CaCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CaComment record);

    int insertSelective(CaComment record);

    List<CaComment> selectByExample(CaCommentExample example);

    CaComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CaComment record, @Param("example") CaCommentExample example);

    int updateByExample(@Param("record") CaComment record, @Param("example") CaCommentExample example);

    int updateByPrimaryKeySelective(CaComment record);

    int updateByPrimaryKey(CaComment record);
}