package org.anonymous.observer.display;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/28 22:40
 * 主题
 *
 */
public interface Subject {

    /**
     * 两个方法都需要一个观察者作为变量, 该观察者是用来注册或被删除的.
     * @param o 观察者.
     */
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    /**
     * 当主题改变时, 这个方法会被调用, 以通知所有的观察者.
     */
    void notifyObservers();
}
