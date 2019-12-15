package com.card.alumni.dao;

import com.card.alumni.entity.CaUserFriend;
import com.card.alumni.entity.CaUserFriendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaUserFriendMapper {
    int countByExample(CaUserFriendExample example);

    int deleteByExample(CaUserFriendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaUserFriend record);

    int insertSelective(CaUserFriend record);

    List<CaUserFriend> selectByExample(CaUserFriendExample example);

    CaUserFriend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaUserFriend record, @Param("example") CaUserFriendExample example);

    int updateByExample(@Param("record") CaUserFriend record, @Param("example") CaUserFriendExample example);

    int updateByPrimaryKeySelective(CaUserFriend record);

    int updateByPrimaryKey(CaUserFriend record);
}