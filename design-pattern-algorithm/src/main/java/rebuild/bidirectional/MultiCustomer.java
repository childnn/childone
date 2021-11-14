package rebuild.bidirectional;

import java.util.HashSet;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 22:00
 */
public class MultiCustomer {

    private final Set<MultiOrder> orders = new HashSet<>();

    Set<MultiOrder> friendOrders() {
        // should only be used by Order when modifying the association.
        return orders;
    }

    void addOrder(MultiOrder order) {
        order.addCustomer(this);
    }

    void removeOrder(MultiOrder order) {
        order.removeCustomer(this);
    }

}
