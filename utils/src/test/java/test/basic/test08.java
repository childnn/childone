package test.basic;

import java.util.ArrayList;

/**
 * 第八题:
 * 完成下列题目要求：
 * ①定义方法filter
 * 要求如下：
 * 参数：String[] arr，String  str
 * 返回值类型：String[]
 * 实现：【遍历arr】，将数组中 【包含参数str】 的元素存入【另一个 String 数组】 中并【返回】
 * PS：返回的数组长度需要用代码获取
 * ②在main方法中完成以下要求：
 * 定义一个String数组arr，数组元素有："itcast","itheima","baitdu","weixin","zhifubao"
 * 调用1中的filter方法传入arr数组和字符串”it”，输出返回的String数组中所有元素
 * 示例如下：
 * 输出的数组中的元素:
 * "itcast","itheima","baitdu"
 */
public class test08 {

    public static void main(String[] args) {

        String[] arr = {"itcast", "itheima", "baitdu", "weixin", "zhifubao"};
//        System.out.println(filter(arr, "it")); // 地址值 （返回的 String[] 的地址值）
//        filter(arr, "it");
        // 遍历 返回的 字符串数组
        for (int i = 0; i < filter(arr, "it").length; i++) {
            System.out.print(filter(arr, "it")[i] + ",");
        }

    }

    public static String[] filter(String[] arr, String str) {
        // 创建集合，(接收（与 str 相等的 字符串）（error），) 接收字符串数组 arr 中的【所有元素】
        ArrayList<String> list = new ArrayList<>();
        // 遍历 字符串数组，将数组中元素添加到集合 // 【可以用 contains 方法判断 字符串是否包含 str】
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        // 新建集合：用来接收【存在 str 子字符串的字符串】
        ArrayList<String> list1 = new ArrayList<>();
        // 获取集合中的每个元素（遍历），每个元素都是字符串，查看每个元素是否含有 str （字符串转字符数组？error）
        for (int i = 0; i < list.size(); i++) {
            // 【关键！！！】  判断每个字符串元素中是否包含 str 这个子字符串：int indexOf(String str)，若不存在该方法返回 - 1 // 【关键！！！】
            if (-1 != list.get(i).indexOf(str)) {
                // 如果返回值不等于 -1， 说明该字符串中含有 str ，将此字符串 存入 （另一个 字符串数组 str1，error）【新的集合】
                list1.add(list.get(i));
            }
        }
//        System.out.println(list1);  // 把这里注释解开，会输出三个 list1 的结果，是因为 main 方法中，for 循环使用了【filter(arr, "it");】三次（数组长度）
//        如果想这里 list1 只输出一遍，main 函数中先将（filter(arr, "it");）赋值调用，再遍历数组
        // 集合 lsit1 转 字符串数组：遍历集合，将集合中的元素赋值给 新建的字符串数组
        String[] str1 = new String[list1.size()];
        for (int i = 0; i < list1.size(); i++) {
            str1[i] = list1.get(i);
        }

        return str1;

        //error： 注意一下内容错误的原因 (======================================================================================)
        // 题中，“ 包含参数 str ”，不是指 String[] 数组中包含 str  字符串，而是指，数组中的 字符串元素 包含 str 字符串
       /* for (int i = 0; i < arr.length; i++) {
            if (arr[i] == str) {   // 【error】
                list.add(arr[i]);
            }
        }*/
        /*// 计数存在 str 子字符串的个数：得到数组长度
        int count = 0;
        // 新建 一个字符串数组：接收【存在 str 子字符串的字符串】 // 【error】 , 新建数组的长度无法确定，必须先建立集合来接收【存在str的字符串】，再通过转换
        String[] str1 = new String[count];*/ // 或者先 获得个数【count++】，再建立长度为（count）的字符串数组
        // (==========================================================================================================================)
    }

}
