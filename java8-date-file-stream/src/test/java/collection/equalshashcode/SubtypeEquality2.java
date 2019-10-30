package collection.equalshashcode;

import java.util.HashSet;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 18:07
 */
public class SubtypeEquality2 {
    public static void main(String[] args) {
        Set<Animal> pets = new HashSet<>();
        pets.add(new Dog2("Ralph", Size.MEDIUM));
        pets.add(new Pig2("Ralph", Size.MEDIUM));
        pets.forEach(System.out::println);
    }
}

class Dog2 extends Animal {
    Dog2(String name, Size size) {
        super(name, size);
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof Dog2 && super.equals(rval);
    }
}

class Pig2 extends Animal {
    Pig2(String name, Size size) {
        super(name, size);
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof Pig2 &&
                super.equals(rval);
    }

}