package com.card.alumni.service;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 3:22 PM
 */
public interface UserService {

    UnifiedResponse queryUserById(Integer userId);
}
