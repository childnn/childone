package stream.another.collector;

import org.junit.Test;
import stream.another.collector.domain.ReceiveBo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorTest {

    /**
     * groupingBy + TreeMap
     */
    @Test
    public void test01() {
        List<ReceiveBo> receiveBoList = prepareData();
        TreeMap<String, List<ReceiveBo>> map = receiveBoList.stream()
                .collect(Collectors.groupingBy(ReceiveBo::getReceiveTime, TreeMap::new, Collectors.toList()));
        map.forEach((k, v) -> System.out.println(k + " --> " + v));
    }

    private List<ReceiveBo> prepareData() {
        List<ReceiveBo> receiveBoList = new ArrayList<>();
        receiveBoList.add(new ReceiveBo("2019-08-30", new BigDecimal("100")));
        receiveBoList.add(new ReceiveBo("2019-08-30", new BigDecimal("123")));
        receiveBoList.add(new ReceiveBo("2019-08-30", new BigDecimal("101")));

        receiveBoList.add(new ReceiveBo("2019-08-31", new BigDecimal("200")));
        receiveBoList.add(new ReceiveBo("2019-08-31", new BigDecimal("321")));
        receiveBoList.add(new ReceiveBo("2019-08-31", new BigDecimal("201")));
        return receiveBoList;
    }

    /**
     * groupingBy + HashMap + 对数据求加法
     * @see #test03()
     */
    @Test
    public void test02() {
        List<ReceiveBo> receiveBoList = prepareData();
        HashMap<String, BigDecimal> map = receiveBoList.stream().collect(Collectors.groupingBy(ReceiveBo::getReceiveTime,
                HashMap::new, // 参数二默认就是 hashmap, 可以省略
                Collectors.mapping(/*Function.identity()*/ReceiveBo::getReceiveAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        map.forEach((k, v) -> System.out.println(k + " --> " + v));
    }

    /**
     * @see #test02()
     */
    @Test
    public void test03() {
        Map<String, String> collect = prepareData()
                .stream().collect(
                        Collectors.groupingBy(
                                // 时间分组
                                ReceiveBo::getReceiveTime,
                                Collectors.mapping(
                                        // 这个 identity 就是 ReceiveBo 本身: 注意于 test02
                                        Function.identity(),
                                        Collectors.reducing("初始值",
                                                // 参数二: Collectors.mapping( 的第一个参数值: identity
                                                o -> o.getReceiveTime() + "------" + o.getReceiveAmount(),

                                                // 这个计算就是表示把 Collectors.reducing( 前俩参数做的处理
                                                // 参数一:
                                                //      第一次循环 就是 Collectors.reducing( 的第一个参数值
                                                //      以后每一次, 都是 本组 (前面分组后的一组数据) 前面经过
                                                //      Collectors.reducing( 第三个参数 BinaryOperator 计算的结果
                                                // 参数二: Collectors.reducing( 的第二个参数值---本次循环的当前值
                                                (o1, o2) -> {
                                                    System.out.println("o1 = " + o1);
                                                    System.out.println("o2 = " + o2);
                                                    return o1 + o2;
                                                })
                                )
                        )
                );
        collect.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
