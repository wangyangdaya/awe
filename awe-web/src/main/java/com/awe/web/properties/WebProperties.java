package com.awe.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description web 配置
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/22.</p>
 */
@ConfigurationProperties(prefix = "awt.web")
public class WebProperties {
    private String restfulPrefix;

    public String getRestfulPrefix() {
        return restfulPrefix;
    }

    public void setRestfulPrefix(String restfulPrefix) {
        this.restfulPrefix = restfulPrefix;
    }
}
