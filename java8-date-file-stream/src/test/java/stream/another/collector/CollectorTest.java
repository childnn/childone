package stream.another.collector;

import org.junit.Test;
import stream.another.collector.domain.ReceiveBo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
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
        receiveBoList.add(new ReceiveBo("2019-08-31", new BigDecimal("200")));
        receiveBoList.add(new ReceiveBo("2019-08-30", new BigDecimal("101")));
        receiveBoList.add(new ReceiveBo("2019-08-31", new BigDecimal("201")));
        return receiveBoList;
    }

    /**
     * groupingBy + HashMap + 对数据求加法
     */
    @Test
    public void test02() {
        List<ReceiveBo> receiveBoList = prepareData();
        HashMap<String, BigDecimal> map = receiveBoList.stream().collect(Collectors.groupingBy(ReceiveBo::getReceiveTime,
                HashMap::new,
                Collectors.mapping(ReceiveBo::getReceiveAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        map.forEach((k, v) -> System.out.println(k + " --> " + v));
    }

}
