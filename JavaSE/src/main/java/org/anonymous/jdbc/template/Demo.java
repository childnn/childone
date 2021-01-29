package org.anonymous.jdbc.template;

import org.anonymous.jdbc.template.domain.Emp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * shadow
 * 2019/3/14 19:58
 */
public class Demo {
    @Test
    @DisplayName("测试")
    public void f() {
        Emp e = new Emp();
        System.out.println(e + "123");
    }
}
