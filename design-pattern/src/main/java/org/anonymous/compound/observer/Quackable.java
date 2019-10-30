package org.anonymous.compound.observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 9:00
 * 必须确定所有实现 Quackable 的具体类都能够扮演 QuackObservable 的角色.
 * 我们需要在每一个类中实现 注册 和 通知.
 * 我们需要在另一个被称为 Observable 的类中封装注册和通知的代码, 然后将它和
 * QuackObservable 组合在一起. 这样, 我们只需要一份代码即可, QuackObservable
 * 所有的调用都委托给 Observable 辅助类.
 * ---
 * 我们有两类 Quackable, 一类是鸭子, 另一类是有鸭子叫声的东西:
 * {@link GooseAdapter} 适配器, {@link Flock} 组合, {@link QuackCounter} 装饰者.
 *
 */
public interface Quackable extends QuackObservable {
    void quack();
}
