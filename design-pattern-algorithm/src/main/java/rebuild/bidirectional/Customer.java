package rebuild.bidirectional;

import java.util.HashSet;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/30 22:00
 */
public class Customer {

    // 首先, 为 Customer 添加一个字段, 由于一个客户可以拥有多份订单, 所以
    // 这个新增字段应该是个集合. 我不希望同一份订单在同一个集合中出现一次以上
    // 这里用 set
    private Set<Order> orders = new HashSet<>();

    // 现在, 需要决定由哪一个类负责控制关联关系. 我比较喜欢让单个类来操控,
    // 因为这样就可以将所有处理关联关系的逻辑集中安置于一地.
    // 我将按下列步骤做出这一决定
    // 如果两者都是引用对象, 而其间的关联是 "一对多" 关系, 难么就由 "拥有单一引用" 的那一方承担 "控制者" 角色
    // 以本例而言, 如果一个客户可拥有多份订单, 那么就有 Order 类来控制关联关系.
    // 如果某个对象是组成另一对象的部件, 那么由后者负责控制关联关系
    // 如果两者都是引用对象, 而期间的关联关系是 "多对多" 关系, 那么随便其中哪个对象来控制关联关系, 都无所谓.

    // 本例, Order 负责控制关联关系, 所以 Customer 添加一个辅助函数, 让 Order 可以直接访问 orders 集合.
    // Order 的修改函数将使用这个辅助函数对指针两端对象进行同步控制. 我将这个辅助函数命名为 friendOrders()
    // 表示这个函数只能在这种特殊情况下使用. 此外, 如果 Order 和 Cutomer 位于同一个包内, 我还会将 friendOrders()
    // 声明为包内可见, 使其可见程度降到最低. 但, 如果不同包, 则只能 public 了.

    Set<Order> friendOrders() {
        // should only be used by Order when modifying the association.
        return orders;
    }

    // 如果你希望在 Customer 中也能修改连接, 就让它调用控制函数
    void addOrder(Order order) {
        order.setCustomer(this);
    }

}
