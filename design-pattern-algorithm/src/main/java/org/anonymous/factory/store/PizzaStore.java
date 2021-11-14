package org.anonymous.factory.store;

import org.anonymous.factory.pizza.ChicagoStyleCheesePizza;
import org.anonymous.factory.pizza.NYStyleVeggiePizza;
import org.anonymous.factory.pizza.Pizza;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 16:45
 *
 * 这是抽象创建者类. 它定义了一个抽象的工厂方法,
 * 让子类实现此方法制造产品.
 *
 * 创建者通常会包含依赖于抽象产品的代码, 而这些抽象产品由于子类制造.
 * 创建者不需要真的知道在制造哪种具体产品.
 */
public abstract class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    protected abstract Pizza createPizza(String type);
}

