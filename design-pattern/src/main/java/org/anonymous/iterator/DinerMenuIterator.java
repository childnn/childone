package org.anonymous.iterator;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 16:07
 */
public class DinerMenuIterator implements Iterator<MenuItem> {
    private MenuItem[] items;
    private int position = 0; // position 记录当前数组遍历的位置.

    public DinerMenuIterator(MenuItem[] items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        // 使用固定长度数组, 不但要检查是否超出了数组长度, 也必须检查是否下一项是 null, 如果是
        // 就表示没有其他项了.
        return position < items.length && items[position] != null;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = items[position];
        position++;
        return menuItem;
    }

    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException("You can't remove an item until you've done at least one next()");
        }
        if (items[position - 1] != null) {
            for (int i = position - 1; i < items.length - 1; i++) {
                items[i] = items[i + 1];
            }
            items[items.length - 1] = null;
        }
    }
}
