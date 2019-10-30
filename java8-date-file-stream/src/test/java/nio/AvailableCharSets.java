package nio;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 14:29
 */
public class AvailableCharSets {
    public static void main(String[] args) {
        SortedMap<String, Charset> charSets = Charset.availableCharsets();

        for (String csName : charSets.keySet()) {
            System.out.print(csName);
            Iterator aliases = charSets.get(csName).aliases().iterator();
            if (aliases.hasNext())
                System.out.print(": ");

            while (aliases.hasNext()) {
                System.out.print(aliases.next());
                if (aliases.hasNext())
                    System.out.print(", ");
            }
            System.out.println();
        }
    }
}
