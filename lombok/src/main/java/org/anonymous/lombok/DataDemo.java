package org.anonymous.lombok;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.anonymous.lombok.builder.BuilderDemo;

import java.time.LocalDate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see lombok.ToString
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.RequiredArgsConstructor
 * @see lombok.EqualsAndHashCode
 * @since 2019/11/11 15:01
 * 等价于同时使用.
 */
@Data
public class DataDemo {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;

    public static void main(String[] args) {
        BuilderDemo demo = BuilderDemo.builder()
                .firstName("Child")
                .lastName("Jack")
                .email("27497963@qq.com")
                .build();

        String s = JSON.toJSONString(demo);
        System.out.println("s = " + s);
    }
}
