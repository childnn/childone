package org.anonymous.generics;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/18 10:44
 */
public interface HasColor {
    java.awt.Color getColor();
}

class WithColor<T extends HasColor> {
    T item;

    WithColor(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    // The bound allows you to call a method:
    java.awt.Color color() {
        return item.getColor();
    }
}