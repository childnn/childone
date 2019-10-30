package org.anonymous.factory.store;

import org.anonymous.factory.pizza.ChicagoStyleCheesePizza;
import org.anonymous.factory.pizza.NYStyleVeggiePizza;
import org.anonymous.factory.pizza.Pizza;

public class ChicagoPizzaStore extends PizzaStore {

    @Override
    protected Pizza createPizza(String type) {
        if ("cheese".equals(type)) {
            return new ChicagoStyleCheesePizza();
        } else if ("veggie".equals(type)) {
            return new NYStyleVeggiePizza();
        } else {
            return null;
        }
    }
}
