package com.card.alumni.controller;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.AlumniService;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.query.AlumniAppointAdminParam;
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
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "查询单个协会", notes = "查询单个协会", response = UnifiedResponse.class)
    public UnifiedResponse queryAlumniDetail(@PathVariable Integer id) throws Exception {
        return new UnifiedResponse(alumniService.queryAlumniDetail(id));
    }

    /**
     * 查询协会待审核记录
     * @param alumniId
     * @return
     */
    @GetMapping("/audit/list/{alumniId}")
    @ApiOperation(value = "查询协会待审核记录", notes = "查询协会待审核记录", response = UnifiedResponse.class)
    public UnifiedResponse queryAlumniAudit(@PathVariable Integer alumniId) {
        return new UnifiedResponse(alumniService.queryAlumniAudit(alumniId));
    }

    /**
     * 审核通过加入协会申请
     * @param id
     * @return
     */
    @GetMapping("/audit/pass/{id}")
    @ApiOperation(value = "审核通过加入协会申请", notes = "审核通过加入协会申请", response = UnifiedResponse.class)
    public UnifiedResponse auidtAlumniRecordPass(@PathVariable Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.PASS));
    }

    /**
     * 审核通过驳回协会申请
     * @param id
     * @return
     */
    @GetMapping("/audit/reject/{id}")
    @ApiOperation(value = "审核通过驳回协会申请", notes = "审核通过驳回协会申请", response = UnifiedResponse.class)
    public UnifiedResponse auidtAlumniRecordReject(@PathVariable Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.REJECT));
    }

    /**
     * 退出协会
     * @param id
     * @return
     */
    @GetMapping("/audit/exit/{id}")
    @ApiOperation(value = "退出协会", notes = "退出协会", response = UnifiedResponse.class)
    public UnifiedResponse exitAlumn(@PathVariable Integer id) throws Exception {
        return new UnifiedResponse(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.EXIT));
    }

    /**
     * 分配管理员
     * @param alumniAppointAdminParam
     * @return
     */
    @PostMapping("/appoint/admin")
    @ApiOperation(value = "分配管理员", notes = "分配管理员", response = UnifiedResponse.class)
    public UnifiedResponse appointAdmin(@RequestBody AlumniAppointAdminParam alumniAppointAdminParam) throws Exception {
        return new UnifiedResponse(alumniService.appointAdmin(alumniAppointAdminParam.getAlumniId(), alumniAppointAdminParam.getUserId()));
    }

    /**
     * 申请加入协会
     * @param alumniId
     * @return
     * @throws CaException
     */
    @PostMapping("/apply/{id}")
    @ApiOperation(value = "申请加入协会", notes = "申请加入协会", response = UnifiedResponse.class)
    public UnifiedResponse applyAlumni(@PathVariable Integer alumniId) throws Exception {
        return new UnifiedResponse(alumniService.applyAlumni(alumniId));
    }

    /**
     * 创建协会
     * @param alumniVO
     * @return
     * @throws CaException
     */
    @PostMapping("/create")
    @ApiOperation(value = "申请创建协会", notes = "申请创建协会", response = UnifiedResponse.class)
    UnifiedResponse createAlumni(@RequestBody AlumniVO alumniVO) throws CaException {
        return new UnifiedResponse(alumniService.createAlumni(alumniVO));
    }

    /**
     * 修改协会信息
     * @param alumniVO
     * @return
     * @throws CaException
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新协会信息", notes = "更新协会信息", response = UnifiedResponse.class)
    UnifiedResponse updateAlimni(@RequestBody AlumniVO alumniVO) throws CaException {
        return new UnifiedResponse(alumniService.updateAlimni(alumniVO));
    }
}
