package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.AlumniService;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.query.AlumniQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:30 PM
 */
@RestController
@RequestMapping("/alumni")
@Api(value = "群组模块", tags = "群组模块")
public class AlumniController {

    @Resource
    private AlumniService alumniService;

    /**
     * 查询协会校友会
     * @param alumniQuery
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "查询协会校友会", notes = "查询协会校友会", response = UnifiedResponse.class)
    public UnifiedResponse queryAlumniService(@RequestBody AlumniQuery alumniQuery) {
        return new UnifiedResponse(alumniService.queryAlumniService(alumniQuery));
    }

    /**
     * 查询单个协会
     * @param id
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation(value = "查询单个协会", notes = "查询单个协会", response = UnifiedResponse.class)
    public UnifiedResponse queryAlumniDetail(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.queryAlumniDetail(id));
    }

    /**
     * 查询协会待审核记录
     * @param id
     * @return
     */
    @GetMapping("/audit/list")
    @ApiOperation(value = "查询协会待审核记录", notes = "查询协会待审核记录", response = UnifiedResponse.class)
    public UnifiedResponse queryAlumniAudit(Integer id) {
        return new UnifiedResponse(alumniService.queryAlumniAudit(id));
    }

    /**
     * 审核通过加入协会申请
     * @param id
     * @return
     */
    @PostMapping("/audit/pass")
    @ApiOperation(value = "审核通过加入协会申请", notes = "审核通过加入协会申请", response = UnifiedResponse.class)
    public UnifiedResponse auidtAlumniRecordPass(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.PASS));
    }

    /**
     * 审核通过驳回协会申请
     * @param id
     * @return
     */
    @PostMapping("/audit/reject")
    @ApiOperation(value = "审核通过驳回协会申请", notes = "审核通过驳回协会申请", response = UnifiedResponse.class)
    public UnifiedResponse auidtAlumniRecordReject(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.REJECT));
    }

    /**
     * 退出协会
     * @param id
     * @return
     */
    @PostMapping("/audit/exit")
    @ApiOperation(value = "退出协会", notes = "退出协会", response = UnifiedResponse.class)
    public UnifiedResponse exitAlumn(Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.EXIT));
    }

    /**
     * 分配管理员
     * @param alumniId
     * @param userId
     * @return
     */
    @PostMapping("/appoint/admin")
    @ApiOperation(value = "分配管理员", notes = "分配管理员", response = UnifiedResponse.class)
    public UnifiedResponse appointAdmin(Integer alumniId, Integer userId) throws Exception {
        return new UnifiedResponse(alumniService.appointAdmin(alumniId, userId));
    }

    /**
     * 申请加入协会
     * @param alumniId
     * @return
     * @throws CaException
     */
    @PostMapping("/apply")
    @ApiOperation(value = "申请加入协会", notes = "申请加入协会", response = UnifiedResponse.class)
    public UnifiedResponse applyAlumni(Integer alumniId) throws Exception {
        return new UnifiedResponse(alumniService.applyAlumni(alumniId));
    }
}
