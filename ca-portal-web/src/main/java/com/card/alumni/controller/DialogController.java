package com.card.alumni.controller;

import com.alibaba.fastjson.JSON;
import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResult;
import com.card.alumni.service.DialogService;
import com.card.alumni.vo.DialogVO;
import com.card.alumni.vo.query.DialogQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author sunxiaodong10 2020/1/18
 * @date 12:01 PM
 */
@RestController
@RequestMapping("/dialog")
@Api(value = "聊天记录模块", tags = "聊天记录模块")
public class DialogController {

    private final Logger LOGGER = LoggerFactory.getLogger(DialogController.class);

    @Resource
    private DialogService dialogService;

    /**
     * 保存
     *
     * @param dialogVO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "保存")
    public UnifiedResult<Integer> save(DialogVO dialogVO) {
        LOGGER.info("DialogController#save param:{}", JSON.toJSONString(dialogVO));

        return UnifiedResult.success(dialogService.save(dialogVO));
    }

    /**
     * 分页查询
     *
     * @param dialogQuery
     * @return
     */
    @PostMapping("/queryPage")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public UnifiedResult<PageData<DialogVO>> queryPage(DialogQuery dialogQuery) {
        LOGGER.info("DialogController#queryPage param:{}", JSON.toJSONString(dialogQuery));

        return UnifiedResult.success(dialogService.queryPage(dialogQuery));
    }

    /**
     * 删除
     *
     * @param dialogQuery
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public UnifiedResult deleteDialog(DialogQuery dialogQuery) {
        LOGGER.info("DialogController#deleteDialog param:{}", JSON.toJSONString(dialogQuery));

        dialogService.deleteDialog(dialogQuery);
        return UnifiedResult.success();
    }

    /**
     * 更新
     *
     * @param dialogVO
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新")
    public UnifiedResult updateDialog(DialogVO dialogVO) {
        LOGGER.info("DialogController#updateDialog param:{}", JSON.toJSONString(dialogVO));

        dialogService.updateDialog(dialogVO);
        return UnifiedResult.success();
    }
}
