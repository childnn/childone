package org.anonymous.test;

import org.anonymous.model.Role;
import org.anonymous.model.User;
import org.anonymous.model.UserMapper;
import org.anonymous.model.UserRoleDTO;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 14:03
 */
public class App {
    public static void main(String[] args) {
        Role role = new Role();
        role.setId(1);
        role.setName("role");
        UserRoleDTO userRoleDTO = UserMapper.MAPPER.toDTO(new User(12, "jack", role));
        System.out.println("userRoleDTO = " + userRoleDTO);
    }
}
