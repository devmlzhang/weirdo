package com.weirdo.controller;

import com.weirdo.model.UserDomain;
import com.weirdo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @Author ML.Zhang
 * @Date 2018/5/25
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/add")
    public int addUser(UserDomain user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public UserDomain getUser(@PathVariable Integer id) {

        List<UserDomain> oneUser = userService.findOneUser(id);
        return oneUser.get(0);
    }

    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        return userService.findAllUser(pageNum, pageSize);
    }

    @GetMapping("/get")
    public UserDomain get(UserDomain user) {
        System.out.println(user.toString());
        return user;
    }

    @PostMapping("/post")
    public UserDomain post(@RequestBody UserDomain user) {
        System.out.println(user.toString());
        return user;
    }
}