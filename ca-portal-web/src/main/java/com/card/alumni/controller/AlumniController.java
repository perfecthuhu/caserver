package com.card.alumni.controller;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.exception.CaException;
import com.card.alumni.request.UserSchoolAlumniRequest;
import com.card.alumni.service.AlumniService;
import com.card.alumni.service.UserService;
import com.card.alumni.utils.RequestUtil;
import com.card.alumni.vo.AlumniVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.enums.AlumniAuditStatusEnum;
import com.card.alumni.vo.query.AlumniQuery;
import com.google.api.client.util.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private UserService userService;

    /**
     * 查询协会校友会
     *
     * @param alumniQuery
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "查询协会校友会", notes = "查询协会校友会")
    public UnifiedResult<PageData<AlumniVO>> queryAlumniService(@RequestBody AlumniQuery alumniQuery) {
        return UnifiedResult.success(alumniService.queryAlumniService(alumniQuery));
    }

    /**
     * 查询单个协会
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "查询单个协会", notes = "查询单个协会")
    public UnifiedResult<AlumniVO> queryAlumniDetail(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.queryAlumniDetail(id));
    }

    /**
     * 退出协会
     *
     * @param id
     * @return
     */
    @GetMapping("/audit/exit/{id}")
    @ApiOperation(value = "退出协会", notes = "退出协会")
    public UnifiedResult<Boolean> exitAlumn(@PathVariable Integer id) throws Exception {
        return UnifiedResult.success(alumniService.auidtAlumniRecord(id, AlumniAuditStatusEnum.EXIT));
    }

    /**
     * 申请加入协会
     *
     * @param alumniId
     * @return
     * @throws CaException
     */
    @PostMapping("/apply/{alumniId}")
    @ApiOperation(value = "申请加入协会", notes = "申请加入协会")
    public UnifiedResult<Boolean> applyAlumni(@PathVariable Integer alumniId) throws Exception {
        return UnifiedResult.success(alumniService.applyAlumni(alumniId));
    }

    @GetMapping("/my")
    @ApiOperation(value = "我加入的协会", notes = "我加入的协会")
    public UnifiedResult<List<AlumniVO>> queryMyAlumni() {
        return UnifiedResult.success(alumniService.queryMyAlumni(RequestUtil.getUserId()));
    }

    @PostMapping("/school")
    @ApiOperation(value = "查询学校组织校友会成员接口", notes = "查询学校组织校友会成员接口")
    public UnifiedResult<PageData<UserVO>> querySchoolAlumni(@RequestBody UserSchoolAlumniRequest request) {
        if (request.getPage() != 1){
            return UnifiedResult.success(new PageData(0, Lists.newArrayList()));
        }
        List<UserVO> userVOS = userService.querySchoolAlumni(request);
        return UnifiedResult.success(new PageData(userVOS.size(), userVOS));
    }

}
