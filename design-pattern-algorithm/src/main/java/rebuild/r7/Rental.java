package rebuild.r7;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:28
 * 运用多态取代与加个相关的条件逻辑
 * 最好不要在另一个对象的属性基础上运用 switch 语句. 如果不得不使用, 也应该在对象自己的数据上使用,
 * 而不是在别人的数据上使用.
 * 这暗示 {@link #getCharge()} 函数应该移到 Movie 类中去
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
     * @see Movie#getCharge(int)
     */
    @Deprecated
    public double getCharge() {
        return movie.getCharge(daysRented);
        /*double result = 0;
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
        return result;*/
    }

    /**
     * 迁移 getCharge 后, 以相同手法处理常客积分计算. 这样把根据影片类型而变化的所有东西,
     * 都放在影片类型所属的类中.
     * @deprecated use {@link Movie#getFrequentRenterPoints(int)} instead.
     */
    @Deprecated
    public int getFrequentRenterPoints() {
        // return getMovie().getPriceCode() == rebuild.Movie.NEW_RELEASE && getDaysRented() > 1 ? 2 : 1;
        return movie.getFrequentRenterPoints(daysRented);
    }

}
