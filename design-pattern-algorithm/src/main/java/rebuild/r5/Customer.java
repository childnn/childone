package rebuild.r5;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:53
 * 重构第五步: Replace Temp with Query
 * 移除临时变量: 由于任何函数都可以调用 tatalAmount 查询函数, 所以它能促成干净的设计, 减少冗长复杂的函数
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

    // Replace Temp with Query
    public String statement() {
        // double totalAmount = 0;
        // int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental each : rentals) {

            // Replace Temp with Query
            // 抽取 常客积分计算 方法
            // frequentRenterPoints += each.getFrequentRenterPoints();

            // show figures for this rental
            result.append("\t")
                    .append(each.getMovie().getTitle())
                    .append("\t")
                    .append(each.getCharge()/*thisAmount*/)
                    .append("\n");

            // Replace Temp with Query
            // 抽取 计算总金额 方法
            // totalAmount += r.getCharge()/*thisAmount*/;
        }

        // add footer lines
        return result.append("Amount owed is ")
                .append(getTotalCharge()/*totalAmount*/) // Replace Temp with Query
                .append("\n")
                .append("You earned ")
                .append(getFrequentRenterPoints()/*frequentRenterPoints*/) // Replace Temp with Query
                .append(" frequent renter points")
                .toString();

        // return result.toString();
    }

    // Replace Temp with Query
    private int getFrequentRenterPoints() {
        int result = 0;
        for (Rental each : rentals) {
            result += each.getFrequentRenterPoints();
        }
        return result;
    }

    // Replace Temp with Query
    private double getTotalCharge() {
        double result = 0;
        for (Rental each : rentals) {
            result += each.getCharge();
        }
        return result;
    }
}
