package test.basic;

import java.util.ArrayList;

/**
 * 第五题:
 a.定义"电动汽车类"Ecar,包含:
 1)构造方法: 空参构造、满参构造；
 2)成员变量:
 品牌 brand（String类型）
 价格price（int类型）
 续航mile（int类型）；
 3)成员方法:所有成员变量的set/get方法；

 b.定义测试类EcarTest,完成以下需求:
 1)定义public static ArrayList<Ecar> filter(ArrayList<Ecar> list,int price ,int mile) 方法,
 要求:
 遍历list集合，将list中价格[低于]参数price,并且续航[不低于]参数mile的元素存入到另一个ArrayList<Ecar> 中并返回；

 2）定义main(String[] args)方法,要求:
 根据以下内容创建并初始化3个Ecar对象,
 {"威马EX5",179800,400},{"蔚来ES8", 448000,500},{"特斯拉ModelX",828000,552}，

 创建一个ArrayList<Ecar> list_ecar，将上面的3个Ecar对象添加到list_ecar中，
 调用filter方法传入list_ecar,500000和400，根据返回的list集合输出所有元素信息；
 示例如下:
 威马EX5 179800元 400公里
 蔚来ES8 448000元 500公里
 */
public class EcarTest {

    public static void main(String[] args) {
        Ecar car1 = new Ecar("威马EX5",179800,400);
        Ecar car2 = new Ecar("蔚来ES8", 448000,500);
        Ecar car3 = new Ecar("特斯拉ModelX",828000,552);
        ArrayList<Ecar> listEcar = new ArrayList<>();
        listEcar.add(car1);
        listEcar.add(car2);
        listEcar.add(car3);
        ArrayList<Ecar> filter = filter(listEcar, 500000, 400);
        for (int i = 0; i < filter.size(); i++) {
            System.out.println(filter.get(i).getBrand() + " " + filter.get(i).getPrice() + " " + filter.get(i).getMile());
        }
    }

    public static ArrayList<Ecar> filter(ArrayList<Ecar> list, int price, int mile) {
        ArrayList<Ecar> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            if (list.get(i).getPrice() < price && list.get(i).getMile() >= mile) {
                list1.add(list.get(i));
            }
        }
        return list1;
    }

}
