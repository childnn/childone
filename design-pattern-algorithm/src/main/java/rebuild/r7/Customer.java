package rebuild.r7;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:58
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
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental each : rentals) {
            // show figures for this rental
            result.append("\t")
                    .append(each.getMovie().getTitle())
                    .append("\t")
                    .append(each.getCharge()/*thisAmount*/)
                    .append("\n");
        }

        // add footer lines
        return result.append("Amount owed is ")
                .append(getTotalCharge()/*totalAmount*/) // Replace Temp with Query
                .append("\n")
                .append("You earned ")
                .append(getFrequentRenterPoints()/*frequentRenterPoints*/) // Replace Temp with Query
                .append(" frequent renter points")
                .toString();
    }

    private int getFrequentRenterPoints() {
        int result = 0;
        for (Rental each : rentals) {
            result += each.getFrequentRenterPoints();
        }
        return result;
    }

    private double getTotalCharge() {
        double result = 0;
        for (Rental each : rentals) {
            result += each.getCharge();
        }
        return result;
    }

}
