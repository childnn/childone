package org.anonymous.factory.test;

import org.anonymous.factory.pizza.Pizza;
import org.anonymous.factory.store.ChicagoPizzaStore;
import org.anonymous.factory.store.NYPizzaStore;
import org.anonymous.factory.store.PizzaStore;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 16:55
 */
public class PizzaTestDrive {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + pizza.getName() + "\n");

        pizza = chicagoStore.orderPizza("cheese");
        System.out.println("Joel ordered a " + pizza.getName() + "\n");
    }
}
