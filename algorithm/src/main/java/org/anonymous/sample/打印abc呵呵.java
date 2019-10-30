package org.anonymous.sample;

import java.util.ArrayList;
import java.util.List;

public class 打印abc呵呵 {
    public static void main(String[] args) {
        String str = "abc";
        List<String> result = new ArrayList<String>();
        getAllLists(result, str, "");
        for (String each : result) {
            System.out.println(each);
        }
    }

    /**
     *   
     *  @param base 以该字符串作为基础字符串，进行选择性组合。  
     *  @param buff 所求字符串的临时结果  
     *  @param result 存放所求结果  
     */
    public static void getAllLists(List<String> result, String base, String buff) {
        if (base.length() <= 0) {
            result.add(buff);
        }
        for (int i = 0; i < base.length(); i++) {
            getAllLists(result, new StringBuilder(base).deleteCharAt(i).toString(),
                    buff + base.charAt(i));
        }
    }
}
