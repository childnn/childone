package rebuild.r5;

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

    public int getFrequentRenterPoints() {
        return getMovie().getPriceCode() == rebuild.Movie.NEW_RELEASE && getDaysRented() > 1 ? 2 : 1;
    }

}
