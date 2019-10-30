package org.anonymous.template.method;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 9:07
 * 关于模板方法中的 抽象方法和钩子.
 * -- 当子类 "必须" 提供算法中某个方法或步骤的实现时, 就使用抽象方法.
 * 如果算法的这个部分是可选的, 就使用钩子. 如果是钩子的话, 子类可以选择实现这个钩子,
 * 但并不强制这么做.
 */
public abstract class CaffeineBeverage { // 咖啡因饮料是一个抽象类.

    /**
     * 用同一个 prepareRecipe() 方法来处理茶和咖啡.
     * prepareRecipe() 被声明为 final, 因为我们不希望
     * 子类覆盖这个方法! 我们将步骤 2 和步骤 4 泛化为 brew()
     * 和 addCondiments().
     */
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        // 加上条件语句, 而该条件是否成立, 是由一个具体方法
        // customerWantsCondiments() 决定的. 如果顾客 "想要" 调料,
        // 只有这时我们才调用 addCondiments() 方法.
        if (customerWantsCondiments()) {
            addCondiments();
        }
        hook();
    }

    /**
     * 定义一个方法(通常)是空的默认实现.
     * 这个方法只会返回 true, 不做别的事.
     *
     * 这就是一个钩子, 子类可以覆盖这个方法,
     * 但不见得一定要这么做.
     * @return
     */
    protected boolean customerWantsCondiments() {
        return false;
    }

    /**
     * 可以由 “默认不做事的方法”, 我们称这种方法为 "hook"(钩子),
     * 子类可以视情况决定要不要覆盖它们.
     */
    protected void hook() {
    }

    /**
     * 因为咖啡和茶处理这些方法的做法不同,
     * 所以这两个方法必须被声明为抽象, 剩余的东西留给子类去操心.
     * 自行处理冲泡和添加调料部分.
     */
    protected abstract void brew();

    protected abstract void addCondiments();

    /**
     * 具体方法被定义在抽象类中, 将它声明为 final, 这样一来子类就无法
     * 覆盖它. 它可以被模板方法直接使用, 或者被子类使用.
     */
    protected final void pourInCup() {
        System.out.println("Pouring into cup");
    }


    protected final void boilWater() {
        System.out.println("Boiling water");
    }
}
