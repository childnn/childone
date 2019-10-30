package org.anonymous.iterator;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 22:43
 */
public interface Menu {
    Iterator<MenuItem> createIterator();
}
