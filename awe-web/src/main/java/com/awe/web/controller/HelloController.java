package com.awe.web.controller;

import com.awe.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodType;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/9.</p>
 */
@Api(value = "HelloController", description = "用户接口")
@RestController
public class HelloController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "hello", method = RequestMethod.POST)
    public String hello() {
        return "Hello World";
    }

    @ApiOperation(value = "获取用户信息", notes = "分页查询用户信息")
    @ApiImplicitParam(name = "pageNum", value = "当前页码", required = true, dataType = "Integer", paramType = "query")
    @RequestMapping(value = "findUsers", method = RequestMethod.POST)
    public Object findUsers(@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
//        return userService.findUsers(pageNum, pageSize);
        return userService.selectById(1);
    }
}
