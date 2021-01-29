package stream;

import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws Exception {
       /* BufferedReader br = new BufferedReader(new FileReader("D:\\Develope\\BasicCode71\\myDay10\\src\\cn\\itheima\\InterfaceDemo01\\Demo01.java"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("interface.txt")); //D:\Develope\BasicCode71\myDay10\src\cn\itheima\InterfaceDemo01\Demo01.java
        String line;
        while (null != (line = br.readLine())) {
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        bw.close();
        br.close();*/
        System.out.println("重地".hashCode()); //1179395
        System.out.println("通话".hashCode()); //1179395
        System.out.println("重地".equals("通话")); //false
        System.out.println("重地".hashCode() == "通话".hashCode()); //true
        String s1 = "重地";
        String s2 = "通话";
        System.out.println(s1.hashCode() == s2.hashCode()); //true
        HashSet<String> set = new HashSet<>();
        set.add("重地");
        set.add("重地");
        set.add("通话");
        System.out.println(set);
    }
}
