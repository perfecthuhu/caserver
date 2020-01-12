package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.AlumniModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.model.enums.AlumniAuditStatusEnum;
import com.card.alumni.request.AlumniRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2019-12-29 11:32 AM
 */
public interface AlumniService {

    /**
     * 查询协会校友会
     * @param alumniRequest
     * @return
     */
    PageData<AlumniModel> queryAlumniService(AlumniRequest alumniRequest);

    /**
     * 查询单个协会
     * @param alumniId
     * @return
     */
    AlumniModel queryAlumniDetail(Integer alumniId) throws CaException, CaConfigException;

    /**
     * 查询协会待审核记录
     * @param alumniId
     * @return
     */
    List<UserModel> queryAlumniAudit(Integer alumniId) throws CaConfigException;

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
     * 创建协会
     * @param alumniModel
     * @return
     * @throws CaException
     */
    Boolean createAlumni(AlumniModel alumniModel) throws CaException;

    /**
     * 修改协会信息
     * @param alumniModel
     * @return
     * @throws CaException
     */
    Boolean updateAlimni(AlumniModel alumniModel) throws CaException;
}
