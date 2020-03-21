package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.UserFeedback;
import com.card.alumni.model.FeedBackModel;
import com.card.alumni.request.FeedBackQueryRequest;
import com.card.alumni.request.FeedBackRequest;

/**
 * @author sunxiaodong10 2020/1/21
 * @date 5:50 PM
 */
public interface FeedBackService {

    /**
     * 分页查询问题反馈
     *
     * @param feedBackRequest
     * @return
     */
    PageData<FeedBackModel> queryPage(FeedBackQueryRequest feedBackRequest);

    UserFeedback findById(Integer id);

    FeedBackModel findModeById(Integer id);

    void resolve(Integer id);

    /**
     * 解决反馈问题（支持批量）
     *
     * @param feedBackRequest
     */
    void batchResolve(FeedBackRequest feedBackRequest);

}
