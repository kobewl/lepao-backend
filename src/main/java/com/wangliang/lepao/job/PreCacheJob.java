package com.wangliang.lepao.job;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangliang.lepao.mapper.UserMapper;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 预热缓存
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 动态获取重点用户
    private List<Long> getMainUserLists() {
        return userService.getMainUserIds();
    }

    // 每天执行预热缓存任务
    @Scheduled(cron = "0 0 0 * * ?")
    public void preCache() {
        for (Long userId : getMainUserLists()) {
            String redisKey = String.format("lepao:user:recommend:%s", userId);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
            // 将用户信息缓存到redis中
            try {
                redisTemplate.opsForValue().set(redisKey, userPage, 60, TimeUnit.MINUTES);
            } catch (Exception e) {
                log.error("redis set key error for user {}", userId, e);
            }
        }
    }
}
