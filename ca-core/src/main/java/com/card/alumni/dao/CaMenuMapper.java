package com.card.alumni.dao;

import com.card.alumni.entity.CaMenu;
import com.card.alumni.entity.CaMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CaMenuMapper {
    int countByExample(CaMenuExample example);

    int deleteByExample(CaMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaMenu record);

    int insertSelective(CaMenu record);

    List<CaMenu> selectByExample(CaMenuExample example);

    CaMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaMenu record, @Param("example") CaMenuExample example);

    int updateByExample(@Param("record") CaMenu record, @Param("example") CaMenuExample example);

    int updateByPrimaryKeySelective(CaMenu record);

    int updateByPrimaryKey(CaMenu record);

    int getMaxRankByParentId(Integer pid);

    int batchUpdate(List<CaMenu> list);
}