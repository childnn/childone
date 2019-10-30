package org.anonymous.observer.java;

import org.anonymous.observer.display.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 9:46
 */
public class CurrentConditionDisplay implements Observer, DisplayElement {
    private Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionDisplay() {
    }

    /**
     * 现在构造方法需要一个 {@link Observable} 当参数, 并将 当前对象登记为 观察者.
     * @param observable
     */
    public CurrentConditionDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    /**
     * 在 update() 方法中, 先确定 "可观察者" 属于 WeatherData 类型,
     * 然后利用 getter 方法, 获取温度和湿度的测量值.
     * 然后调用 display() 方法.
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) o;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
    }
}
