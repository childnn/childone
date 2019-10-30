package pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/22 15:36
 */
@Data
@Accessors(chain = true)
public class Student implements Serializable {
    // 静态变量不会被序列化.
    private static final long serialVersionUID = -4941741482387466532L;
    private Integer age;
    private String name;
    private transient String sex; // 在序列化时被忽略.
}
