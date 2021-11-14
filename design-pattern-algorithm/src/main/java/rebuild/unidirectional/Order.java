package rebuild.unidirectional;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/31 8:37
 */
public class Order {

    // 首先, 我需要研究所有读取这个字段的函数, 以及所有使用这些函数的函数.
    // 我能找到另一条途径来提供 Customer 对象吗 --- 这通常意味着将
    // Customer 对象作为参数传递给用户.
    // 参见: getDiscountedPrice() --> getDiscountedPrice(Customer)
    // 如果待改函数是被 Customer 对象调用的, 那么这样的修改方案特别容易实施,
    // 因为 Customer 对象将自己作为参数传给函数很容易.
    // 参见 rebuild.unidirectional.Customer.getPriceFor 的修改

    // 移除 customer 字段后
    double getDiscountedPrice(Customer customer) {
        return getCrossPrice() * (1 - customer.getDiscount());
    }

    // 移除 customer 字段前
    double getDiscountedPrice() {
        return getCrossPrice() * (1 - customer.getDiscount());
    }

    private double getCrossPrice() {
        return 0;
    }


    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

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
