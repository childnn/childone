package com.example.querydsl.dao;

import com.example.querydsl.dao.base.BaseJPA;
import com.example.querydsl.entity.User;
import org.springframework.stereotype.Repository;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 19:23
 */
@Repository
public interface UserJPA extends BaseJPA<User> {
}
