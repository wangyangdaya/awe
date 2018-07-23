package com.awe.web.service;

import com.awe.web.WebApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@ExtendWith(SpringExtension.class)
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    void hello() {
        Assert.notNull(helloService, "no bean");
        helloService.hello();
    }

    @Test
    void redis() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("key0", "random1=" + Math.random());
        System.out.println(valueOperations.get("key0"));
    }

    @Test
    void test() {
        List list = new ArrayList();
        String[] strings = new String[]{"01", "02", "03", "04"};
        list.add(strings);
        list.stream()
                .filter(s -> Arrays.binarySearch((Object[]) s, "01") > 0)
                .findAny().orElse(null);
    }
}