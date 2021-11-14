package entity;

import lombok.Data;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/29 19:54
 */
@Data
public class Person1 {

    private int age;
    private String name;
    private List<Stu1> children;
    private Stu stu;
    private List<String> strs;

}
