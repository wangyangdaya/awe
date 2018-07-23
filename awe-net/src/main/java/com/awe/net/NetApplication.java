package com.awe.net;

import com.awe.net.service.DiscardServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/20.</p>
 */
@SpringBootApplication
public class NetApplication {

    public static void main(String[] args) {

        SpringApplication.run(NetApplication.class, args);
    }

//    @Bean
    public InitializingBean initializingBean() {
        return() -> {
            DiscardServer discardServer = new DiscardServer();
            discardServer.start(8889);
        };
    }
}
