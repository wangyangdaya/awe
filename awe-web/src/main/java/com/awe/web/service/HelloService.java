package com.awe.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@Service
public class HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    public void hello() {
        logger.info("Hello World");
    }

    public void hello(String name) {
        logger.info(String.format("Hello on params:%s", name));
    }

    public void hello(String name, String world) {
        logger.info("Hello two params:{},{}", name, world);
    }

    public void hello(String name, int age) {
        logger.info("Hello two params:{},int{}", name, age);
    }

    public void hello(String name, Integer age) {
        logger.info("Hello:{},Integer:{}", name, age);
    }

    public void hello(String... name) {
        Arrays.asList(name).stream().forEach((String n) -> logger.info(n));
    }
}
