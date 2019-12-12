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
     * @param id
     * @return
     */
    AlumniVO queryAlumniDetail(Integer id) throws CaException;

    /**
     * 查询协会待审核记录
     * @param id
     * @return
     */
    List<UserVO> queryAlumniAudit(Integer id);

    /**
     * 审核加入协会申请
     * @param id
     * @param status
     * @return
     */
    Boolean auidtAlumniRecord(Integer id, AlumniAuditStatusEnum status) throws CaException;

    /**
     * 分配管理员
     * @param id
     * @param userId
     * @return
     */
    Boolean appointAdmin(Integer id, Integer userId) throws CaException;
}
