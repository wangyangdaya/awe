package com.awe.web;

import com.awe.web.util.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

/**
 * description springBoot
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);

        /**
         * 添加applicationContext
         */
        SpringUtils.setApplicationContext(applicationContext);
    }
}
