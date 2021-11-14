package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/29 19:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stu {

    private int age;
    private String name;
    private boolean def;
    private BigDecimal bigDecimal;
    private int[] arr;

}
