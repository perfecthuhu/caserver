package com.card.alumni.service.impl;

import com.card.alumni.common.PageData;
import com.card.alumni.dao.CaDialogMapper;
import com.card.alumni.entity.CaDialog;
import com.card.alumni.entity.CaDialogExample;
import com.card.alumni.exception.CaException;
import com.card.alumni.service.DialogService;
import com.card.alumni.service.UserService;
import com.card.alumni.vo.DialogVO;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.DialogQuery;
import com.card.alumni.vo.query.UserQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author sunxiaodong10 2020/1/18
 * @date 10:56 AM
 */
@Service
public class DialogServiceImpl implements DialogService {

    @Resource
    private CaDialogMapper caDialogMapper;

    @Resource
    private UserService userService;

    @Override
    public Integer save(DialogVO dialogVO) {
        checkDialogVO(dialogVO);

        CaDialog caDialog = buildDialogVO(dialogVO);

        if (caDialogMapper.insert(caDialog) != 1) {
            throw new CaException("保存消息记录失败");
        }

        return caDialog.getId();
    }

    private void checkDialogVO(DialogVO dialogVO) {
        if (Objects.isNull(dialogVO)) {
            throw new CaException("参数为空");
        }
        if (Objects.isNull(dialogVO.getTargetId())) {
            throw new CaException("目标ID为空");
        }
        if (Objects.isNull(dialogVO.getType())) {
            throw new CaException("类型为空");
        }
        if (Objects.isNull(dialogVO.getFromId())) {
            throw new CaException("源ID为空");
        }
    }

    private CaDialog buildDialogVO(DialogVO dialogVO) {
        CaDialog caDialog = new CaDialog();

        BeanUtils.copyProperties(dialogVO, caDialog);

        Date now = new Date(System.currentTimeMillis());
        caDialog.setCreateTime(now);
        caDialog.setUpdateTime(now);

        return caDialog;
    }

    @Override
    public PageData<DialogVO> queryPage(DialogQuery dialogQuery) {
        CaDialogExample example = buildCaDialogExample(dialogQuery);

        PageHelper.startPage(dialogQuery.getPage(), dialogQuery.getPageSize());

        List<CaDialog> caDialogs = caDialogMapper.selectByExample(example);

        PageInfo<CaDialog> pageInfo = new PageInfo<>(caDialogs);
        List<DialogVO> dialogVOS = convertDialogVOList(caDialogs);
        queryDialogUser(dialogVOS);

        PageData<DialogVO> dialogVOPageData = new PageData<>(pageInfo.getTotal(), dialogVOS);

        return dialogVOPageData;
    }

    private void queryDialogUser(List<DialogVO> dialogVOS) {
        if (CollectionUtils.isEmpty(dialogVOS)) {
            return;
        }
        Set<Integer> userIdSet = dialogVOS.stream().map(s -> Integer.parseInt(s.getFromId())).collect(Collectors.toSet());
        userIdSet.addAll(dialogVOS.stream().map(s -> Integer.parseInt(s.getTargetId())).collect(Collectors.toSet()));
        UserQuery userQuery = new UserQuery();
        userQuery.setIdList(Lists.newArrayList(userIdSet));
        userQuery.setPageSize(dialogVOS.size());
        PageData<UserVO> userVOPageData = userService.queryUserVO(userQuery);

        List<UserVO> userVOList;
        if (userVOPageData != null && CollectionUtils.isNotEmpty(userVOList = userVOPageData.getItems())) {
            Map<Integer, UserVO> userVOMap = userVOList.stream().collect(Collectors.toMap(UserVO::getId, Function.identity()));
            dialogVOS.stream().forEach(s -> {
                UserVO fromUser = userVOMap.get(s.getFromId());
                if (Objects.nonNull(fromUser)) {
                    s.setFromUser(fromUser);
                }
                UserVO targetUser = userVOMap.get(s.getTargetId());
                if (Objects.nonNull(targetUser)) {
                    s.setTargetUser(targetUser);
                }
            });
        }
    }

    private List<DialogVO> convertDialogVOList(List<CaDialog> caDialogs) {
        if (CollectionUtils.isEmpty(caDialogs)) {
            return new ArrayList<>();
        }
        return caDialogs.stream().map(this::convertDialogVO).collect(Collectors.toList());
    }

    private DialogVO convertDialogVO(CaDialog caDialog) {
        DialogVO dialogVO = new DialogVO();

        BeanUtils.copyProperties(caDialog, dialogVO);
        return dialogVO;
    }

    private CaDialogExample buildCaDialogExample(DialogQuery dialogQuery) {
        CaDialogExample example = new CaDialogExample();
        CaDialogExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(dialogQuery.getId())) {
            criteria.andIdEqualTo(dialogQuery.getId());
        }
        if (Objects.nonNull(dialogQuery.getFromId())) {
            criteria.andFromIdEqualTo(dialogQuery.getFromId());
        }
        if (Objects.nonNull(dialogQuery.getType())) {
            criteria.andTypeEqualTo(dialogQuery.getType());
        }
        if (Objects.nonNull(dialogQuery.getTargetId())) {
            criteria.andTargetIdEqualTo(dialogQuery.getTargetId());
        }
        if (Objects.nonNull(dialogQuery.getSentStatus())) {
            criteria.andSentStatusEqualTo(dialogQuery.getSentStatus());
        }
        return example;
    }

    @Override
    public void deleteDialog(DialogQuery dialogQuery) {
        CaDialogExample example = buildCaDialogExample(dialogQuery);
        caDialogMapper.deleteByExample(example);
    }

    @Override
    public void updateDialog(DialogVO dialogVO) {
        if (Objects.isNull(dialogVO)) {
            throw new CaException("参数为空");
        }
        if (Objects.isNull(dialogVO.getTargetId())) {
            throw new CaException("目标ID为空");
        }
        CaDialog caDialog = convertCaDialog(dialogVO);

        CaDialogExample example = new CaDialogExample();
        CaDialogExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(dialogVO.getId())) {
            criteria.andIdEqualTo(dialogVO.getId());
        }

        if (Objects.nonNull(dialogVO.getTargetId())) {
            criteria.andTargetIdEqualTo(dialogVO.getTargetId());
        }
        caDialogMapper.updateByExample(caDialog, example);
    }

    private CaDialog convertCaDialog(DialogVO dialogVO) {
        CaDialog caDialog = new CaDialog();

        BeanUtils.copyProperties(dialogVO, caDialog);
        return caDialog;
    }


}
