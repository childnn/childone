package org.anonymous.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 13:50
 */
//@Mapper
@Accessors(chain = true)
@AllArgsConstructor
@Data
public class User {

    private int id;

    private String name;

    private Role role;

}
