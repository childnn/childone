package collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/25 20:50
 * 注意， **SortedSet** 表示“根据对象的比较函数进行排序”，而不是“根据插入顺序”。可以使用 **LinkedHashSet** 保留元素的插入顺序。
 */
public class SortedSetDemo {
    public static void main(String[] args) {
        SortedSet<String> sortedSet =
                Arrays.stream(
                        "one two three four five six seven eight"
                                .split(" "))
                        .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(sortedSet);
        String low = sortedSet.first();
        String high = sortedSet.last();
        System.out.println(low);
        System.out.println(high);
        Iterator<String> it = sortedSet.iterator();
        for (int i = 0; i <= 6; i++) {
            if (i == 3) low = it.next();
            if (i == 6) high = it.next();
            else it.next();
        }
        System.out.println(low);
        System.out.println(high);
        System.out.println(sortedSet.subSet(low, high));
        System.out.println(sortedSet.headSet(high));
        System.out.println(sortedSet.tailSet(low));
    }
}
