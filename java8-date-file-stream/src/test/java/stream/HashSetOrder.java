package stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/31 9:23
 */
public class HashSetOrder {
    @Test
    public void test() {
        List<String> wordList = Arrays.asList("this", "is", "a", "stream", "of", "strings");
        HashSet<String> words = new HashSet<>(wordList);
        HashSet<String> words2 = new HashSet<>(words);

        // 通过添加，删除足够多的元素来强制进行 rehashing
        IntStream.rangeClosed(0, 50).forEachOrdered(i -> words2.add(String.valueOf(i)));
        words2.retainAll(wordList);

        // 相等，但具有不同元素排序
        System.out.println(words.equals(words2));
        System.out.println("before: " + words);
        System.out.println("after: " + words2);
    }
}
