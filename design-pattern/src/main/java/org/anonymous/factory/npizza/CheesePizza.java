package org.anonymous.factory.npizza;

import org.anonymous.factory.ingredient.PizzaIngredientFactory;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/30 13:52
 *
 */
public class CheesePizza extends Pizza0 {
    private PizzaIngredientFactory ingredientFactory;

    /**
     * 制作比萨, 需要工厂提供原料. 所以每个比萨类都需要从构造方法中
     * 得到一个工厂, 并把这个工厂存储在一个实例变量中.
     *
     * @param ingredientFactory
     */
    public CheesePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    /**
     * 关键方法: 当需要原料时, 就跟工厂要.
     */
    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
    }
}
