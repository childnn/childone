package rebuild.r7;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 16:08
 * // 使用 多态 取代 switch
 * // Replace Type Code with State/Strategy
 * // Replace Conditional with Polymorphism
 */
public class Movie {

    public static final int CHILDREN = 2;
    public static final int NEW_RELEASE = 1;
    public static final int REGULAR = 0;

    protected String title;
    protected int priceCode;
    private Price price;

    public Movie(String title, int priceCode) {
        this.title = title;
        setPriceCode(priceCode);
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
        switch (priceCode) {
            case REGULAR:
                price = new RegularPrice();
                break;
            case CHILDREN:
                price = new ChildrenPrice();
                break;
            case NEW_RELEASE:
                price = new NewReleasePrice();
                break;
            default:
                throw new IllegalArgumentException("Incorrect Price Code.");
        }
    }

    // 使用 多态 取代 switch
    // Replace Type Code with State/Strategy
    // Replace Conditional with Polymorphism

    /**
     * 我们有数种影片, 它们以不同的方式回答相同的问题. 这听起来很像子类的工作
     * 我们可以建立 Movie 的三个子类, 每个都有自己的计费法.
     * 这么一来, 就可以用多态来取代 switch 语句了. 很遗憾的是有一个小问题, 不能这么干.
     * 一部影片可以在生命周期内修改自己的分类, 一个对象却不能在生命周期内修改自己所属的类.
     * todo: 上面的描述是原文, 没理解, 为什么 Movie 的子类就无法实现了... 是因为这里 Movie 不可变?
     * 这里是说, 在运行时可以修改价格而无法将 Movie 实例修改为其一个子类, 吗?
     * ---
     * 不过还有一个解决方法: State 模式.
     * Movie 本身不可变, 但是其内部属性不一.
     * 用 Price 这种 State 来表现不同的影片.
     * 加入这一层间接性, 就可以在 Price 对象内进行子类化动作, 于是便可在任何必要时刻修改价格.
     * ---
     * 关于 State 与 Strategy
     * Price 到底是 State 还是 Strategy?
     * 取决于 Price 类究竟代表计费方式(Pricer/PricingStrategy), 还是代表影片的某个状态(xxx是以部新片).
     * 在这个阶段, 对于模式及其名称的选择反映出你对结构的想法. 此刻把它视为影片的某种状态.
     * 实际上也可以称为 Strategy.
     */
    public double getCharge(int daysRented) {
        return price.getCharge(daysRented);
        /*double result = 0;
        switch (getPriceCode()) {
            case REGULAR:
                result += 2;
                if (daysRented > 2) {
                    result += (daysRented - 2) * 1.5;
                }
                break;
            case NEW_RELEASE:
                result += daysRented * 3;
                break;
            case CHILDREN:
                result += 1.5;
                if (daysRented > 3) {
                    result += (daysRented - 3) * 1.5;
                }
                break;
        }
        return result;*/
    }

    public int getFrequentRenterPoints(int daysRented) {
        return price.getFrequentRenterPoints(daysRented);
        // return getPriceCode() == NEW_RELEASE && daysRented > 1 ? 2 : 1;
    }

}
