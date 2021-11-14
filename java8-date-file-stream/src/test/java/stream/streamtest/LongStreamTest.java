package stream.streamtest;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/1 20:41
 */
public class LongStreamTest {

    @Test
    public void test() {
        Stream<Long> longStream = LongStream.rangeClosed(1L, 10L).boxed();
        List<Long> lists = longStream.collect(Collectors.toList());
        System.out.println("lists = " + lists);
        System.out.println(Arrays.toString(LongStream.rangeClosed(1L, 10L).toArray()));
        // LongStream.iterate()
    }
}
