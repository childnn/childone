package test.basic;

import java.util.ArrayList;

/**
 * 第十二题:
 * 根据要求完成以下功能：
 * a.定义ArrayList集合，存入如下整数：
 * 11，22， 55，66， 77 , 88
 * b.遍历集合,删除大于60的元素,在控制台打印输出删除后的集合中所有元素
 */
public class test712 {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(22);
        list.add(55);
        list.add(66);
        list.add(77);
        list.add(88);
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i) > 60) {
//                list.remove(i);
//                i--; // 集合长度可变，没删除一个，长度减小一个，被删除元素之后的元素索引都会变小一个：因此，想要获得 被删除元素 后一个的大小，必须 i--
//            }
//        }
//        for (IntegerClass integer : list) {
//            System.out.println(integer);
//        }
        for (int i = list.size() - 1; i >= 0; i--) { // 直接使用反向遍历，排除了 list.size()  的干扰
            if (list.get(i) > 60) {
                list.remove(i);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
