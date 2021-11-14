package org.anonymous.factory.npizza;

import org.anonymous.factory.ingredient.PizzaIngredientFactory;
import org.anonymous.factory.ingredient.ny.NYPizzaIngredientFactory;
import org.anonymous.factory.store.PizzaStore0;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/30 14:00
 */
public class NYPizzaStore0 extends PizzaStore0 {
    @Override
    protected Pizza0 createPizza(String type) {
        Pizza0 pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();
        // 把工厂传递给每一个 pizza, 以便比萨能从工厂中取得原料.
        if ("cheese".equals(type)) {
            pizza = new CheesePizza(ingredientFactory);
            pizza.setName("New York Style Cheese Pizza");
        } else if ("veggie".equals(type)) {
            pizza = new VeggiePizza(ingredientFactory);
            pizza.setName("New York Style Veggie Pizza");
        } else if ("clam".equals(type)) {
            pizza = new ClamPizza(ingredientFactory);
            pizza.setName("New York Style Clam Pizza");
        } else if ("pepperoni".equals(type)) {
            pizza = new PepperoniPizza(ingredientFactory);
            pizza.setName("New York Style Pepperoni Pizza");
        }
        return pizza;
    }
}
