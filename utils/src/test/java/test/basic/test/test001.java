package test.basic.test;

public class test001 {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1 == s2); // true
        System.out.println(s1.equals(s2)); 	// true

        String s3 = new String("abc");
        String s4 = "abc";
        System.out.println(s3 == s4); // false
        System.out.println(s3.equals(s4));  // true

        String s5 = "a" + "b" + "c";
        String s6 = "abc";
        System.out.println(s5 == s6); // ???
        System.out.println(s5.equals(s6));	 // true

        String s7 = "ab";
        String s8 = "abc";
        String s9 = s7 + "c";
        System.out.println(s9 == s8); // ???
        System.out.println(s9.equals(s8)); // true

        String i = "a" + "b";
        String j = i + "c";
        String k = "abc";
        System.out.println(i == k); // false
    }
}
