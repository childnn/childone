package test;

import entity.Person;
import entity.Stu;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/1 15:52
 */
public class ComparatorTest {

    @Test
    public void nullsComp() {
        Person p = new Person();
        p.setName("X");
        p.setStu(new Stu(1, "3rose", true, null, null));
        Person p1 = new Person();
        p1.setName("A");
        p1.setStu(new Stu(1, "3rose", true, null, null));
        Person p3 = new Person();
        p3.setName("A");
        p3.setStu(new Stu(1, "1rose", true, null, null));
        Person p2 = new Person();
        p2.setName(null);
        p2.setStu(new Stu(1, null, true, null, null));
        Person p4 = new Person();
        p4.setName(null);
        p4.setStu(new Stu(1, "aaaaa", true, null, null));

        List<Person> result = Arrays.asList(p, p1, p2, p3, p4, new Person(), null);

        // System.out.println("result = " + result);
        sout(result);

        result.sort(
                Comparator.nullsFirst(Comparator.comparing(Person::getName, Comparator.nullsLast(String::compareTo)))
                .thenComparing(Person::getStu, Comparator.nullsFirst(Comparator.comparing(Stu::getName, Comparator.nullsFirst(String::compareTo))))
        );

        sout(result);
        // System.out.println("result = " + result);
    }

    private void sout(List<Person> result) {
        result.forEach(System.out::println);
        System.out.println("===============================");
    }

}
