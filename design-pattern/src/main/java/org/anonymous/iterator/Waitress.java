package org.anonymous.iterator;

import java.util.Iterator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 16:16
 */
public class Waitress {
    // 针对接口编程， 而不是针对实现类编程。
    // 减少 女招待和具体类之间的依赖。
    private Menu pancakeHouseMenu;
    // private PancakeHouseMenu pancakeHouseMenu;
    // private DinerMenu dinerMenu;
    private Menu dinerMenu;
    private Menu cafeMenu;

    // public Waitress(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu) {
    //     this.pancakeHouseMenu = pancakeHouseMenu;
    //     this.dinerMenu = dinerMenu;
    // }
    public Waitress(Menu pancakeHouseMenu, Menu dinerMenu, Menu cafeMenu) {
        this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
        this.cafeMenu = cafeMenu;
    }

    /**
     * 本方法 多次调用 {@link #printMenu(Iterator)} 方法,
     * 如果每次有新的菜单加入, 就必须加入更多的代码.
     * 算不算违反 "开闭原则".
     */
    public void printMenu() {
        Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
        Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
        Iterator<MenuItem> cafeIterator = cafeMenu.createIterator();

        System.out.println("MENU\n---\nBREAKFAST");
        printMenu(pancakeIterator);

        System.out.println("\nLUNCH");
        printMenu(dinerIterator);

        System.out.println("\nDINNER");
        printMenu(cafeIterator);
    }

    private void printMenu(Iterator<MenuItem> iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            System.out.print(menuItem.getName() + ", ");
            System.out.print(menuItem.getPrice() + " -- ");
            System.out.println(menuItem.getDescription());
        }
    }

    public static void main(String[] args) {
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        DinerMenu dinerMenu = new DinerMenu();
        CafeMenu cafeMenu = new CafeMenu();

        Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu, cafeMenu);
        waitress.printMenu();
    }
}
