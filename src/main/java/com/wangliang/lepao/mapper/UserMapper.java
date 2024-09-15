package com.wangliang.lepao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangliang.lepao.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户 Mapper
 *
 * @author wangliang
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询重点用户
     * @return 重点用户列表
     */
    List<User> selectImportantUsers();

    /**
     * 更新用户信息
     */
    int updateUser(User user);
}



