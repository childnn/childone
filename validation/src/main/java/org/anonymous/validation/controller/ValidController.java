package org.anonymous.validation.controller;

import org.anonymous.validation.entity.ValidEntity;
import org.springframework.core.ResolvableType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/16 13:42
 */
@Validated
@RestController
public class ValidController {

    // 在类上加 @Validated 注解, 方法参数不加 @Valid/@Validated 注解, 查看区别
    @PostMapping("valid")
    public Object valid(@Valid @RequestBody ValidEntity entity) {
        return entity;
    }

    @PostMapping("validated")
    public Object validated(@Validated @RequestBody ValidEntity entity) {
        return entity;
    }

    @PostMapping("list")
    public Object list(@RequestBody
                           @NotEmpty(message = "入参集合不可为空") // 必须配合类上注解 @Validated
                                   Collection<String> list) {
        return list;
    }

    public <S extends ValidController, T> void resolve(List<S> src, Class<T> clazz) {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method r = ValidController.class.getDeclaredMethod("resolve", List.class, Class.class);
        ResolvableType rt =
                ResolvableType.forMethodParameter(r, 0);
        Class<?> resolve = rt.getGeneric(0).resolve();
        ResolvableType rt1 = ResolvableType.forMethodParameter(r, 1);
        Class<?> resolve1 = rt1.getGeneric(0).resolve();

        System.out.println("resolve = " + resolve);
        System.out.println("resolve1 = " + resolve1);

        System.out.println(decodeUnicode("\\u4e2a\\u6570\\u5fc5\\u987b\\u5728\\u548c\\u4e4b\\u95f4"));

    }

    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuilder buffer = new StringBuilder();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(Character.toString(letter));
            start = end;
        }
        return buffer.toString();
    }
}
