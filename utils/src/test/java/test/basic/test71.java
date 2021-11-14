package test.basic;

import test.TPhone;

import java.util.ArrayList;

/**
 * 第一题:
 1.创建TPhone类，包含如下属性
 品牌
 价格
 使用年限
 2.在测试类中，利用【满参构造】创建4个对象，将【对象】存入【集合】中。
 华为-1200-4  苹果-9000-1  锤子-3000-3  小米-1800-2
 3.遍历集合，将使用年限小于2或价格低于2000的手机筛选出来。
 4.在控制台上打印所有筛选出来的对象（格式：华为-1200-4）
 */
public class test71 {

    public static void main(String[] args) {
        test.TPhone a = new TPhone("华为", 1200, 4);
        TPhone b = new TPhone("苹果", 9000, 1);
        TPhone c = new TPhone("锤子", 3000, 3);
        TPhone d = new TPhone("小米", 1800, 2);
        ArrayList<TPhone> list = new ArrayList<TPhone>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
//        for (Object o : list) {
//
//        }
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
            if (list.get(i).getAge() < 2 || list.get(i).getPrice() < 2000) {
                System.out.println(list.get(i).getName() + "-" + list.get(i).getPrice() + "-" + list.get(i).getAge());
            }
        }
    }

}
