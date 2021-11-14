package rebuild.r7;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/26 16:10
 * 引入 State 模式花了不少力气, 这值得吗? 这么做的收获是: 如果我要修改任何与价格相关的行为, 或是添加新的定价标准, 或是加入其他取决于价格的行为,
 * 程序的修改会容易得多. 这个程序的其余部分并不知道运用了 State 模式. 对于目前拥有的这个几个小量行为来说, 任何功能或特性上的修改也许都不合算,
 * 但如果在一个更复杂的系统中, 有十多个与价格相关的函数, 程序的修改难易程度就会有很大的区别.
 * 以上所有的修改都是小步骤进行, 不求快, 但求好.
 * Extract Method, Move Method, Replace Conditional with Polymorphism, Self Encapsulate Field,
 * Replace Type Code with State/Strategy.
 * 所有重构行为都使责任的分配更合理, 代码的维护更轻松. 重构后的程序风格, 将迥异与过程化风格--后者也许是某些人习惯的风格.
 * 不过一旦你习惯了这种重构后的风格, 就很难再满足于结构化风格了.
 *
 */
public abstract class Price {

    abstract int getPriceCode();

    /**
     * @see Movie#getCharge(int)
     */
    abstract double getCharge(int daysRented);

    /**
     * @see Movie#getFrequentRenterPoints(int)
     */
    public int getFrequentRenterPoints(int daysRented) {
        // 默认实现返回 1
        return 1;
    }

}

class ChildrenPrice extends Price {

    @Override
    int getPriceCode() {
        return rebuild.Movie.CHILDREN;
    }

    @Override
    double getCharge(int daysRented) {
        double result = 1.5;
        if (daysRented > 3) {
            result += (daysRented - 3) * 1.5;
        }
        return result;
    }

}

class NewReleasePrice extends Price {

    @Override
    int getPriceCode() {
        return Movie.NEW_RELEASE;
    }

    @Override
    double getCharge(int daysRented) {
        return daysRented * 3;
    }

    /**
     * @see Movie#getFrequentRenterPoints(int)
     */
    @Override
    public int getFrequentRenterPoints(int daysRented) {
        return daysRented > 1 ? 2 : 1;
    }

}

class RegularPrice extends Price {

    @Override
    int getPriceCode() {
        return Movie.REGULAR;
    }

    @Override
    double getCharge(int daysRented) {
        double result = 2;
        if (daysRented > 2) {
            result += (daysRented - 2) * 1.5;
        }
        return result;
    }
}