package org.anonymous.service.impl;

import org.anonymous.dao.UserMapper;
import org.anonymous.service.UserService;
import org.springframework.stereotype.Service;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/15 20:17
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;

    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void in() {

    }

    @Override
    public void out() {

    }
}
