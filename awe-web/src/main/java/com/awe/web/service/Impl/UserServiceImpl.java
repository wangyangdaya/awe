package com.awe.web.service.Impl;

import com.awe.web.mapper.UserMapper;
import com.awe.web.model.domain.User;
import com.awe.web.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/17.</p>
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    @Transactional
    public PageInfo<User> findUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.findUsers();
        return new PageInfo<>(users);
    }

    @Override
    @Transactional
    public User selectById(int id) {
        return userMapper.selectById(id);
    }
}
