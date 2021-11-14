package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 15:21
 *
 * 这种方法在功能上与 公有域方法 类似, 但更加简洁
 * 无偿提供了序列化机制, 有效防止多次实例化, 即使在面对复杂的序列化或者
 * 反射攻击的时候.
 * 单元素的枚举类型实际上是实现 Singleton 的最佳方法
 *
 */
public enum SingletonEnum {

    INSTANCE;

    // 枚举类的构造方法默认私有
    SingletonEnum() {

    }
}
