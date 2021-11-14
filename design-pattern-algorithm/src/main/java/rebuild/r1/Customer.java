package rebuild.r1;

import rebuild.Movie;
import rebuild.Rental;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:25
 * 重构第一步: 提取 "金额计算" 代码
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
     * @see rebuild.Customer#statement()
     */
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental r : rentals) {
            double thisAmount = amountFor(r);

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

    public double amountFor(Rental r) {
        double result = 0;
        switch (r.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                result += 2;
                if (r.getDaysRented() > 2) {
                    result += (r.getDaysRented() - 2) * 1.5;
                }
                break;
            case Movie.NEW_RELEASE:
                result += r.getDaysRented() * 3;
                break;
            case Movie.CHILDREN:
                result += 1.5;
                if (r.getDaysRented() > 3) {
                    result += (r.getDaysRented() - 3) * 1.5;
                }
                break;
        }
        return result;
    }

}
