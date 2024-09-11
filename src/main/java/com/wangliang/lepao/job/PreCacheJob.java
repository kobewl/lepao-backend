package com.wangliang.lepao.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangliang.lepao.config.RedissonConfig;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.wangliang.lepao.constant.SystemConstant.SYSTEM_NAME;

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

    @Resource
    private RedissonClient redissonClient;

    // 动态获取重点用户
    private List<Long> getMainUserLists() {
        return userService.getMainUserIds();
    }

    // 每天执行预热缓存任务
    @Scheduled(cron = "0 0 0 * * ?")
    public void preCache() {

        RLock lock = redissonClient.getLock(SYSTEM_NAME + ":precachejob:docache:lock");
        try {
            // 保证只有一个线程可以获取锁，其他线程直接跳过
            if (lock.tryLock(0, 3, TimeUnit.MILLISECONDS)) {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
