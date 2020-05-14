package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.AlumniAuditModel;
import com.card.alumni.model.AlumniModel;
import com.card.alumni.model.BatchCheckModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.model.enums.AlumniAuditStatusEnum;
import com.card.alumni.request.AlumniAppointAdminRequest;
import com.card.alumni.request.AlumniRequest;
import com.card.alumni.request.common.BaseQueryRequest;
import com.card.alumni.service.AlumniService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunxiaodong10 2020/1/12
 * @date 11:52 AM
 */
@RestController
@Api(value = "组织模块", tags = "组织模块")
@RequestMapping("/api/alumni")
public class AlumniController {

    @Resource
    private AlumniService alumniService;

    /**
     * 查询协会校友会
     * @param alumniRequest
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "查询协会校友会", notes = "查询协会校友会")
    public UnifiedResult<PageData<AlumniModel>> queryAlumniService(@RequestBody AlumniRequest alumniRequest) {
        return UnifiedResult.success(alumniService.queryAlumniService(alumniRequest));
    }

    /**
     * 查询单个协会
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "查询单个协会", notes = "查询单个协会")
    public UnifiedResult<AlumniModel> queryAlumniDetail(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.queryAlumniDetail(id));
    }


    /**
     * 查询协会待审核记录
     * @param alumniId
     * @return
     */
    @GetMapping("/audit/list/{alumniId}")
    @ApiOperation(value = "查询协会待审核记录", notes = "查询协会待审核记录")
    public UnifiedResult<List<UserModel>> queryAlumniAudit(@PathVariable Integer alumniId) throws CaConfigException {
        return UnifiedResult.success(alumniService.queryAlumniAudit(alumniId));
    }


    /**
     * 驳回加入协会申请
     * @param id
     * @return
     */
    @GetMapping("/audit/exit/{id}")
    @ApiOperation(value = "驳回加入协会申请（id为待审核记录ID）", notes = "驳回加入协会申请（id为待审核记录ID）")
    public UnifiedResult<Boolean> rejectAlumni(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.REJECT));
    }

    /**
     * 将用户踢出协会
     * @param id
     * @return
     */
    @GetMapping("/audit/exit/{alumniId}/{id}")
    @ApiOperation(value = "将用户踢出协会（第一个为协会ID，第二个为用户ID）", notes = "将用户踢出协会（id为待审核记录ID）")
    public UnifiedResult<Boolean> exitAlumni(@PathVariable Integer alumniId, @PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.exitAlumni(alumniId, id, AlumniAuditStatusEnum.EXIT));
    }

    /**
     * 审核通过加入协会
     * @param id
     * @return
     */
    @GetMapping("/audit/pass/{id}")
    @ApiOperation(value = "审核通过加入协会（id为待审核记录ID）", notes = "审核通过加入协会")
    public UnifiedResult<Boolean> passAlumni(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.PASS));
    }

    /**
     * 审核通过加入协会
     * @param batchCheckModel
     * @return
     */
    @PostMapping("/audit/batch")
    @ApiOperation(value = "批量审核或驳回", notes = "批量审核或驳回")
    public UnifiedResult<Boolean> batchCheck(BatchCheckModel batchCheckModel) throws Exception {
        return UnifiedResult.success(alumniService.batchCheck(batchCheckModel));
    }

    /**
     * 分配管理员
     * @param alumniAppointAdminRequest
     * @return
     */
    @PostMapping("/appoint/admin")
    @ApiOperation(value = "分配管理员", notes = "分配管理员")
    public UnifiedResult<Boolean> appointAdmin(@RequestBody AlumniAppointAdminRequest alumniAppointAdminRequest) throws Exception {
        return UnifiedResult.success(alumniService.appointAdmin(alumniAppointAdminRequest.getAlumniId(), alumniAppointAdminRequest.getUserId()));
    }

    /**
     * 创建协会
     * @param alumniModel
     * @return
     * @throws CaException
     */
    @PostMapping("/create")
    @ApiOperation(value = "申请创建协会", notes = "申请创建协会")
    public UnifiedResult<Boolean> createAlumni(@RequestBody AlumniModel alumniModel) throws CaException {
        return UnifiedResult.success(alumniService.createAlumni(alumniModel));
    }

    /**
     * 修改协会信息
     * @param alumniModel
     * @return
     * @throws CaException
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新协会信息", notes = "更新协会信息")
    public UnifiedResult<Boolean> updateAlimni(@RequestBody AlumniModel alumniModel) throws CaException {
        return UnifiedResult.success(alumniService.updateAlimni(alumniModel));
    }

    @PostMapping("/query/audit/all")
    @ApiOperation(value = "查询全部协会待审核信息", notes = "查询全部协会待审核信息")
    public UnifiedResult<PageData<AlumniAuditModel>> queryAllAlumniAudit(@RequestBody AlumniRequest alumniRequest) {
        return UnifiedResult.success(alumniService.queryAllAlumniAudit(alumniRequest));
    }

    @PostMapping("/delete/{alumniId}")
    @ApiOperation(value = "删除协会", notes = "删除协会")
    public UnifiedResult<Boolean> deleteAlumni(@PathVariable Integer alumniId) {
        return UnifiedResult.success(alumniService.deleteAlumni(alumniId));
    }
}
