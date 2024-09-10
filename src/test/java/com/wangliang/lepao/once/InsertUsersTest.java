package com.wangliang.lepao.once;

import com.wangliang.lepao.mapper.UserMapper;
import com.wangliang.lepao.model.domain.User;
import com.wangliang.lepao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class InsertUsersTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService usersService;

    /**
     * 向数据库中插入10000000个用户
     */
    @Test
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("测试用户" + i);
            user.setUserAccount("fakeUser" + i);
            user.setAvatarUrl("https://www.pexels.com/zh-cn/video/26347967/");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("12345678");
            user.setEmail("12345678@qq.com");
            user.setTags("[\"测试标签\"]");
            user.setUserStatus(0);
            user.setIsDelete(0);
            user.setUserRole(0);
            user.setPlanetCode("11111111");
            userList.add(user);
        }
        usersService.saveBatch(userList, 100);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    /**
     * 并发批量向数据库中插入10000000个用户
     */
    @Test
    public void doConcurrentInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        // 分10组
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true) {
                j++;
                User user = new User();
                user.setUsername("测试用户" + i);
                user.setUserAccount("fakeUser" + i);
                user.setAvatarUrl("https://www.pexels.com/zh-cn/video/26347967/");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("12345678");
                user.setEmail("12345678@qq.com");
                user.setTags("[\"测试标签\"]");
                user.setUserStatus(0);
                user.setIsDelete(0);
                user.setUserRole(0);
                user.setPlanetCode("11111111");
                userList.add(user);
                if (j % 10000 == 0) {
                    break;
                }
            }
            // 异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName());
                Boolean insertedCount = usersService.saveBatch(userList, 10000);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

}