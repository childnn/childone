package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 8:56
 * 被观察对象.
 * Observable 需要注册和通知观察者的方法.
 * 我们本来也需要删除观察者的方法, 但是在这里为了让实现保持简单,
 * 我们省略这部分了.
 */
public interface QuackObservable { // 任何向北观察的 Quackable 都必须实现该接口.
    // 注册观察者的方法, 任何实现了 Observer 接口的对象都可以监听呱嘎叫.
    void registerObserver(Observer observer);

    // 通知观察者的方法.
    void notifyObservers();

}
