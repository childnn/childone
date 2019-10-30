package org.anonymous.observer.java;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 9:55
 */
public class WeatherStation {
    public static void main(String[] args) {
        // 创建主题: 可观察者.
        WeatherData weatherData = new WeatherData();
        // 创建观察者: 向主题中注册当前观察者.
        CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay(weatherData);
        // 改变主题状态 - 调用 -> java.util.Observable.notifyObservers() - 调用 ->  java.util.Observer.update
        // - 实际上是其子类(实际的观察者) -> org.anonymous.observer.java.CurrentConditionDisplay.update (pull 数据)
        // - 调用 -> org.anonymous.observer.java.CurrentConditionDisplay.display 展示变化.
        weatherData.setMeasurements(80F, 67F, 65.0F);
    }
}
