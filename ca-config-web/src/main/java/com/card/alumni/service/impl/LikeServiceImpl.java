package com.card.alumni.service.impl;

import com.card.alumni.dao.CaLikeMapper;
import com.card.alumni.entity.CaLike;
import com.card.alumni.entity.CaLikeExample;
import com.card.alumni.enums.LikeTenantEnum;
import com.card.alumni.exception.CaException;
import com.card.alumni.request.LikeRequest;
import com.card.alumni.service.LikeService;
import com.card.alumni.utils.RequestUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 点赞服务
 *
 * @author liumingyu
 * @date 2020-03-18 11:12 AM
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private CaLikeMapper caLikeMapper;

    @Override
    public Long like(LikeRequest request) {
        checkRequest(request);
        Integer userId = RequestUtil.getUserId();

        CaLike like = findByTenantIdAndTargetIdAndUserId(request.getTenantId(), request.getTargetId(), userId);
        if (Objects.nonNull(like)) {
            throw new CaException("已经点赞过了,请勿重复操作~");
        }

        like = new CaLike();
        like.setTenantId(request.getTenantId());
        like.setTargetId(request.getTargetId());

        like.setUpdater(userId);
        like.setCreator(userId);

        Date now = new Date();
        like.setCreateTime(now);
        like.setUpdateTime(now);

        like.setIsDelete(Boolean.FALSE);

        caLikeMapper.insert(like);

        return like.getId();
    }

    @Override
    public void cancel(LikeRequest request) {
        checkRequest(request);

        Integer userId = RequestUtil.getUserId();
        CaLike like = findByTenantIdAndTargetIdAndUserId(request.getTenantId(), request.getTargetId(), userId);
        if (Objects.isNull(like) || like.getIsDelete()) {
            throw new CaException("点赞已经取消,请勿频繁操作~");
        }

        like.setIsDelete(Boolean.TRUE);
        like.setUpdater(userId);
        like.setUpdateTime(new Date());

        caLikeMapper.updateByPrimaryKeySelective(like);
    }

    @Override
    public int countByTenantIdAndTargetId(Integer tenantId, Long targetId) {
        checkParam(tenantId, targetId);

        CaLikeExample example = new CaLikeExample();
        CaLikeExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andTargetIdEqualTo(targetId);

        return caLikeMapper.countByExample(example);
    }

    @Override
    public Map<Long, Integer> mapCountByTenantIdAndTargetIds(Integer tenantId, List<Long> targetIds) {
        checkTenant(tenantId);
        if (CollectionUtils.isEmpty(targetIds)) {
            return Maps.newHashMap();
        }
        CaLikeExample example = new CaLikeExample();
        CaLikeExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdIn(targetIds);
        criteria.andTenantIdEqualTo(tenantId);
        List<CaLike> likeList = caLikeMapper.selectByExample(example);
        Map<Long, List<CaLike>> likeListMap = likeList.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(CaLike::getTargetId));

        Map<Long, Integer> result = Maps.newHashMap();
        for (Map.Entry<Long, List<CaLike>> entry : likeListMap.entrySet()) {
            result.put(entry.getKey(), CollectionUtils.isEmpty(entry.getValue()) ? 0 : entry.getValue().size());
        }
        return result;
    }

    @Override
    public CaLike findByTenantIdAndTargetIdAndUserId(Integer tenantId, Long targetId, Integer userId) {
        checkParam(tenantId, targetId);

        CaLikeExample example = new CaLikeExample();
        CaLikeExample.Criteria criteria = example.createCriteria();
        criteria.andTargetIdEqualTo(targetId);
        criteria.andTenantIdEqualTo(tenantId);
        criteria.andCreatorEqualTo(userId);
        criteria.andIsDeleteEqualTo(Boolean.FALSE);
        List<CaLike> likeList = caLikeMapper.selectByExample(example);

        return CollectionUtils.isEmpty(likeList) ? null : likeList.get(0);
    }

    private void checkRequest(LikeRequest request) {
        if (Objects.isNull(request)) {
            throw new CaException("请求不能为空");
        }

        checkParam(request.getTenantId(), request.getTargetId());
    }

    private void checkParam(Integer tenantId, Long targetId) {
        if (Objects.isNull(targetId)) {
            throw new CaException("目标ID不能为空");
        }

        checkTenant(tenantId);
    }

    private void checkTenant(Integer tenantId) {
        LikeTenantEnum tenantEnum = LikeTenantEnum.getEnumByType(tenantId);
        if (Objects.isNull(tenantEnum)) {
            throw new CaException("非法的点赞接入方类型");
        }
    }
}
