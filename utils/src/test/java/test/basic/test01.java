package test.basic;

/**
 * 第一题:
 * 定义一个字符串s = "Hello-World",利用API完成如下小需求
 * 1.判断字符串s,与字符串"World"是否相等,并打印出来.
 * 2.用程序得到字符串"Wo",在字符串s中的起始索引.
 * 3.得到s中,3号索引对应的字符,打印到控制台上
 * 4.得到s的长度,打印在控制台上.
 * 5.获得s中的"Hell"字符串,打印在控制台上.
 * 6.获得s中的"orld"字符串,打印在控制台上.
 * 7.将字符串s中的所有o替换为*号.打印在控制台上
 * 8.将字符串s切割成"Hello"和"World"两个字符串,打印在控制台上
 * 9.将字符串s变为字符数组,遍历数组将每个字符打印在控制台上
 */
class test01_ {

    public static void main(String[] args) {
        String s = "Hello-World";
        System.out.println(s.equals("World")); // false
        System.out.println(s.indexOf("Wo")); // 6
//        System.out.println(s.substring(6, 8)); // Wo
        System.out.println(s.charAt(3)); // l
        System.out.println(s.length()); // 11
        System.out.println(s.substring(0, 4)); // Hell
        System.out.println(s.substring(7)); //  orld : 到最后一个字符
//        System.out.println(s.substring(7, 11)); // orld
        System.out.println(s.replace('o', '*')); // Hell*-W*rld
        System.out.println(s.replace("o", "*")); // Hell*-W*rld
        String[] split = s.split("-"); // 切割，【返回值是数组】
        System.out.println(split); // 地址值
        for (int i = 0; i < split.length; i++) {
            System.out.print(split[i] + " "); // Hello World
        }
        System.out.println("==================");
        for (String s1 : split) {
            System.out.print(s1 + " ");
        }
        System.out.println("==================");
        for (int i = 0; i < s.toCharArray().length; i++) {
            System.out.print(s.toCharArray()[i] + " "); // H e l l o - W o r l d
        }


    }

}
