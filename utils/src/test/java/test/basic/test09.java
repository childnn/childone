package test.basic;

import java.util.ArrayList;

/**
 * 第九题:
 * a.定义方法public static ArrayList<String>  handleString(String [] arr，String str)；
 * 实现以下功能：
 * 遍历arr，将数组中包含参数str的元素,含有str的部分替换为*, 存入另一个集合中,将新集合返回；
 * b.在main方法中完成以下要求：
 * 1)定义一个String数组arr，数组元素有："beijing", "shanghai", "tianjin", "chongqing"；
 * 2)调用handleString方法传入arr数组和字符串”a”，输出返回的集合中所有元素；
 * <p>
 * 示例如下：
 * 控制台输出元素如下:
 * [sh*ngh*i,ti*njin]
 */
public class test09 {

    public static void main(String[] args) {
        String[] arr = {"beijing", "shanghai", "tianjin", "chongqing"};
        System.out.println(handleString(arr, "a"));
    }

    public static ArrayList<String> handleString(String[] arr, String str) {
        // 新建集合：用来接收存在 str 的字符串
        ArrayList<String> list = new ArrayList<>();
        // 替换 str 为 *
//        String replace = str.replace(str, "*");
        // 遍历arr
        for (int i = 0; i < arr.length; i++) {
            // 判断 arr 中各元素 是否 包含 str： 【contains】, 并将 str 替换为 "*"
            if (arr[i].contains(str)) {
                list.add(arr[i].replace(str, "*"));
            }
        }

        return list;
    }

}
