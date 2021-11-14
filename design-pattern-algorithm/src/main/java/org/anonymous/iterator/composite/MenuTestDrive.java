package org.anonymous.iterator.composite;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/9 21:11
 */
public class MenuTestDrive {
    public static void main(String[] args) {
        MenuComponent pancakeHouseMenu = new MenuPlus("PANCAKE HOUSE MENU", "Breakfast");
        MenuComponent dinerMenu = new MenuPlus("DINER MENU", "Lunch");
        MenuComponent cafeMenu = new MenuPlus("CAFE MENU", "Dinner");
        MenuComponent dessertMenu = new MenuPlus("DESSERT MENU", "Dessert of course!");

        // 使用组合的 add() 方法, 将每个 菜单 都加入到顶层菜单 all menus 中.
        MenuComponent allMenus = new MenuPlus("ALL MENUS", "ALL menus combined");

        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);

        // 添加菜单项.
        pancakeHouseMenu.add(new MenuItemPlus("K&B Pancake Breakfast", "Pancakes with scrambled eggs, and toast", true, 2.99));
        pancakeHouseMenu.add(new MenuItemPlus("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", false, 2.99));
        pancakeHouseMenu.add(new MenuItemPlus("Blueberry Pancakes", "Pancakes made with fresh blueberries, and blueberry syrup", true, 3.49));
        pancakeHouseMenu.add(new MenuItemPlus("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59));

        dinerMenu.add(new MenuItemPlus("Vegetarian BLT", "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99));
        dinerMenu.add(new MenuItemPlus("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99));
        dinerMenu.add(new MenuItemPlus("Soup of the day", "A bowl of the soup of the day, with a side of potato salad", false, 3.29));
        dinerMenu.add(new MenuItemPlus("Hotdog", "A hot dog, with saurkraut, relish, onions, topped with cheese", false, 3.05));
        dinerMenu.add(new MenuItemPlus("Steamd Veggies and Brown Rice", "Steamd vegetables over brown rice", true, 3.99));
        dinerMenu.add(new MenuItemPlus("Pasta", "Spaghetti with Marinara Saure, and a slice of sourdough bread", true, 3.89));
        // ... 其他菜单项.

        // 然后我们也在菜单中加入另一个菜单.
        // 由于菜单和菜单项都是 MenuComponent, 所以菜单可以顺利地被加入.
        dinerMenu.add(dessertMenu); // 当打印所有餐厅菜单组件时, 新地甜点菜单也一起打印出来.

        // 在甜点菜单上加了 苹果派.
        dessertMenu.add(new MenuItemPlus("Apple Pie", "Apple pie with a flakey crust, topped with vanilla ice cream", true, 1.59));
        dessertMenu.add(new MenuItemPlus("Cheesecake", "Creamy New York cheesecake, with a chocolate graham crust", true, 1.99));
        dessertMenu.add(new MenuItemPlus("Sorbet", "A scoop of raspberry and a scoop of lime", true, 1.89));
        // 其他菜单项...

        cafeMenu.add(new MenuItemPlus("Veggie Burger and Air Fries", "Veggie burger on a whole wheat bun, lettuce, tomato, and fries", true, 3.99));
        cafeMenu.add(new MenuItemPlus("Soup of the day", "A cup of the soup of the day, with a side salad", false, 3.69));
        cafeMenu.add(new MenuItemPlus("Burrito", "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29));

        // 一旦我们将整个菜单层次构造完毕, 把它整个交给女招待, 你会发现,
        // 女招待要将整份菜单打印出来, 简直就是易如反掌.
        WaitressPlus waitress = new WaitressPlus(allMenus);
        // waitress.printMenu();
        waitress.printVegetarianMenu();

    }
}
