package entity;

import lombok.Data;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/29 19:53
 */
@Data
public class Person {

    private int age;
    private String name;
    private List<Stu> children;
    private boolean def;
    private Stu stu;
    private List<String> strs;

}
