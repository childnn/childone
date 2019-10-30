package stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 17:09
 */
public class FileToWordsBuilder {
    Stream.Builder<String> builder = Stream.builder();

    public FileToWordsBuilder(String filePath) throws Exception {
        Files.lines(Paths.get(filePath))
                .skip(1) // 略过开头的注释行
                .forEach(line -> {
                    for (String w : line.split("[ .?,]+"))
                        builder.add(w);
                });
    }

    public static void main(String[] args) throws Exception {
        new FileToWordsBuilder("Cheese.dat")
                .stream()
                .limit(7)
                .map(w -> w + " ")
                .forEach(System.out::print);
    }

    Stream<String> stream() {
        return builder.build();
    }
}
