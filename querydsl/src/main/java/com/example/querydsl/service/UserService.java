package com.example.querydsl.service;

import com.example.querydsl.entity.User;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 19:30
 */
public interface UserService {

    List<User> queryAll();

    User detail(Long id);

    User detail2(Long id);

    User detail3(Long id);

    List<User> likeQueryWithName(String name);
}
