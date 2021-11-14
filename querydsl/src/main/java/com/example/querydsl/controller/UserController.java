package com.example.querydsl.controller;

import com.example.querydsl.entity.User;
import com.example.querydsl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 19:26
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryAll")
    public List<User> queryAll() {
        return userService.queryAll();
    }

    @RequestMapping("detail/{id}")
    public User detail(@PathVariable("id") Long id) {
        return userService.detail(id);
    }

    @RequestMapping("detail2/{id}")
    public User detail2(@PathVariable("id") Long id) {
        return userService.detail2(id);
    }

    /**
     * @see com.example.querydsl.entity.User
     * @see com.fasterxml.jackson.annotation.JsonIgnoreProperties
     */
    @RequestMapping("detail3/{id}")
    public User detail3(@PathVariable("id") Long id) {
        return userService.detail3(id);
    }

    @RequestMapping("likeQueryWithName/{name}")
    public List<User> likeQueryWithName(@PathVariable("name") String name) {
        return userService.likeQueryWithName(name);
    }
}
