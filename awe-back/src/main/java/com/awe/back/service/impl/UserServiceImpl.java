package com.awe.back.service.impl;

import com.awe.back.mapper.UserMapper;
import com.awe.back.model.domain.User;
import com.awe.back.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/26.</p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

}
