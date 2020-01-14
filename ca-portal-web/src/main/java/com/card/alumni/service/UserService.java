package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.context.User;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;

/**
 * @author sunxiaodong10 2019/12/7
 * @date 3:22 PM
 */
public interface UserService {


    /**
     * 登陆
     *
     * @param userVO
     * @param verificatioCode
     * @throws Exception
     */
    CaUser login(UserVO userVO, String verificatioCode) throws Exception;

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    User queryUserById(Integer userId);

    /**
     * 填写用户信息
     *
     * @param userVO
     * @return
     */
    void submitUserInfo(UserVO userVO) throws Exception;

    /**
     * 查询用户信息
     *
     * @param userQuery
     * @return
     */
    PageData<UserVO> queryUserVO(UserQuery userQuery);

    /**
     * 手机号查询用户信息
     *
     * @param phone
     * @return
     * @throws Exception
     */
    CaUser queryUserByPhone(String phone) throws Exception;

    /**
     * 发送验证码
     *
     * @param phone
     */
    void sendValidateCode(String phone) throws Exception;

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     * @throws CaException e
     */
    SimpleUserModel findUserById(Integer userId) throws CaException;

    /**
     * 查询我的信息
     *
     * @return 用户信息
     * @throws CaException e
     */
    UserVO findMyUserInfo() throws CaException;
}
