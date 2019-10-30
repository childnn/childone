package org.anonymous.model;

import lombok.Data;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 13:57
 */
//@Accessors(chain = true)
@Data
public class UserRoleDTO {

    private int userId;

    private String username;

    private String roleName;

}
