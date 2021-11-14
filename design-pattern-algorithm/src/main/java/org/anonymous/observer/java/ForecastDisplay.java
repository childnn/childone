package org.anonymous.observer.java;

import org.anonymous.observer.display.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 9:13
 * @see Observer#update(Observable, Object)
 *  具体的观察者所定义的 update() 方法需要做出一些改变, 但是基本上还是一样的想法:
 *  有一个共同的 Observer 接口, 提供了一个被主题调用的 update() 方法.
 */
public class ForecastDisplay implements Observer, DisplayElement {
    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void display() {

    }
}
