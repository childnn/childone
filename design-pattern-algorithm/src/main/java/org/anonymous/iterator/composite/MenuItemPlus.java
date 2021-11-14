package org.anonymous.iterator.composite;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 15:50
 * 菜单项类: 这是组合类图里的 leaf.
 * 它实现组合内元素的行为.
 */
public class MenuItemPlus extends MenuComponent {
    private String name;
    private String description;
    private boolean vegetarian;
    private double price;

    /**
     * 菜单项目.
     * @param name 名称.
     * @param description 叙述.
     * @param vegetarian 是否素食.
     * @param price 价格.
     */
    public MenuItemPlus(String name, String description, boolean vegetarian, double price) {
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isVegetarian() {
        return vegetarian;
    }

    @Override
    public double getPrice() {
        return price;
    }

    /**
     * 覆盖 MenuComponent 类的 print() 方法,
     * 对菜单项来说, 此方法会打印完整的菜单项条目,
     * 包括: 名字, 描述, 价格以及是否为素食.
     */
    @Override
    public void print() {
        System.out.print(" " + getName());
        if (isVegetarian()) {
            System.out.print("(v)");
        }
        System.out.println(", " + getPrice());
        System.out.println("    -- " + getDescription());
    }

    @Override
    public Iterator<MenuComponent> createIterator() {
        return new NullIterator();
    }
}
