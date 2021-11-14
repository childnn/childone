package org.anonymous.lombok.builder;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 15:24
 */
// @NoArgsConstructor
// @AllArgsConstructor
@Data
@Builder
public class BuilderDemo {
    private final String firstName;
    private final String lastName;
    private final String email;

    public static void main(String[] args) {
        BuilderDemo demo = BuilderDemo.builder()
                .firstName("Child")
                .lastName("Jack")
                .email("27497963@qq.com")
                .build();

        String s = JSON.toJSONString(demo);
        System.out.println("s = " + s);

        // 反序列化时, 必须存在 public 构造,
        // 否则: com.alibaba.fastjson.util.JavaBeanInfo: default constructor not found.
        JSON.parseObject(s, BuilderDemo.class);
    }

}
