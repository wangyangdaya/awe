package com.awe.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * description 用户信息
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/22.</p>
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public Map login(String userName, String passwrod) {
        Map<String, Object> data = new HashMap<>();
        data.put("username", "test");
        return data;
    }
}
