package org.anonymous.factory.ingredient;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/30 10:10
 * 原料工厂. -- 抽象工厂模式.
 * 抽象工厂的每个方法实际上看起来都像是工厂方法;
 * 每个方法都被声明成抽象, 而子类的方法覆盖这些方法来创建某些对象.
 *
 * 另: 如果需要加入新的产品(调料), 也就意味着需要改变改变接口,
 *  同时改变实现类 -- 或者使用 default 关键字.
 */
public interface PizzaIngredientFactory {
    default Dough createDough() {
        return null;
    }

    Sauce createSauce();

    Cheese createCheese();

    Veggies[] createVeggies();

    Pepperoni createPepperoni();

    Clams createClam();

}
