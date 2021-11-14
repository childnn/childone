package test.basic.te;

/**
 * 字符串： String str = "wuhan wuxi wuhu javawu"
 * 判断每个单词的的是否以 "wu" 开头，若是，将 "wu" 替换为 "liu"
 */
public class test000 {

    public static void main(String[] args) {
        String str = "wuhan wuxi wuhu javawu";
        String[] arr = str.split(" ");
        System.out.println(str);
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            if (s.startsWith("wu")) {
                System.out.println(s.replace("wu", "liu"));

            } else {
                System.out.println(arr[i]);
            }
        }
    }

}
