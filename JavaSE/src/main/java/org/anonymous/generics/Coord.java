package org.anonymous.generics;

interface Weight {
    int weight();
}

// This fails. Class must be first, then interfaces:
// class WithColorCoord<T extends HasColor & Coord> {

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/18 10:45
 */
public class Coord {
    public int x, y, z;
}

// Multiple bounds:
class WithColorCoord<T extends Coord & HasColor> {
    T item;

    WithColorCoord(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    java.awt.Color color() {
        return item.getColor();
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }
}

// As with inheritance, you can have only one
// concrete class but multiple interfaces:
class Solid<T extends Coord & HasColor & Weight> {
    T item;

    Solid(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    java.awt.Color color() {
        return item.getColor();
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }

    int weight() {
        return item.weight();
    }
}

class Bounded
        extends Coord implements HasColor, Weight {
    @Override
    public java.awt.Color getColor() {
        return null;
    }

    @Override
    public int weight() {
        return 0;
    }
}


class HoldItem<T> {
    T item;

    HoldItem(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }
}

class WithColor2<T extends HasColor>
        extends HoldItem<T> {
    WithColor2(T item) {
        super(item);
    }

    java.awt.Color color() {
        return item.getColor();
    }
}

class WithColorCoord2<T extends Coord & HasColor>
        extends WithColor2<T> {
    WithColorCoord2(T item) {
        super(item);
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }
}

class Solid2<T extends Coord & HasColor & Weight>
        extends WithColorCoord2<T> {
    Solid2(T item) {
        super(item);
    }

    int weight() {
        return item.weight();
    }
}
