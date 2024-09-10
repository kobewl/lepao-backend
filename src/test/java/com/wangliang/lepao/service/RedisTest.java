package com.wangliang.lepao.service;
import java.util.Date;


import com.wangliang.lepao.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void test(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("name","lepao");
        valueOperations.set("int","1");
        valueOperations.set("double","2.0");
        User user = new User();
        user.setId(0L);
        user.setUsername("");
        user.setUserAccount("");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("");
        user.setPhone("");
        user.setEmail("");
        user.setTags("");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);
        user.setUserRole(0);
        user.setPlanetCode("");
        valueOperations.set("user",user);
        // 查询
        Object name = valueOperations.get("name");
        Assertions.assertTrue("lepao".equals(name));

        String intStr = (String) valueOperations.get("int");
        Assertions.assertTrue(1 == Integer.parseInt(intStr));

        String doubleStr = (String) valueOperations.get("double");
        Assertions.assertTrue(2.0 == Double.parseDouble(doubleStr));

        System.out.println(valueOperations.get("user"));

        redisTemplate.delete("name");
        redisTemplate.delete("int");
        redisTemplate.delete("double");
        redisTemplate.delete("user");
    }
}
