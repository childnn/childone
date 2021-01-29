package org.anonymous.util;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author child
 * 2019/4/23 10:08
 * FactoryBean: 工厂 bean
 * 利用 <bean id="" class="org.anonymous.utils.MyFactoryBean"> 注册 工厂 bean
 * 实际上注册到 ioc 中的对象 不是 工厂bean 本身, 而是 getObject() 方法的返回值 所表示的对象
 * 即 FactoryBean 所生产的对象
 * 如果需要 注入工厂创建的对象,直接 ref="FatoryBean的id属性值" 引用即可
 * <p>
 * 这种创建对象的方法,要比 自定义工厂类(不实现 FactoryBean 接口), 注册对象要简单
 * 但是 一个 FatoryBean 只能创建一个对象(一个方法只能创建某个固定类的对象)
 */
public class MyFactoryBean implements FactoryBean<Object> {
    @Override
    public Object getObject() throws Exception { //返回值可以根据需要定义
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
