package rebuild;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/24 15:53
 */
public /*abstract*/ class Rental {

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

    // 重构第二步之后加
    // protected abstract double getCharge();

    // 重构第五步之后加
    // protected int getFrequentRenterPoints() {
    //     return 0;
    // }
}
