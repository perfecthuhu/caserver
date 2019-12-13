package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.entity.CaAlumniAuditLog;
import com.card.alumni.exception.CaException;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.query.AlumniQuery;

import java.util.List;

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
    PageData<AlumniVO> queryAlumniService(AlumniQuery alumniQuery);

    /**
     * 查询单个协会
     * @param alumniId
     * @return
     */
    AlumniVO queryAlumniDetail(Integer alumniId) throws CaException;

    /**
     * 查询协会待审核记录
     * @param alumniId
     * @return
     */
    List<UserVO> queryAlumniAudit(Integer alumniId);

    /**
     * 审核加入协会申请
     * @param alumniId
     * @param status
     * @return
     */
    Boolean auidtAlumniRecord(Integer alumniId, AlumniAuditStatusEnum status) throws CaException;

    /**
     * 分配管理员
     * @param alumniId
     * @param userId
     * @return
     */
    Boolean appointAdmin(Integer alumniId, Integer userId) throws CaException;

    /**
     * 申请加入协会
     * @param alumniId
     * @return
     * @throws CaException
     */
    Boolean applyAlumni(Integer alumniId) throws CaException;

}
