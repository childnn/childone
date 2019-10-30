package hutools.clone;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/15 17:22
 */
public class DeepCloneTest {

    @Test
    public void test() {
        Cat cat = new Cat().setName("cat").setAge(13);

        Dog dog = new Dog().setCat(cat);
        Dog cloneDog = ObjectUtil.clone(dog);
        System.out.println("cloneDog = " + cloneDog);

        Monkey monkey = new Monkey()
                .setCat(new Cat1().setAge(111).setName("cat1"));
        Monkey cloneMonkey = ObjectUtil.clone(monkey);
        System.out.println("cloneMonkey = " + cloneMonkey);

        Monkey monkeyClone = ObjectUtil.cloneByStream(monkey);
        System.out.println("monkeyClone = " + monkeyClone);
    }

    @Data
    @Accessors(chain = true)
    public static class Cat implements Cloneable<Cat> {

        private static final long serialVersionUID = -540055018847409959L;
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

    @Data
    @Accessors(chain = true)
    public static class Cat1 implements Serializable {

        private static final long serialVersionUID = -540055018847409959L;
        private String name = "miao";
        private int age = 2;


    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Accessors(chain = true)
    private static class Dog extends CloneSupport<Dog> {
        private String name = "wang";
        private int age = 2;
        private Cat cat;
    }

    /**
     * deep clone, 本类及其依赖类都必须实现 {@link Serializable}.
     */
    @Data
    @Accessors(chain = true)
    private static class Monkey implements Serializable {

        private static final long serialVersionUID = -4819457467150574914L;

        private String name = "King";
        private int age = 3;
        private Cat1 cat;
    }



}
