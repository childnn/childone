package org.anonymous.observer.java;

import java.util.Observable;
import java.util.Observer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see Observable 类追踪所有的观察者, 并通知它们.
 * Observable 是一个 "类", 而不是一个 接口, 所以 WeatherData 扩展了
 * Observable 主题(等同于 {@link org.anonymous.observer.display.Subject}).
 * @see Observable#addObserver(Observer)
 * @see Observable#deleteObserver(Observer)
 * @see Observable#notifyObservers()
 * @see Observable#setChanged()
 * 注: 必须将 {@link Observable#setChanged()} 设置为 true,
 * 观察者 {@link Observer} 才会被通知.
 * 这样做, 可以使通知有更多的弹性, 比如在满足特定条件的时候将 {@link Observable#setChanged()} 设置为 true,
 * 不至于 主题 状态一改变, 就通知 观察者.
 *
 * 子类 {@link WeatherData} 继承父类 {@link Observable} 的行为(方法).
 * 我们不需要追踪观察者了, 也不需要管理注册与删除(让超类代劳即可).
 * 所以我们把注册、添加、通知的相关代码删除。
 * 不再需要通过 构造方法 "记住" 观察者们了.
 *
 *
 * 1. 如何把对象变成观察者?
 * 如 display 包中的一样, 实现观察者接口 {@link Observer}, 然后调用任何
 * {@link Observable} 对象的 {@link Observable#addObserver(Observer)} 方法.
 * 不想再当观察者时, 调用 {@link Observable#deleteObserver(Observer)} 方法就可以了.
 *
 * 2. "可观察者" 要如何推送通知?
 * 首先, 利用扩展 {@link Observable} 产生 "可观察者" 类, 然后, 需要两个步骤:
 * 2.1. 先调用 {@link Observable#setChanged()} 方法, 标记状态已经改变的事实;
 * 2.2. 然后调用两种 {@link Observable#notifyObservers()} 或 {@link Observable#notifyObservers(Object)}
 * 有参的方法是把 参数传递给 {@link Observer#update(Observable, Object)} 方法的第二个参数.
 * 3. 观察者如何接收通知?
 * 同以前一样, 观察者实现了更新的方法, 但是方法签名不太一样.
 * {@link Observer#update(Observable, Object)}
 * @since 2019/9/29 9:05
 */
public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    /**
     * 注:
     *  1. 在调用 {@link #notifyObservers()} 之前, 要先调用 {@link #setChanged()} 来指示状态已经改变.
     *  2. 我们没有调用 {@link #notifyObservers(Object)} 传递数据对象, 这表示我们采用的做法是 "拉" pull.
     *   观察者会利用 setter 方法取得 WeatherData 对象的状态 -- 此即 "pull".
     *
     */
    public void measurementsChanged() {
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
