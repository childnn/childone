package org.anonymous.factory.dependent;

import org.anonymous.factory.pizza.Pizza;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/30 9:23
 * 注: 当你直接实例化一个对象时, 就是在依赖它的具体类.
 * 这个依赖性很高的比萨店例子, 它由比萨店类来创建所有的比萨对象,
 * 而不是委托给工厂.
 *
 * 这个版本的 PizzaStore 依赖于所有的比萨对象, 因为它直接创建出这些比萨对象.
 * 因为对于比萨具体实现的任何改变都会影响到 PizzaStore. 我们说 PizzaStore
 * "依赖于" 比萨的实现. 如果这些类的实现改变了, 那么可能必须修改 PizzaStore.
 * 每新增一个比萨种类(一个 Pizza 的具体实现), 就等于让 PizzaStore 多了一个依赖.
 */
public class DependentPizzaStore {
    public Pizza createPizza(String style, String type) {
        Pizza pizza = null;
        if ("NY".equals(style)) {
            if ("cheese".equals(type)) {
                pizza = null; // 省略具体的 Pizza 实现类.
            } else {
                //...
            }
        } else if ("Chicago".equals(style)) {
            if ("cheese".equals(type)) {
                pizza = null; // ignore.
            } else {
                //..
            }
        } else {
            return null;
        }
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
