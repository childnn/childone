package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/24 17:29
 */
public class FillingLists {
    public static void main(String[] args) {
        List<StringAddress> list = new ArrayList<>(
                Collections.nCopies(4, new StringAddress("Hello")));
        System.out.println(list);
        Collections.fill(list, new StringAddress("World!"));
        System.out.println(list);
    }
}

class StringAddress {

    private String s;

    StringAddress(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return super.toString() + " " + s;
    }

}
