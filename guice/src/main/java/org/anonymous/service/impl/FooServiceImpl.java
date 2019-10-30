package org.anonymous.service.impl;

import com.google.inject.Inject;
import org.anonymous.dao.UserMapper;
import org.anonymous.entity.User;
import org.anonymous.service.FooService;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/6 15:44
 */
public class FooServiceImpl implements FooService {

    @Inject
    private UserMapper userMapper;

    @Override
    public User doSomeBusinessStuff(int userId) {
        return userMapper.getUser(userId);
    }
}
