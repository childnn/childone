package org.anonymous.iterator.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/9 17:08
 * 菜单类. 此组合类可以持有菜单项或其他菜单.
 * 有一些方法并未在 MenuComponent 类中实现, 比如
 * getPrice() 和 isVegetarian(), 因为这些方法对菜单而言并没有多大意义.
 * ---
 * 菜单可以有任意数目的孩子, 这些孩子都必须属于 MenuComponent 类型,
 * 我们使用内部的 ArrayList 记录它们.
 *
 */
public class MenuPlus extends MenuComponent {
    private List<MenuComponent> menuComponents = new ArrayList<>();
    private String name;
    private String description;

    /**
     * 与之前的实现不一样, 我们将给每个菜单一个名字和一个描述.
     * 以前, 每个菜单的类名称就是此菜单的名字.
     */
    public MenuPlus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent component) {
        menuComponents.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        menuComponents.remove(component);
    }

    @Override
    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 我们所要做的只是改变 print() 方法, 能让它不仅打印出
     * 菜单本身的信息, 也打印出菜单内所有组件的内容: 其他菜单和菜单项.
     * ---
     * 使用迭代器遍历所有菜单组件.
     * 遍历过程中, 可能遇到其他菜单, 或者是遇到菜单项.
     * 由于菜单和菜单项都实现了 print(), 那我们只需要调用
     * print() 即可.
     * ---
     * 在遍历期间, 如果遇到另一个菜单对象, 它的 print()
     * 方法会开始另一个遍历, 依次类推.
     */
    @Override
    public void print() {
        System.out.print("\n" + getName());
        System.out.println(", " + getDescription());
        System.out.println("-----------------------");

        Iterator<MenuComponent> iterator = menuComponents.iterator();
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            menuComponent.print();
        }

        // for (MenuComponent menuComponent : menuComponents) {
        //     menuComponent.print();
        // }
    }

    /**
     * 这里使用一个新的, 被称为 CompositeIterator 的迭代器.
     * 这个迭代器知道如何遍历任何组合.
     * 我们将目前组合的迭代器传入它的构造器.
     */
    @Override
    public Iterator<MenuComponent> createIterator() {
        return new CompositeIterator(menuComponents.iterator());
    }
}
