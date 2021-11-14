package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 14:22
 */
public class MallardDuck implements Quackable {
    // 每个 Quackable 都有一个 Observable 实例变量.
    private Observable observable;

    public MallardDuck() {
        observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("Quack");
        // 当我们呱呱叫时, 需要让观察者知道.
        notifyObservers();
    }

    // 把两个 QuackObservable 方法委托给辅助类进行.
    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }

    @Override
    public String toString() {
        return "Mallard Duck just quacked.";
    }
}
