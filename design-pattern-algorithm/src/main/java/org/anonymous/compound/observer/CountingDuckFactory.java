package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 21:23
 * CountingDuckFactory 也扩展自抽象工厂.
 * 每个方法都会先用 叫声计数装饰者 将 Quackable 包装起来.
 * 模拟器并不知道有何不同, 只知道它实现了 Quackable 接口.
 * 但是, 巡逻员可以因此而放心, 所有的叫声都会被计算进去.
 */
public class CountingDuckFactory extends AbstractDuckFactory {

    @Override
    public Quackable createMallardDuck() {
        return new QuackCounter(new MallardDuck());
    }

    @Override
    public Quackable createRedheadDuck() {
        return new QuackCounter(new RedheadDuck());
    }

    @Override
    public Quackable createDuckCall() {
        return new QuackCounter(new DuckCall());
    }

    @Override
    public Quackable createRubberDuck() {
        return new QuackCounter(new RubberDuck());
    }
}
