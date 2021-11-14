package org.anonymous.iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 15:50
 */
public class MenuItem {
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
    public MenuItem(String name, String description, boolean vegetarian, double price) {
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public double getPrice() {
        return price;
    }
}
