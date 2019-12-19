package com.card.alumni.dao;

import com.card.alumni.entity.CaUser;
import com.card.alumni.entity.CaUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaUserMapper {
    int countByExample(CaUserExample example);

    int deleteByExample(CaUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaUser record);

    int insertSelective(CaUser record);

    List<CaUser> selectByExample(CaUserExample example);

    CaUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaUser record, @Param("example") CaUserExample example);

    int updateByExample(@Param("record") CaUser record, @Param("example") CaUserExample example);

    int updateByPrimaryKeySelective(CaUser record);

    int updateByPrimaryKey(CaUser record);

    CaUser selectByPhone(String phone);
}