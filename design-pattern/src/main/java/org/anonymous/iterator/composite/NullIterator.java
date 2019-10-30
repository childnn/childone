package org.anonymous.iterator.composite;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 8:50
 * 空迭代器, 菜单项 内没什么可以遍历的, 我们要如何实现 菜单项的 createIterator() 方法呢?
 * 有两种选择:
 * -- 返回null. 让客户代码判断返回值是否为 null.
 * -- 返回一个迭代器, 而这个迭代器的 hasNext() 方法永远返回 false.
 *
 */
public class NullIterator implements Iterator<MenuComponent> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public MenuComponent next() {
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
