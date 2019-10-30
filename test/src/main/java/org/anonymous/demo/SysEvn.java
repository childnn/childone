package org.anonymous.demo;

import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 11:20
 */
public class SysEvn {

    public static void main(String[] args) {
        Map<String, String> envMap = System.getenv();
        //envMap.forEach((k, v) -> System.out.println(k + ": " + v));
        for (Map.Entry<String, String> entry : envMap.entrySet()) {
            System.out.println(String.format("%s = %s", entry.getKey(), entry.getValue()));
        }
    }
}
