package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.service.AlumniService;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.query.AlumniQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:30 PM
 */
@RestController
@RequestMapping("/alumni")
public class AlumniController {

    @Resource
    private AlumniService alumniService;

    /**
     * 查询协会校友会
     * @param alumniQuery
     * @return
     */
    @RequestMapping("/page")
    public UnifiedResponse queryAlumniService(AlumniQuery alumniQuery) {
        return new UnifiedResponse(alumniService.queryAlumniService(alumniQuery));
    }

    /**
     * 查询单个协会
     * @param id
     * @return
     */
    public UnifiedResponse queryAlumniDetail(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.queryAlumniDetail(id));
    }

    /**
     * 查询协会待审核记录
     * @param id
     * @return
     */
    public UnifiedResponse queryAlumniAudit(Integer id) {
        return new UnifiedResponse(alumniService.queryAlumniAudit(id));
    }

    /**
     * 审核通过加入协会申请
     * @param id
     * @return
     */
    public UnifiedResponse auidtAlumniRecordPass(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.PASS));
    }

    /**
     * 审核通过驳回协会申请
     * @param id
     * @return
     */
    public UnifiedResponse auidtAlumniRecordReject(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.REJECT));
    }

    /**
     * 分配管理员
     * @param id
     * @param userId
     * @return
     */
    public UnifiedResponse appointAdmin(Integer id, Integer userId) throws Exception {
        return new UnifiedResponse(alumniService.appointAdmin(id, userId));
    }
}
