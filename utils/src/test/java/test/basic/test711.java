package test.basic;

import java.util.ArrayList;
import java.util.Random;

/**
 * 第十一题:
 * 分析一下需求,并用代码实现
 * 1.创建一个储存整数的集合,随机产生10个[两位数]存入集合
 * 2.定义一个方法,将集合传入,筛选出其中所有小于50的元素,存入新的集合中,将新集合返回
 * 3.在主方法中,调用2中的方法,将1中的集合传入,得到返回的集合,将返回的集合遍历打印.
 */
public class test711 {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Random r = new Random();
        int count = 0;
        while (true) {
            int i = r.nextInt(100);
            if (i > 9) {
                count++;
                list.add(i);
                if (10 == count) {
                    break;
                }
            }
        }
        System.out.println(list);
        ArrayList<Integer> list1 = smallList(list);
        for (int i = 0; i < list1.size(); i++) {
            System.out.print(list1.get(i) + " ");
        }
    }

    public static ArrayList<Integer> smallList(ArrayList<Integer> list) {
        ArrayList<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < 50) {
                list1.add(list.get(i));
            }
        }
        return list1;
    }

}
