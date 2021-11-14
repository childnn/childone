package test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/21 20:24
 * 这里只是一个哨兵示例, 看起来差别不大, 但运行效率还是会有差别
 * 主要应该注意此思想的精妙.
 * 该示例源自 <<计算机是怎样跑起来的>> 人民邮电出版社 Page: 96-97
 */
public class Sentinel {

    static List<Gene> geneList() {
        return Stream.generate(() -> new Gene(new Random().nextInt()))
                .limit(100).collect(Collectors.toList());
    }

    static void noSentinel() {
        boolean flag = false;
        List<Gene> list = geneList();

        int i = 0;
        do {
            Gene gene = list.get(i);
            if (gene.getI() == 55) {
                flag = true;
                // 每一次循环: 判断 i 是否小于 100
                // 判断是否找到期望的满足条件的数据
                // System.out.println("eureka: " + i);
                break;
            }
            i++;
        } while (i < 100);
        // System.out.println("flag = " + flag);
    }

    // 哨兵:
    static void sentinel() {
        boolean flag = false;
        List<Gene> list = geneList();

        int r = new Random().nextInt(); // 哨兵
        list.add(new Gene(r)); // 第 101 位定为 哨兵

        int i = 0;

        // 有了哨兵之后, 不需要判断每次循环的索引是否越界
        // 结果一定存在: 至少是哨兵, 如果最终结果为 哨兵
        // 即表示数据源中没有期望的值
        // 如果找到结果, 且不是哨兵, 则表示数据源存在期望的值
        while (true) {
            Gene gene = list.get(i);
            if (gene.getI() == r) {
                // System.out.println("eureka: " + r);
                if (i != 100) {
                    flag = true;
                }
                break;
            }
            i++;
        }
        // System.out.println("flag = " + flag);
    }

    @Test
    public void sen() {
        extracted(x -> noSentinel());
        extracted(x -> sentinel());
    }

    private void extracted(Consumer s) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            s.accept(null);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Gene {

    private int i;
}
