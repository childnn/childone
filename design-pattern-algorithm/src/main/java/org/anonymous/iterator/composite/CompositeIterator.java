package org.anonymous.iterator.composite;

import java.util.Iterator;
import java.util.Stack;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/10 8:46
 * 组合迭代器, 它的工作是遍历组件内的菜单项, 而且确保所有的子菜单(以及子子菜单...) 都被包括进来.
 */
public class CompositeIterator implements Iterator<MenuComponent> {

    private Stack<Iterator<MenuComponent>> stack = new Stack<>();

    public CompositeIterator(Iterator<MenuComponent> iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        /*
            想要知道是否还有下一个元素, 我们检查堆栈是否被清空, 如果已经空了, 就表示没有下一个元素了.
            否则, 我们就从堆栈的顶层中取出迭代器, 看看是否还有下一个元素.
            如果它没有元素, 我们将它弹出堆栈, 然后递归地调用 hasNext().
         */
        if (stack.isEmpty()) {
            return false;
        } else {
            Iterator<MenuComponent> iterator = stack.peek(); // peek 表示获取(查看)但不移除.
            if (!iterator.hasNext()) {
                stack.pop(); // 移除
                return hasNext();
            } else {
                return true;
            }
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(); // 不支持删除操作, 只有遍历.
    }

    /**
     * 好了, 当客户想要取得下一个元素地时候, 我们先调用 hasNext() 来确定是否还有下一个.
     * 如果还有下一个元素, 我们就从堆栈中取出目前的迭代器, 然后取得它的下一个元素.
     */
    @Override
    public MenuComponent next() {
        if (hasNext()) {
            Iterator<MenuComponent> iterator = stack.peek();
            MenuComponent component = iterator.next();
            /* 如果元素是一个菜单, 我们有了另一个需要被包含进遍历中的组合, 所以我们将它丢进
                堆栈中. 不管是不是菜单, 我们都返回该组件. */
            if (component instanceof MenuPlus) {
                stack.push(component.createIterator());
            }
            return component;
        } else {
            return null;
        }
    }

}
