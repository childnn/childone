package org.anonymous.decorate;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 15:23
 * 摩卡是一个装饰者, 所以让它扩展自 CondimentDecorator.
 * 而 CondimentDecorator 本身就是一个 Beverage.
 */
public class Mocha extends CondimentDecorator {
    // 用一个实例变量记录饮料, 即被装饰者.
    Beverage beverage;

    // 想办法让被装饰者(饮料) 被记录到实例变量中.
    // 这里地做法是: 把饮料当作构造器地参数, 再由构造器将此饮料记录在实例变量中.
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    /**
     * 饮料的描述 + 调料的描述.
     */
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    /**
     * 调用委托给被装饰对象, 以计算价钱, 然后再加上 Mocha 的价钱, 得到最后结果.
     */
    @Override
    public double cost() {
        return .20D + beverage.cost();
    }
}

class Whip extends CondimentDecorator {

    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public double cost() {
        return .33D + beverage.cost();
    }
}

class Soy extends CondimentDecorator {

    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    @Override
    public double cost() {
        return .23D + beverage.cost();
    }
}