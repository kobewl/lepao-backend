package com.wangliang.lepao.constant;

/**
 * 用户常量
 *
 * @author wangliang
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */

    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 盐值，混淆密码
     */
    String SALT = "wangliang";

    //  ------- 权限 --------

    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;

    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;

}
