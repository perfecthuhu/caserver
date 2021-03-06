package com.card.alumni.service;

import com.card.alumni.common.PageData;
import com.card.alumni.context.User;
import com.card.alumni.entity.CaUser;
import com.card.alumni.exception.CaException;
import com.card.alumni.model.SimpleUserModel;
import com.card.alumni.request.FeedbackRequest;
import com.card.alumni.request.UserSchoolAlumniRequest;
import com.card.alumni.vo.UserVO;
import com.card.alumni.vo.query.UserQuery;

import java.util.List;

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
     * 查询用户信息
     *
     * @param userQuery
     * @return
     */
    List<UserVO> queryUserList(UserQuery userQuery);

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
     * 根据用户ID列表查询用户列表
     *
     * @param userIdList 用户ID列表
     * @return 用户列表
     */
    List<SimpleUserModel> listSimpleUserModelByIdList(List<Integer> userIdList);

    /**
     * 用户退出
     * @param userId
     */
    void loginOut(Integer userId) throws CaException;

    /**
     * 查询我的信息
     *
     * @return 用户信息
     * @throws CaException e
     */
    UserVO findMyUserInfo() throws CaException;

    /**
     * 保存用户反馈
     * @param request
     */
    void saveFeedBack(FeedbackRequest request) throws CaException;

    /**
     * 查询学校组织校友会成员接口
     * @param request
     * @return
     */
    List<UserVO> querySchoolAlumni(UserSchoolAlumniRequest request);
}
