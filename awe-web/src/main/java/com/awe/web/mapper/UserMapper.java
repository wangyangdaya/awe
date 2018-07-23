package com.awe.web.mapper;

import com.awe.web.model.domain.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/17.</p>
 */
public interface UserMapper extends BaseMapper<User> {

    int addUser(User user);

    List<User> findUsers();
}
