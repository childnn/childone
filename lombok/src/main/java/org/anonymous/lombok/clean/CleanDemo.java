package org.anonymous.lombok.clean;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Cleanup#value() 释放资源的方法名; 方法不能又参数.
 * @since 2019/11/11 16:29
 */
public class CleanDemo {
    @SneakyThrows
    public static void main(String[] args) {
        @Cleanup
        // FileInputStream in = new FileInputStream("E:\\dev-code\\generator\\lombok\\src\\main\\java\\org\\anonymous\\lombok\\clean\\CleanDemo.java");
                BufferedReader reader = new BufferedReader(new FileReader("lombok\\src\\main\\java\\org\\anonymous\\lombok\\clean\\CleanDemo.java"));
        @Cleanup
        // FileOutputStream out = new FileOutputStream("E:\\dev-code\\generator\\lombok\\src\\main\\java\\org\\anonymous\\lombok\\clean\\out.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter("lombok\\\\src\\\\main\\\\java\\\\org\\\\anonymous\\\\lombok\\\\clean\\\\out.txt"));
        String read;
        while (null != (read = reader.readLine())) {
            writer.write(read);
            writer.newLine();
        }
        writer.flush();
    }

    public void test() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("lombok\\src\\main\\java\\org\\anonymous\\lombok\\clean\\CleanDemo.java"));

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("lombok\\\\src\\\\main\\\\java\\\\org\\\\anonymous\\\\lombok\\\\clean\\\\out.txt"));

                try {
                    String read;
                    while (null != (read = reader.readLine())) {
                        writer.write(read);
                        writer.newLine();
                    }
                    writer.flush();
                } finally {
                    if (Collections.singletonList(writer).get(0) != null) {
                        writer.close();
                    }
                }
            } finally {
                if (Collections.singletonList(reader).get(0) != null) {
                    reader.close();
                }
            }
        } catch (Throwable var14) {
            // throw var14;
        }
    }
}
