package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/2 15:26
 */
public class CollectionUtil {

    private CollectionUtil() {
        // can't init.
    }

    /**
     * 应用场景, 在执行 oracle-in 操作时, in 的参数数量不可大于 1000,
     * 会报异常: oracle.jdbc.OracleDatabaseException: ORA-01795: 列表中的最大表达式数为 1000
     * 需要分组查询
     * 将集合按指定的大小分组
     * @param sources 源集合
     * @param pageSize 分组大小, 最后一组 <= pageSize
     * @param <T> the type
     * @return 分组后的集合
     */
    public static <T> List<List<T>> groupList(List<T> sources, int pageSize) {
        List<List<T>> result = new ArrayList<>();
        int size = sources.size();
        for (int i = 0; i < size; i += pageSize) {
            // 最后一次取值
            // if (i + pageSize > size) {
            //     pageSize = size - i;
            // }
            // 最后一次, i + pageSize >= size, subList-toIndex 取 size 即可
            result.add(sources.subList(i, Math.min(i + pageSize, size)));
        }
        return result;
    }

    public static <T> List<List<T>> groupListStream(List<T> sources, int pageSize) {
        int size = sources.size();
        int page = Math.floorDiv(size, pageSize) + 1; // 总页数

        return IntStream.rangeClosed(1, page) // 页数: 1, 2...page
                .boxed().map(i ->
                        sources.stream()
                                .skip((long) (i - 1) * pageSize) // 忽略已经分组的值
                                .limit(pageSize) // 如果最后一页数据量 < pageSize, 则取剩余的数据
                                .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
        // for (int i = 0; i < size; i += pageSize) {
        //     List<T> lis = sources.stream().skip(i)
        //             .limit(pageSize).collect(Collectors.toList());
        //     // 最后一次取值
        //     // if (i + pageSize > size) {
        //     //     pageSize = size - i;
        //     // }
        //     // 最后一次, i + pageSize >= size, subList-toIndex 取 size 即可
        //     result.add(lis);
        // }
        // return result;
    }


    public static void main(String[] args) {
        List<Double> data = Stream.generate(Math::random).limit(999999L).collect(Collectors.toList());

        long start2 = System.currentTimeMillis();
        List<List<Double>> lists1 = groupListStream(data, 900);
        System.out.println(System.currentTimeMillis() - start2);
        lists1.forEach(System.out::println);


        long start1 = System.currentTimeMillis();
        List<List<Double>> lists = groupList(data, 900);
        System.out.println(System.currentTimeMillis() - start1);
        lists.forEach(System.out::println);


    }

}
