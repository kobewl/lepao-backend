package com.wangliang.lepao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangliang.lepao.common.ErrorCode;
import com.wangliang.lepao.constant.UserConstant;
import com.wangliang.lepao.exception.BusinessException;
import com.wangliang.lepao.mapper.UserMapper;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.wangliang.lepao.constant.UserConstant.SALT;
import static com.wangliang.lepao.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 *
 * @author wangliang
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 用户编号
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (planetCode.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号过长");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    /**
     * 用户登录
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求
     * @return  用户
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser 原始用户
     * @return User
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setTags(originUser.getTags());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList 用户要拥有的标签
     * @return List<User>
     */
    @Override
    public List<User> searchUsersByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 计算耗时,开始时间
        //long startTime = System.currentTimeMillis();
        // ----------------第一种方式：通过数据库查询   查询耗时：241ms----------------------------------
        /*QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 查询条件：标签包含所有输入的标签
        // like'' and lik''  and like''
        for (String tagName : tagNameList){
            queryWrapper = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        // 脱敏
        return userList.stream()
                .map(this::getSafetyUser)
                .collect(Collectors.toList());
*/      // ----------------第二种方式：通过内存查询  查询耗时：241ms----------------------------------
        // 1. 查询所有用户列表
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();
        // 2. 在内存中根据标签过滤用户列表
        return userList.stream().filter(user -> {
                    String tags = user.getTags();
                    Set<String> tempTageNameList = gson.fromJson(tags, new TypeToken<Set<String>>() {
                    }.getType());
                    // Optional java8新特性，如果为空，则返回一个空的集合，否则返回一个包含所有标签的集合
                    tempTageNameList = Optional.ofNullable(tempTageNameList).orElse(new HashSet<>());
                    for (String tagName : tagNameList) {
                        if (!tempTageNameList.contains(tagName)) {
                            return false;
                        }
                    }
                    return true;
                }).map(this::getSafetyUser)
                .collect(Collectors.toList());

        // 计算耗时,结束时间
/*        long endTime = System.currentTimeMillis();
        log.info("查询耗时：" + (endTime - startTime) + "ms");
        return userList;*/
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     * @param loginUser 登录用户
     * @return int
     */
    @Override
    public int updateUser(@RequestBody User user, User loginUser) {
        // 判断权限, 管理员可以更新任意用户，普通用户只能更新自己的信息
        long userId = user.getId();
        if (loginUser == null || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 检查用户是否存在
        User oldUser = userMapper.selectById(userId);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 如果不是管理员，只允许更新当前（自己的）信息
        if (!isAdmin(loginUser) && userId != loginUser.getId()) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 如果用户没有传任何要更新的值，就直接报错，不用执行 update 语句
        if (user.equals(oldUser)) {
            throw new BusinessException(ErrorCode.NO_UPDATE_FIELD);
        }
        // 更新用户信息
        int updatedRows = userMapper.updateById(user);
        if (updatedRows == 0) {
            throw new BusinessException(ErrorCode.UPDATE_FAILED);
        }
        return updatedRows;
    }

    /**
     * 获取登录用户
     * @param request HttpServletRequest
     * @return User
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null){
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    /**
     * 是否为管理员
     *
     * @param loginUser
     * @return
     */
    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == UserConstant.ADMIN_ROLE;
    }

    /**
     * 获取重点用户id：活跃度高，评论多，影响力大，关注人数多的用户
     * @return List<Long>
     */
    @Override
    public List<Long> getMainUserIds() {
        List<Long> mainUserIds = new ArrayList<>();
        userMapper.selectImportantUsers().forEach(user -> mainUserIds.add(user.getId()));
        return mainUserIds;
    }

}


