package org.anonymous.iterator.composite;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/9 16:31
 */
public class WaitressPlus {
    private MenuComponent allMenus;

    public WaitressPlus(MenuComponent allMenus) {
        this.allMenus = allMenus;
    }

    public void printMenu() {
        allMenus.print();
    }

    public void printVegetarianMenu() {
        Iterator<MenuComponent> iterator = allMenus.createIterator();
        System.out.println("\nVEGETARIAN MENU\n---");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            try {
                /* 调用每个元素的 isVegetarian() 方法, 如果为 true, 就调用它的 print() 方法
                   菜单 的 isVegetarian() 方法本身就是继承自 MenuComponent, 无法正常调用.
                * */
                if (menuComponent.isVegetarian()) {
                    /* 只有菜单项的 print() 方法可以被调用, 绝对不能调用菜单(组合)的 print() 方法
                       其 print() 继承自 MenuComponent, 调用就会 抛异常.
                    *  */
                    menuComponent.print();
                }
            } catch (UnsupportedOperationException e) {
                //e.printStackTrace();
                // System.out.println("~~~");
            }
        }
    }
}
