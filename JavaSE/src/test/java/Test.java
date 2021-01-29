import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String s = "java woaijava,i like jajavava i enjoy java";
        String s1 = "java";
        //        System.out.println(s.indexOf(s1, s1.length()));
        StringBuilder sb = new StringBuilder(s);
        f(sb, s1);
        //        System.out.println(sb);
        Map map = new HashMap() {
            private static final long serialVersionUID = -1194505595760146047L;

            {
                put(null, null);
            }
        };
        //        System.out.println(map);
        ArrayList<String> list = new ArrayList<String>() {{
            add("a");
        }};
        for (Object a : list) {
            //            System.out.println(a);
        }
    }

    private static void f(StringBuilder s, String ss) {
        s.append(ss);
    }
}
