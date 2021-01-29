package org.anonymous.test.spring;

import org.anonymous.beans.ProtoBean;
import org.anonymous.beans.SingleBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/25 18:56
 * 测试 bean_proto_single.xml
 * 多例依赖于单例, 单例销毁时,多例会如何
 */
public class ProtoSingleTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean_proto_single.xml");
        System.out.println("main...main\n");

        SingleBean singleBean = applicationContext.getBean(SingleBean.class);
        System.out.println(singleBean);

        ProtoBean protoBean = applicationContext.getBean(ProtoBean.class);
        System.out.println(protoBean);

        applicationContext.registerShutdownHook(); //这个方法到底是干嘛的？

        //容器销毁,对象的销毁方法执行, 容器不再可以使用, 但是并不意味着对象不再可以使用, 对象仍然可以使用
        applicationContext.close();

        //        ProtoBean bean = applicationContext.getBean(ProtoBean.class); //already closed   Exception

        //        singleBean.destroy();
        System.out.println(singleBean);
        System.out.println(protoBean);
    }

}
