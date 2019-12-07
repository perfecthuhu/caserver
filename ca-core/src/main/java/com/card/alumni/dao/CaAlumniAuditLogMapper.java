package com.card.alumni.dao;

import com.card.alumni.entity.CaAlumniAuditLog;
import com.card.alumni.entity.CaAlumniAuditLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CaAlumniAuditLogMapper {
    int countByExample(CaAlumniAuditLogExample example);

    int deleteByExample(CaAlumniAuditLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CaAlumniAuditLog record);

    int insertSelective(CaAlumniAuditLog record);

    List<CaAlumniAuditLog> selectByExample(CaAlumniAuditLogExample example);

    CaAlumniAuditLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CaAlumniAuditLog record, @Param("example") CaAlumniAuditLogExample example);

    int updateByExample(@Param("record") CaAlumniAuditLog record, @Param("example") CaAlumniAuditLogExample example);

    int updateByPrimaryKeySelective(CaAlumniAuditLog record);

    int updateByPrimaryKey(CaAlumniAuditLog record);
}