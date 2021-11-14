package rebuild.unidirectional;

import java.util.HashSet;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/31 8:37
 *
 * 在此例中, 除非现有 Customer 对象, 否则不会存在 Order 对象.
 * 因此我想将从 Order 到 Customer 的连接移除掉.
 * 对于本项重构来说, 最困难的就是检查可行性. 如果我知道本项重构是安全的, 那么重构手法自身十分简单.
 * 问题在于是否有任何代码依赖 Order 中的 customer 字段存在. 如果确实有, 那么在删除这个字段之后,
 * 必须提供替代品.
 * 参见 Order 代码示例
 */
public class Customer {

    // 修改后
    double getPriceFor(Order order) {
        return order.getDiscountedPrice(this);
    }

    // 修改前
    // double getPriceFor(Order order) {
    //     return order.getDiscountedPrice();
    // }



    private Set<Order> orders = new HashSet<>();

    Set<Order> friendOrders() {
        return orders;
    }

    void addOrder(Order order) {
        order.setCustomer(this);
    }

    public double getDiscount() {
        return 0;
    }
}
