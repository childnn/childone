package rebuild.r4;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:35
 * 重构第四步: 提炼 "常客积分计算" 代码
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

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental each : rentals) {

            // add frequent renter points
            frequentRenterPoints += each.getFrequentRenterPoints();

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
