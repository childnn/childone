package stream.partition;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/16 18:01
 */
public class PartitionTest {

    @Test
    public void testPartition() {
        // partitioningBy 会无条件分区, 即使数据源为空, 分区结果也会分两个区(均为空集合), 在使用的时候注意判断
        Map<Boolean, List<Object>> collect = Collections.emptyList().stream().collect(Collectors.partitioningBy(Objects::isNull));
        System.out.println("collect = " + collect);
    }

}
