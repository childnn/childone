package hutools.clone;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/15 10:31
 * 泛型 clone 接口.
 * @see Cloneable
 */
public class CloneTest {

    @Test
    public void cat() {
        Cat cat = new Cat()
                .setAge(3).setName("child");
        Cat cloneCat = cat.clone();
        System.out.println("cloneCat = " + cloneCat);
    }

    @Test
    public void dog() {
        Dog dog = new Dog().setAge(5).setName("wangwang");
        Dog cloneDog = dog.clone();
        System.out.println("cloneDog = " + cloneDog);
    }

    /**
     * @see Cloneable
     */
    @Data
    @Accessors(chain = true)
    public static class Cat implements Cloneable<Cat> {

        private String name = "miao";
        private int age = 2;

        @Override
        public Cat clone() {
            try {
                return (Cat) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new CloneRuntimeException(e);
            }
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    private static class Dog extends CloneSupport<Dog> {
        private String name = "wang";
        private int age = 2;
    }



}
