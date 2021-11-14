package org.anonymous.decorate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 15:30
 */
public class StarbuzzCoffee {
    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        // 订一杯 Espresso, 不需要调料, 打印出它的描述与价钱.
        System.out.println(beverage.getDescription() + " $" + beverage.cost());

        // 制造出一个 DarkRoast 对象.
        Beverage beverage1 = new DarkRoast();
        beverage1 = new Mocha(beverage1); // 用 Mocha 装饰它.
        beverage1 = new Mocha(beverage1); // 用 第二个 Mocha 装饰它.
        beverage1 = new Whip(beverage1); // 用 Whip 装饰它.
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());

        // 最后, 再来一杯调料为豆浆、摩卡、奶泡的 HouseBlend 咖啡.
        Beverage beverage2 = new HouseBlend();
        beverage2 = new Soy(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());
    }
}
