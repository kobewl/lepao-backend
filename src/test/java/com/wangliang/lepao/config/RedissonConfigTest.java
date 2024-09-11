package com.wangliang.lepao.config;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RedissonConfigTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void test() {

        // list
        /*List<String> list1 = new ArrayList<>();
        list1.add("wangliang");
        System.out.println("1:"+list1.get(0));

        RList<String> list = redissonClient.getList("list");
        //list.add("wangliang");
        System.out.println("2:"+list.get(0));
        list.remove(0);*/

        // map
        /*Map<String, String> map = new HashMap<>();
        map.put("name", "wangliang");
        System.out.println("3:"+map.get("name"));

        RMap<String, String> map1 = redissonClient.getMap("map");
        //map1.put("name", "wangliang");
        System.out.println("4:"+map1.get("name"));
        map1.remove("name");*/
    }

}