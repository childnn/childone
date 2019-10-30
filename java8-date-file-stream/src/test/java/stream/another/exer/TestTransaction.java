package stream.another.exer;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestTransaction {
    List<Transaction> transactions = null;

    /**
     * @param str 就是 trader name.
     */
    public static Stream<String> filterCharacter(String str) {
        List<String> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch.toString());
        }

        return list.stream();
    }

    @Before
    public void before() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950));
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
    @Test
    public void test1() {
        transactions.stream().filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue)).forEach(System.out::println);
    }

    //2. 交易员都在哪些不同的城市工作过？
    @Test
    public void test2() {
        transactions.stream().map(t -> t.getTrader().getCity()).distinct() // .collect(Collectors.toSet())
                .forEach(System.out::println);
    }

    //3. 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test3() {
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader).sorted(Comparator.comparing(Trader::getName)/*.reversed()*/).distinct()
                .forEach(System.out::println);
    }

    //4. 返回所有交易员的姓名字符串，按字母顺序排序
    @Test
    public void test4() {
        transactions.stream().map(t -> t.getTrader().getName())
                .flatMap(/*new Function<String, Stream<String>>() {
                @Override
                public Stream<String> apply(String s) {
                    return null;
                }
            }*/TestTransaction::filterCharacter)
                .sorted(String::compareToIgnoreCase/*String.CASE_INSENSITIVE_ORDER*/).forEach(System.out::print);
    }

    //5. 有没有交易员是在米兰工作的？
    @Test
    public void test5() {
        boolean anyMatch = transactions.stream()
                .anyMatch(t -> t.getTrader().getCity().equals("Milan"));
        System.out.println(anyMatch);
    }

    //6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6() {
        Integer sum = transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.summingInt(Transaction::getValue)); // .mapToInt(Transaction::getValue).sum();
        System.out.println(sum);

        Optional<Integer> optional = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge")).map(Transaction::getValue)
                .reduce(Integer::sum);
        System.out.println(optional.get());
    }

    // 注: 注意 test7, test8 的返回值.
    //7. 所有交易中，最高的交易额是多少 -- 先 map 再 比较.
    @Test
    public void test7() {
        Optional<Integer> max = transactions.stream().map(Transaction::getValue).max(/*Comparator.comparingInt(v -> v)*/Integer::compare);
        System.out.println(max.get());
    }

    //8. 找到交易额最小的交易 -- 直接比较值, 获取对象.
    @Test
    public void test8() {
        Optional<Transaction> min = transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue));
        System.out.println(min.get());
    }

}
