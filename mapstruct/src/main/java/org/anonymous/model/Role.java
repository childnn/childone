package org.anonymous.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 13:52
 */
@Accessors(chain = true)
//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class Role {

    private int id;

    private String name;
}
