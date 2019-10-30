package org.anonymous.observer.display;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/28 22:41
 */
public interface Observer {
    /**
     * 所有观察者必须实现 update() 方法, 以实现观察者接口.
     */
    void update(float temperature, float humidity, float pressure);
}
