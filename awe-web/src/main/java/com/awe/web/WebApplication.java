package com.awe.web;

import com.awe.core.util.SqliteUtils;
import com.awe.web.properties.WebProperties;
import com.awe.web.util.SpringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

/**
 * description springBoot
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@SpringBootApplication
@EnableConfigurationProperties({WebProperties.class})
public class WebApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);

        /**
         * 添加applicationContext
         */
        SpringUtils.setApplicationContext(applicationContext);
    }

    @Bean
    public InitializingBean init() {
        return () -> {
            try {
                SqliteUtils.create();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
    }

}
