package org.anonymous.beans;

/**
 * @author child
 * 2019/4/25 18:50
 */
public class ProtoBean {
    private SingleBean singleBean;

    public ProtoBean(SingleBean singleBean) {
        this.singleBean = singleBean;
        System.out.println("prototype....");
    }

    public ProtoBean() {
        System.out.println("proto--空参---");
    }

    public void destroy() {
        System.out.println("prototype--destroy....");
    }
}
