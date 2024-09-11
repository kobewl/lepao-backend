package com.wangliang.lepao.service;

import com.wangliang.lepao.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private com.wangliang.lepao.service.UserService userService;

    @Test
    public void testSearchUsersByTags() {
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchUsersByTags(tagNameList);
        Assert.assertNotNull(userList);
    }

    @Test
    void userRegister() {
    }

    @Test
    void userLogin() {
    }

    @Test
    void getSafetyUser() {
    }

    @Test
    void userLogout() {
    }

    @Test
    void searchUsersByTags() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getLoginUser() {
    }

    @Test
    void isAdmin() {
    }

    @Test
    void testIsAdmin() {
    }

    @Test
    void getMainUserIds() {
        List<Long> mainUserIds = userService.getMainUserIds();
        System.out.println(mainUserIds);
    }
}