package org.anonymous.factory.store;

import org.anonymous.factory.npizza.Pizza0;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/30 14:11
 */
public abstract class PizzaStore0 {
    public Pizza0 orderPizza(String type) {
        Pizza0 pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    protected abstract Pizza0 createPizza(String type);
}
