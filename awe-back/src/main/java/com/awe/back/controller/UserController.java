package com.awe.back.controller;

import com.awe.back.model.domain.User;
import com.awe.back.model.vo.UserVo;
import com.awe.back.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/26.</p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String add(@RequestBody UserVo user) {
        User u = new User();
        u.setName(user.getName());
        u.setAge(user.getAge());
        u.setFullName(user.getFullName());
        return userService.insert(u) ? "添加成功" : "添加失败";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Integer id) {
        return userService.deleteById(id) ? "删除成功" : "删除失败";
    }

    @PostMapping("/update")
    public String update(@RequestBody User user) {
        return userService.updateById(user) ? "修改成功" : "修改失败";
    }

    @GetMapping("/list")
    public List<User> list() {
        Wrapper<User> wrapper = new EntityWrapper<>();
        return userService.selectList(wrapper);
    }

    @GetMapping("/listPage")
    public Page<User> listPage() {
        Page<User> page = new Page<>(1, 10);
        return userService.selectPage(page);
    }
}
