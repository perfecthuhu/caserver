package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.vo.DialogVO;
import com.card.alumni.vo.query.DialogQuery;

/**
 * @author sunxiaodong10 2020/1/18
 * @date 10:52 AM
 */
public interface DialogService {

    /**
     * 保存
     * @param dialogVO
     * @return
     */
    Integer save(DialogVO dialogVO);

    /**
     * 分页查询
     * @param dialogQuery
     * @return
     */
    PageData<DialogVO> queryPage(DialogQuery dialogQuery);

    /**
     * 删除
     * @param dialogQuery
     */
    void deleteDialog(DialogQuery dialogQuery);

    /**
     * 更新
     * @param dialogVO
     */
    void updateDialog(DialogVO dialogVO);
}
