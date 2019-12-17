package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.context.User;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 3:22 PM
 */
public interface UserService {


    /**
     * 登陆
     * @param userVO
     * @param verificatioCode
     * @throws Exception
     */
    void login(UserVO userVO, String verificatioCode) throws Exception ;

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    User queryUserById(Integer userId);

    /**
     * 填写用户信息
     * @param userVO
     * @return
     */
    void submitUserInfo(UserVO userVO) throws Exception;

    /**
     * 查询用户信息
     * @param userQuery
     * @return
     */
    PageData<UserVO> queryUserVO(UserQuery userQuery);

    /**
     * 注册用户
     * @param verificatioCode
     * @return
     */
    void register(UserVO userVO, String verificatioCode) throws Exception;

}
