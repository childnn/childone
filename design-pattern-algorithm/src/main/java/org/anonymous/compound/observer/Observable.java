package org.anonymous.compound.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/12 9:04
 */
public class Observable implements QuackObservable {
    private List<Observer> observers = new ArrayList<>();
    private QuackObservable duck;

    // 在此构造中, 传入 QuackObservable.
    // notify() 方法: 当通知发生时, 观察者把此对象传过去,
    // 好让观察者知道是那个对象在呱呱叫.
    public Observable(QuackObservable duck) {
        this.duck = duck;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        Iterator<Observer> iterator = observers.iterator();
        while (iterator.hasNext()) {
            Observer observer = iterator.next();
            observer.update(duck);
        }
    }

}
