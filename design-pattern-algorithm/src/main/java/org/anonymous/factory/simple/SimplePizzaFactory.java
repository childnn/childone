package org.anonymous.factory.simple;

import org.anonymous.factory.pizza.Pizza;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 17:21
 *
 * 简单工厂方法.
 */
public class SimplePizzaFactory {
    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if ("cheese".equals(type)) {
            pizza = null; // ...something...
        } else {
            // others..
        }
        return pizza;
    }
}
