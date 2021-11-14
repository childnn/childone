package test.rebuild;

import org.junit.Before;
import org.junit.Test;
import rebuild.r7.Customer;
import rebuild.r7.Movie;
import rebuild.r7.Rental;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 16:45
 * 只有 rebuild.r7 包测试过, 其他包下的都是示例代码, 不保证正确性
 */
public class RebuildTest {

    private Customer customer;

    @Before
    public void b4() {
        customer = new Customer("Jack");
        customer.addRental(newRental("步履不停", 1, 3));
        customer.addRental(newRental("比海更深", 0, 4));
        customer.addRental(newRental("海边的曼彻斯特", 2, 1));
        customer.addRental(newRental("蜘蛛侠", 1, 2));
    }

    Rental newRental(String title, int priceCode, int daysRented) {
        return new Rental(new Movie(title, priceCode), daysRented);
    }

    @Test
    public void rt() {
        System.out.println(customer.statement());
    }

}
