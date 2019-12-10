package com.card.alumni.service;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaException;
import com.card.alumni.vo.query.AlumniQuery;

import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:03 PM
 */
public interface AlumniService {

    /**
     * 查询协会校友会
     * @param alumniQuery
     * @return
     */
    UnifiedResponse queryAlumniService(AlumniQuery alumniQuery);

    /**
     * 查询单个协会
     * @param id
     * @return
     */
    UnifiedResponse queryAlumniDetail(Integer id) throws CaException;

    /**
     * 查询协会待审核记录
     * @param id
     * @return
     */
    UnifiedResponse queryAlumniAudit(Integer id);

    /**
     * 审核加入协会申请
     * @param id
     * @param status
     * @return
     */
    UnifiedResponse auidtAlumniRecord(Integer id, Integer status);
}
