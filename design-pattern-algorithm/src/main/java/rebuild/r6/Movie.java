package rebuild.r6;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 15:31
 */
public class Movie {

    public static final int CHILDREN = 2;
    public static final int NEW_RELEASE = 1;
    public static final int REGULAR = 0;

    protected String title;
    protected int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    /**
     * 为了让它得以运行, 必须把租期长度作为参数传递进去. 当然, 租期长度来自
     * Rental 对象. 计算费用时需要两项数据: 租期长度和影片类型. 为什么我选择将租期长度传给 Movie 对象, 而不是将
     * 影片类型传给 Rental 对象呢? 因为系统可能发生的变化是加入新影片类型, 这种变化带有不稳定倾向. 如果影片类型有所
     * 变化, 我希望尽量控制它中造成的影响, 所有选择在 Movie 对象内计算费用.
     * @see Rental#getCharge()
     * @param daysRented 租期
     * @return 金额
     */
    public double getCharge(int daysRented) {
        double result = 0;
        switch (getPriceCode()) {
            case Movie.REGULAR:
                result += 2;
                if (daysRented > 2) {
                    result += (daysRented - 2) * 1.5;
                }
                break;
            case Movie.NEW_RELEASE:
                result += daysRented * 3;
                break;
            case Movie.CHILDREN:
                result += 1.5;
                if (daysRented > 3) {
                    result += (daysRented - 3) * 1.5;
                }
                break;
        }
        return result;
    }

    // 迁移 getCharge 后, 以相同手法处理常客积分计算. 这样把根据影片类型而变化的所有东西,
    // 都放在影片类型所属的类中.
    public int getFrequentRenterPoints(int daysRented) {
        return getPriceCode() == NEW_RELEASE && daysRented > 1 ? 2 : 1;
    }
}
