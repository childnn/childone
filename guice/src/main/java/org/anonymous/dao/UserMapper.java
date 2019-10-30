package org.anonymous.dao;

import org.anonymous.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/6 15:43
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUser(int userId);
}
