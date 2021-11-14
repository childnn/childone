package test.basic.te;

public class test001 {

    public static void main(String[] args) {
        String str = "wuhan wuxi wuhu javawu";
        // 遍历字符串
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
        }

        // 字符串转数组
        String[] arr = str.split(" ");
        // 数组输出
//        System.out.println(arr.toString()); // 地址
//        System.out.println(Arrays.toString(arr)); // 固定格式

//        System.out.println(arr[1].substring(0, 2).equals("wu")); // true
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].substring(0, 2).equals("wu")) {
                System.out.println(arr[i].replace("wu", "liu"));
//                System.out.println(arr[i]); // 数组本身没有变，replace 替换之后返回一个新字符串
            } else {
                System.out.println(arr[i]);
            }
        }
    }

}
