package nio;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 23:02
 * **Tester** 使用了模板方法（Template Method）模式，它为匿名内部子类中定义的 `test()`
 * 的各种实现创建一个测试框架。每个子类都执行一种测试，因此 `test()` 方法还提供了执行各种I/O 活动的原型。
 * <p>
 * 虽然映射的写似乎使用 **FileOutputStream**，但是文件映射中的所有输出必须使用 **RandomAccessFile**，就像前面代码中的读/写一样。
 */
public class MappedIO {
    static String fileName = "temp.tmp";
    private static int numOfInts = 4_000_000;
    private static int numOfUbuffInts = 100_000;
    private static Tester[] tests = {
            new Tester("Stream Write") {
                @Override
                public void test() {
                    try (
                            DataOutputStream dos =
                                    new DataOutputStream(
                                            new BufferedOutputStream(
                                                    new FileOutputStream(fileName)))
                    ) {
                        for (int i = 0; i < numOfInts; i++)
                            dos.writeInt(i);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },
            new Tester("Mapped Write") {
                @Override
                public void test() {
                    try (
                            FileChannel fc = new RandomAccessFile(fileName, "rw").getChannel()
                    ) {
                        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                        for (int i = 0; i < numOfInts; i++)
                            ib.put(i);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },
            new Tester("Stream Read") {
                @Override
                public void test() {
                    try (
                            DataInputStream dis =
                                    new DataInputStream(
                                            new BufferedInputStream(
                                                    new FileInputStream(fileName)))
                    ) {
                        for (int i = 0; i < numOfInts; i++)
                            dis.readInt();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },
            new Tester("Mapped Read") {
                @Override
                public void test() {
                    try (
                            FileChannel fc = new FileInputStream(new File(fileName)).getChannel()
                    ) {
                        IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
                        while (ib.hasRemaining())
                            ib.get();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },
            new Tester("Stream Read/Write") {
                @Override
                public void test() {
                    try (
                            RandomAccessFile raf = new RandomAccessFile(fileName, "rw")
                    ) {
                        raf.writeInt(1);
                        for (int i = 0; i < numOfUbuffInts; i++) {
                            raf.seek(raf.length() - 4);
                            raf.writeInt(raf.readInt());
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },
            new Tester("Mapped Read/Write") {
                @Override
                public void test() {
                    try (
                            FileChannel fc = new RandomAccessFile(fileName, "rw").getChannel()
                    ) {
                        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                        ib.put(0);
                        for (int i = 1; i < numOfUbuffInts; i++)
                            ib.put(ib.get(i - 1));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
    };

    public static void main(String[] args) {
        Arrays.stream(tests).forEach(Tester::runTest);
    }

    private abstract static class Tester {
        private String name;

        Tester(String name) {
            this.name = name;
        }

        public void runTest() {
            System.out.print(name + ": ");
            long start = System.nanoTime();
            test();
            double duration = System.nanoTime() - start;
            System.out.format("%.3f%n", duration / 1.0e9);
        }

        public abstract void test();
    }

}
