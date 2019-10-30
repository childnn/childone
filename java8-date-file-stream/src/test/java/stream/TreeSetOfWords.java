package stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:18
 */
public class TreeSetOfWords {

    public static void main(String[] args) throws Exception {
        Set<String> words2 =
                // 把每一行作为一个元素生成流
                Files.lines(Paths.get("java8-date-file-stream/src/test/java/stream/TreeSetOfWords.java"))
                        // 把流中的每个元素按 单词 split
                        .flatMap(s -> Arrays.stream(s.split("\\W+")))
                        // 过滤全数字字符串
                        .filter(s -> !s.matches("\\d+")) // No numbers
                        .map(String::trim)
                        .filter(s -> s.length() > 2)
                        .limit(100)
                        .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(words2);
    }

}
