package stream.function;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 12:43
 */
public class FileToWords {
    public static Stream<String> stream(String filePath) throws Exception {
        return Files.lines(Paths.get(filePath))
                .skip(1) // First (comment) line
                // .flatMap(line -> Arrays.stream(line.split("\\W+"))))
                .flatMap(line -> Pattern.compile("\\W+").splitAsStream(line));
    }
}
