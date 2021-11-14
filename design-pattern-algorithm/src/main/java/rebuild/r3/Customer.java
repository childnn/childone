package rebuild.r3;

import rebuild.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:35
 * 重构第三步: 移除多余临时变量
 */
public class Customer {

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
     * 尽量除去临时变量. 临时变量往往引发问题, 它们会导致大量参数被传来传去, 而其实完全没必要.
     * 你很容易跟丢它们, 尤其在长长的函数之中更是如此.
     * 当然, 这么做也需要付出性能上的代价, 例如本例的 费用 就被计算了两次.
     * 但是, 这很容易在 Rental 类中被优化. 而且如果代码有合理的组织和管理, 优化就会有很好的效果.
     */
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental each : rentals) {

            // 移除 thisAmount
            // 替换新函数
            // double thisAmount = each.getCharge();

            // add frequent renter points
            frequentRenterPoints++;

            // add bonus for a two day new release rental
            if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE &&
                    each.getDaysRented() > 1) {
                frequentRenterPoints++;
            }

            // show figures for this rental
            result.append("\t").append(each.getMovie().getTitle()).append("\t").append(each.getCharge()/*thisAmount*/).append("\n");
            totalAmount += each.getCharge()/*thisAmount*/;
        }

        // add footer lines
        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");

        return result.toString();
    }

}
