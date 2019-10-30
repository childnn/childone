package org.anonymous.set;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author child
 * 2019/6/19 8:43
 */
public class SetTest {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("org.anonymous.demo");
        logger.setLevel(Level.FINEST);

        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        Logger.getLogger("org.anonymous.demo").addHandler(handler);

        Set<Item> parts = new HashSet<>();
        parts.add(new Item("Toaster", 1279));
        parts.add(new Item("Microwave", 4104));
        parts.add(new Item("Toaster", 1279));

        System.err.println(parts);
    }
}
