package com.awe.web.service;

import com.awe.web.model.domain.User;
import com.github.pagehelper.PageInfo;

/**
 * description 用户信息
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/22.</p>
 */
public interface UserService {

    int addUser(User user);

    PageInfo<User> findUsers(int pageNum, int pageSize);

    User selectById(int id);
}
