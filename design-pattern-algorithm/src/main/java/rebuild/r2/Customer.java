package rebuild.r2;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:27
 */
public class Customer {

    /**
     * 重构第二步: 转移 "金额计算" 代码
     * 观察 {@link Customer#amountFor(Rental)} 发现这个函数使用了来自 {@link Rental} 类的信息,
     * 却没有使用来自 {@link rebuild.Customer} 类的信息
     * 这立刻使我怀疑它是否被放错了位置.
     * 绝大多数情况下, 函数应该放在它所使用的数据的所属对象内, 所以
     * #amountFor 函数应该移到 Rental 类去.
     */

    protected String name;
    protected List<Rental> rentals;

    public Customer(String name) {
        this.name = name;
        rentals = new ArrayList<>();
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String getName() {
        return name;
    }

    /**
     * @see rebuild.Customer#statement()
     */
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental r : rentals) {
            // 找出程序中对旧函数的所有引用点, 并修改它们, 让它们改用新函数
            double thisAmount = r.getCharge(); /*amountFor(r);*/

            // add frequent renter points
            frequentRenterPoints++;

            // add bonus for a two day new release rental
            if (r.getMovie().getPriceCode() == Movie.NEW_RELEASE &&
                    r.getDaysRented() > 1) {
                frequentRenterPoints++;
            }

            // show figures for this rental
            result.append("\t").append(r.getMovie().getTitle()).append("\t").append(thisAmount).append("\n");
            totalAmount += thisAmount;
        }

        // add footer lines
        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");

        return result.toString();
    }

    /**
     * 找出程序中对旧函数的所有引用点, 并修改它们, 让它们改用新函数
     * @deprecated use {@link Rental#getCharge()} instead.
     */
    @Deprecated
    public double amountFor(Rental r) {
        return r.getCharge();
        // double result = 0;
        // switch (r.getMovie().getPriceCode()) {
        //     case Movie.REGULAR:
        //         result += 2;
        //         if (r.getDaysRented() > 2) {
        //             result += (r.getDaysRented() - 2) * 1.5;
        //         }
        //         break;
        //     case Movie.NEW_RELEASE:
        //         result += r.getDaysRented() * 3;
        //         break;
        //     case Movie.CHILDREN:
        //         result += 1.5;
        //         if (r.getDaysRented() > 3) {
        //             result += (r.getDaysRented() - 3) * 1.5;
        //         }
        //         break;
        // }
        // return result;
    }

}
