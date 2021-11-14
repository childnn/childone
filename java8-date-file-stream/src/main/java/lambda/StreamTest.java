package lambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/28 14:08
 */
public class StreamTest {

    // bingRenYzList.stream().collect(Collectors.groupingBy(YZ_BingRenYz::getShuJuLyId))
    //                      .entrySet().stream().forEach(entry->{
    //                  HashMap<String, BigDecimal> hashMap = MapUtil.newHashMap();
    //                  hashMap.put(entry.getKey(),entry.getValue().stream()
    //                  .map(YZ_BingRenYz::getYiCiJl).reduce(BigDecimal.ZERO, BigDecimal::add));
    //                  list.add(hashMap);
    //              });
    @Test
    public void test() {
        // @SuppressWarnings("serial")
        // noinspection serial
        List<Yz> list = new ArrayList<Yz>() {{
            add(build("1", 1));
            add(build("1", 2));
            add(build("2", 3));
            add(build("2", 4));
            add(build("3", 5));
        }};

        // noinspection 可以压制警告
        // 'for' loop replaceable with enhanced 'for'
        // noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        Map<String, BigDecimal> result = list.stream()
                .collect(Collectors.toMap(Yz::getShuJuLYId, Yz::getYiCiJl, BigDecimal::add));
        System.out.println("result = " + result);

        Map<String, BigDecimal> r = new HashMap<>();
        list.stream()
                .collect(Collectors.groupingBy(Yz::getShuJuLYId))
                .forEach((key, value) ->
                        r.put(key, value
                                .stream()
                                // .parallel()
                                // 如果 stream 中的元素与结果元素同类型: 即与 reduce 参数一 同类型
                                // 可以使用 java.util.stream.Stream.reduce(T, java.util.function.BinaryOperator<T>)
                                // 参见: stream.LastElement.main 中的示例
                                // 如果是不同类型, 就使用三个参数的 reduce, 使用 reduce-参数二实现类型转换
                                // reduce-参数三: 只有在使用 parallel() 才有用
                                .reduce(BigDecimal.ZERO, (bigDecimal, yz) -> bigDecimal.add(yz.getYiCiJl()),
                                        new BinaryOperator<BigDecimal>() {
                                    @Override
                                    public BigDecimal apply(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
                                        System.out.println("bigDecimal = " + bigDecimal);
                                        return null;
                                    }
                                })));
        System.out.println("r = " + r);
    }

    Yz build(String s, long b) {
        return Yz.builder().shuJuLYId(s).yiCiJl(BigDecimal.valueOf(b)).build();
    }


    @Test
    public void test1() {
        System.out.println(String.join(",", "1"));
        System.out.println(String.join(",", "1", null));
    }

}

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
class Yz {

    private String shuJuLYId;
    private BigDecimal yiCiJl;

}
