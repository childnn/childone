package rebuild.bidirectional;

import java.util.HashSet;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 22:28
 */
public class MultiOrder {

    private final Set<MultiCustomer> customers = new HashSet<>();

    void addCustomer(MultiCustomer customer) {
        customer.friendOrders().add(this);
        customers.add(customer);
    }

    void removeCustomer(MultiCustomer customer) {
        customer.friendOrders().remove(this);
        customers.remove(customer);
    }

}
