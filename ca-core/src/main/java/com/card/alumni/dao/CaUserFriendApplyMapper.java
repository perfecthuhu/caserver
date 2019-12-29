package com.card.alumni.dao;

import com.card.alumni.entity.CaUserFriendApply;
import com.card.alumni.entity.CaUserFriendApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaUserFriendApplyMapper {
    int countByExample(CaUserFriendApplyExample example);

    int deleteByExample(CaUserFriendApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaUserFriendApply record);

    int insertSelective(CaUserFriendApply record);

    List<CaUserFriendApply> selectByExample(CaUserFriendApplyExample example);

    CaUserFriendApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaUserFriendApply record, @Param("example") CaUserFriendApplyExample example);

    int updateByExample(@Param("record") CaUserFriendApply record, @Param("example") CaUserFriendApplyExample example);

    int updateByPrimaryKeySelective(CaUserFriendApply record);

    int updateByPrimaryKey(CaUserFriendApply record);
}