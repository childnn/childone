package jackson.pojo.serial.ignoreprop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jackson.test.IgnorePropsTest;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/22 17:00
 * @see JsonIgnoreProperties#ignoreUnknown() 忽略未知属性. -- 一般针对反序列化(eg: string 转 对象时.)
 * @see IgnorePropsTest#test1()
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(value = {"name"}, allowGetters = true, ignoreUnknown = true) // 序列化时不忽略, 反序列化时被忽略 -- 忽略 set.
public class Student {
    private int id;
    private String name;
    private String sex;
}
