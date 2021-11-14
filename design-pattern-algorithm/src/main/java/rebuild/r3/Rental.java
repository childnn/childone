package rebuild.r3;

import rebuild.r2.Movie;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:28
 */
public class Rental {

    protected Movie movie;
    protected int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    /**
     * 重构第二步: 转移 "金额计算" 代码
     * 观察 {@link NewCust#amountFor(rebuild.Rental)} 发现这个函数使用了来自 {@link Rental} 类的信息,
     * 却没有使用来自 {@link rebuild.Customer} 类的信息
     * 这立刻使我怀疑它是否被放错了位置.
     * 绝大多数情况下, 函数应该放在它所使用的数据的所属对象内, 所以
     * #amountFor 函数应该移到 Rental 类去.
     */

    /**
     * 去掉参数
     * .@see Customer#amountFor(Rental)
     * @return
     */
    public double getCharge() {
        double result = 0;
        switch (getMovie().getPriceCode()) {
            case rebuild.Movie.REGULAR:
                result += 2;
                if (daysRented > 2) {
                    result += (daysRented - 2) * 1.5;
                }
                break;
            case rebuild.Movie.NEW_RELEASE:
                result += daysRented * 3;
                break;
            case rebuild.Movie.CHILDREN:
                result += 1.5;
                if (daysRented > 3) {
                    result += (daysRented - 3) * 1.5;
                }
                break;
        }
        return result;
    }

}
