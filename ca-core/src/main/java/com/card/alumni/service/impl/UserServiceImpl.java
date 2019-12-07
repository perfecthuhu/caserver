package com.card.alumni.service.impl;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.dao.CaUserMapper;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;
import com.card.alumni.exception.ResultCodeEnum;
import com.card.alumni.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 3:31 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private CaUserMapper caUserMapper;

    @Override
    public UnifiedResponse queryUserById(Integer userId){
        try {
            if (Objects.isNull(userId)) {
                return new UnifiedResponse(ResultCodeEnum.PARAM_EMPTY);
            }
            CaUser caUser = caUserMapper.selectByPrimaryKey(userId);
            if (Objects.nonNull(caUser)) {
                return new UnifiedResponse(caUser);
            }
        } catch (Exception e) {
            LOGGER.error("查询用户异常, 入参:{}", userId, e);
        }
        return null;
    }
}
