package io;

import java.io.IOException;
import java.io.StringReader;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 18:24
 */
public class MemoryInput {
    public static void main(String[] args) throws IOException {

        StringReader in = new StringReader(
                BufferedInputFile.read("java8-date-file-stream/src/test/java/io/MemoryInput.java"));
        int c;
        while ((c = in.read()) != -1)
            System.out.print((char) c);
    }
}
