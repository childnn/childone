package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/04/05
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private String name;
    private int age;

}
