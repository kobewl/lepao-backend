package com.wangliang.lepao.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import static com.wangliang.lepao.constant.SystemConstant.SYSTEM_NAME;

@SpringBootTest
@Slf4j
class PreCacheJobTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void preCache() {

        RLock lock = redissonClient.getLock(SYSTEM_NAME + ":precachejob:docache:lock");
        try {
            // 保证只有一个线程可以获取锁，其他线程直接跳过
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                Thread.sleep(30000);
                System.out.println("get lock success" + Thread.currentThread().getId());
            }
        } catch (InterruptedException e) {
            log.error("preCache error", e);
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                System.out.println("unlock" + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }

}