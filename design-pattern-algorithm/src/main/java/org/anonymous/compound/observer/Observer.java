package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 9:10
 * 如果某个类实现了 Observer, 此类就可以观察 {@link Quackable} (Quackable 继承自 {@link QuackObservable}),
 * 任何 Quackable 都是 QuackObservable.
 * 当 quack() 被调用时, 它会收到通知.
 * 任何实现了 Observer 接口的类都可以观察鸭子. {@link Quackologist}
 */
public interface Observer {
    // 观察者: 传入正在呱呱叫的对象.
    void update(QuackObservable duck);
}
