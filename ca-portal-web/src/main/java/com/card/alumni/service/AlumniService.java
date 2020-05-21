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
import java.util.Map;

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
     * 申请加入协会
     * @param alumniId
     * @return
     * @throws CaException
     */
    Boolean applyAlumni(Integer alumniId) throws CaException;

    /**
     * 退出协会
     * @param id
     * @param statusEnum
     * @return
     * @throws CaException
     */
    Boolean auidtAlumniRecord(Integer id, AlumniAuditStatusEnum statusEnum) throws CaException;

    /**
     * 根据用户ID查询协会
     * @param userId
     * @return
     */
    List<AlumniVO> queryMyAlumni(Integer userId);

}
