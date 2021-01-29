package org.anonymous.dao;

import org.anonymous.domain.User;

import java.util.List;

/**
 * @author child
 * 2019/4/11 20:51
 */
public interface UserDao {
    //查询所有用户及用户的所有账户
    List<User> findUser();

    //查询所有用户及每个用户所具有的所有角色
    List<User> findRole();

    //根据 uid 查询所有用户
    User findUserById(int uid);

    //查询所有用户
    List<User> findUsers();

}
