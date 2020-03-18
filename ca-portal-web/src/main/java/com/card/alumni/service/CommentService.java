package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.entity.CaComment;
import com.card.alumni.model.CommentModel;
import com.card.alumni.request.CommentQueryRequest;
import com.card.alumni.request.CommentRequest;

import java.util.List;

/**
 * @author liumingyu
 * @date 2020-03-18 11:11 AM
 */
public interface CommentService {

    Long save(CommentRequest request);

    void deleteById(Long id);

    CaComment findById(Long id);

    List<CaComment> listByTenantIdAndTargetId(Integer tenantId, Long targetId, List<Long> rootIdList);

    int countByTenantAndTargetId(Integer tenantId, Long targetId);

    PageData<CommentModel> pageByRequest(CommentQueryRequest request);
}
