package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaConfigException;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.AlumniModel;
import com.card.alumni.model.UserModel;
import com.card.alumni.model.enums.AlumniAuditStatusEnum;
import com.card.alumni.request.AlumniAppointAdminRequest;
import com.card.alumni.request.AlumniRequest;
import com.card.alumni.request.common.BaseQueryRequest;
import com.card.alumni.service.AlumniService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
     * 退出协会
     * @param id
     * @return
     */
    @GetMapping("/audit/exit/{id}")
    @ApiOperation(value = "退出协会", notes = "退出协会")
    public UnifiedResult<Boolean> exitAlumn(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.EXIT));
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
    @ApiOperation(value = "查询全部协会信息", notes = "查询全部协会信息")
    public UnifiedResult<PageData<UserModel>> queryAllAlumniAudit(@RequestBody AlumniRequest alumniRequest) {
        return UnifiedResult.success(alumniService.queryAllAlumniAudit(alumniRequest));
    }
}
