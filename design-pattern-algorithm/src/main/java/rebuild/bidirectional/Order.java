package rebuild.bidirectional;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 22:00
 * Order 引用 Customer, 而 Customer 并未引用 Order
 */
public class Order {

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    /**
     * 类之间的关联关系是各式各样的, 因此修改函数的代码也会随之改变.
     * 如果 this.customer 不可能为 null, 那么可以拿掉此处 第一个 null 检查,
     * 但仍然需要检查传入参数是否为 null, 不过, 基本形式总是相同的:
     * 先让对方(Customer中的方向指针)删除指向你的指针,
     * 再将你的指针指向一个新对象.
     * 最后让那个新对象把它的指针指向你
     */
    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.friendOrders().remove(this);
        }
        this.customer = customer;
        if (this.customer != null) {
            this.customer.friendOrders().add(this);
        }
    }

}
