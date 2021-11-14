package entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/29 19:54
 */
@Data
public class Stu1 {

    private int age;
    private String name;
    private boolean def;
    private BigDecimal bigDecimal;
    private int[] arr;

}
