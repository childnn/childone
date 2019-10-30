package org.anonymous.validating;

import java.util.ArrayList;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 19:18
 */
public class CountedList extends ArrayList<String> {
    private static int counter = 0;
    private int id = counter++;

    public CountedList() {
        System.out.println("CountedList #" + id);
    }

    public int getId() {
        return id;
    }
}
