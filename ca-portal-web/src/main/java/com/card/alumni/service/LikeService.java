package com.card.alumni.service;

import com.card.alumni.entity.CaLike;
import com.card.alumni.request.LikeRequest;

import java.util.List;
import java.util.Map;

/**
 * @author liumingyu
 * @date 2020-03-18 11:12 AM
 */
public interface LikeService {

    Long like(LikeRequest request);

    void cancel(LikeRequest request);

    int countByTenantIdAndTargetId(Integer tenantId, Long targetId);

    Map<Long, Integer> mapCountByTenantIdAndTargetIds(Integer tenantId, List<Long> targetId);

    CaLike findByTenantIdAndTargetIdAndUserId(Integer tenantId, Long targetId, Integer userId);

    boolean hasLike(Integer tenantId, Long targetId, Integer userId);

    Map<Long, Boolean> mapHasLike(Integer tenantId, List<Long> targetId, Integer userId);
}
