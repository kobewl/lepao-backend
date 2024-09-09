package com.wangliang.lepao.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wangliang.lepao.model.domain.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author wangliang
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 原始用户
     * @return User
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return int
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList 标签列表
     * @return List<User>
     */
    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     * @param user, request
     * @return int
     */
    int updateUser(User user, User loginUser);

    /**
     * 获取当前登录用户信息
     * @return User
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request request
     * @return boolean
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param loginUser 登录用户
     * @return boolean
     */
    boolean isAdmin(User loginUser);


}
