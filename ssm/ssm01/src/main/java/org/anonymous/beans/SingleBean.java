package org.anonymous.beans;

/**
 * @author child
 * 2019/4/25 18:50
 * 多例依赖于单例， 单例销毁时，多例会如何
 */
public class SingleBean {
    public SingleBean() {
        System.out.println("singleton...");
    }

    public void destroy() {
        System.out.println("single--destroy..");
    }
}
