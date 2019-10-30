package org.anonymous.lombok;

import lombok.ToString;

import java.time.LocalDate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see ToString#includeFieldNames() 是否包含字段名称.
 * @see ToString#exclude() 排除的字段.
 * @see ToString#of() 需要打印的字段.
 * @see ToString#callSuper() 是否包含父类的 toString 方法返回的结果.
 * @since 2019/11/11 14:57
 */
@ToString(exclude = {"dateOfBirth"})
public class ToStringDemo {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;

    public static void main(String[] args) {
        System.out.println(new ToStringDemo());
    }

    /*
      编译文件.
       public String toString() {
        return "ToStringDemo(firstName=" + this.firstName + ", lastName=" + this.lastName + ")";
    }*/
}
