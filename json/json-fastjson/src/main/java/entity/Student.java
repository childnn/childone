package entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/17 22:32
 * @see JSON#parseObject(String, Class)
 */
@Data // 必须要 getter/setter 方法, 才能实现 JSON.parseObject
    // @ToString
public class Student {

    private String name;
    private Integer age;

    private Address address;
}
