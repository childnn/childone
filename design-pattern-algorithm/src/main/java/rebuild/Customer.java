package rebuild;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/24 15:54
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

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");

        for (Rental r : rentals) {
            double thisAmount = 0;
            switch (r.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (r.getDaysRented() > 2) {
                        thisAmount += (r.getDaysRented() - 2) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += r.getDaysRented() * 3;
                    break;
                case Movie.CHILDREN:
                    thisAmount += 1.5;
                    if (r.getDaysRented() > 3) {
                        thisAmount += (r.getDaysRented() - 3) * 1.5;
                    }
                    break;
            }

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
}

// 重构第一步: 提取 "金额计算" 代码
// class NewCust extends Customer {
//     public NewCust(String name) {
//         super(name);
//     }
//
//     @Override
//     public String statement() {
//         double totalAmount = 0;
//         int frequentRenterPoints = 0;
//         StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");
//
//         for (Rental r : rentals) {
//             double thisAmount = amountFor(r);
//
//             // add frequent renter points
//             frequentRenterPoints++;
//
//             // add bonus for a two day new release rental
//             if (r.getMovie().getPriceCode() == Movie.NEW_RELEASE &&
//                     r.getDaysRented() > 1) {
//                 frequentRenterPoints++;
//             }
//
//             // show figures for this rental
//             result.append("\t").append(r.getMovie().getTitle()).append("\t").append(thisAmount).append("\n");
//             totalAmount += thisAmount;
//         }
//
//         // add footer lines
//         result.append("Amount owed is ").append(totalAmount).append("\n");
//         result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
//
//         return result.toString();
//     }
//
//     public double amountFor(Rental r) {
//         double result = 0;
//         switch (r.getMovie().getPriceCode()) {
//             case Movie.REGULAR:
//                 result += 2;
//                 if (r.getDaysRented() > 2) {
//                     result += (r.getDaysRented() - 2) * 1.5;
//                 }
//                 break;
//             case Movie.NEW_RELEASE:
//                 result += r.getDaysRented() * 3;
//                 break;
//             case Movie.CHILDREN:
//                 result += 1.5;
//                 if (r.getDaysRented() > 3) {
//                     result += (r.getDaysRented() - 3) * 1.5;
//                 }
//                 break;
//         }
//         return result;
//     }
// }

/**
 * 重构第二步: 转移 "金额计算" 代码
 * 观察 {@link NewCust#amountFor(rebuild.Rental)} 发现这个函数使用了来自 {@link Rental} 类的信息,
 * 却没有使用来自 {@link Customer} 类的信息
 * 这立刻使我怀疑它是否被放错了位置.
 * 绝大多数情况下, 函数应该放在它所使用的数据的所属对象内, 所以
 * #amountFor 函数应该移到 Rental 类去.
 */
// class NewRental extends Rental {
//
//     public NewRental(Movie movie, int daysRented) {
//         super(movie, daysRented);
//     }
//
//     // 去掉参数
//     @Override
//     public double getCharge() {
//         double result = 0;
//         switch (getMovie().getPriceCode()) {
//             case Movie.REGULAR:
//                 result += 2;
//                 if (r.getDaysRented() > 2) {
//                     result += (r.getDaysRented() - 2) * 1.5;
//                 }
//                 break;
//             case Movie.NEW_RELEASE:
//                 result += r.getDaysRented() * 3;
//                 break;
//             case Movie.CHILDREN:
//                 result += 1.5;
//                 if (r.getDaysRented() > 3) {
//                     result += (r.getDaysRented() - 3) * 1.5;
//                 }
//                 break;
//         }
//         return result;
//     }
// }
//
// class NewCust1 extends NewCust {
//
//     public NewCust1(String name) {
//         super(name);
//     }
//
//     // 委托
//     @Override
//     public double amountFor(Rental r) {
//         return r.getCharge();
//     }
// }


/**
 * 重构第三步: 找出程序中对旧函数的所有引用点, 并修改它们, 让它们改用新函数
 */
// class NewCust2 extends NewCust {
//
//     public NewCust2(String name) {
//         super(name);
//     }
//
//     /**
//      * @deprecated use {@link Rental#getCharge()} instead
//      */
//     @Deprecated
//     @Override
//     public double amountFor(Rental r) {
//         return r.getCharge();
//     }
//
//     @Override
//     public String statement() {
//         double totalAmount = 0;
//         int frequentRenterPoints = 0;
//         StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");
//
//         for (Rental r : rentals) {
//
//             // 替换新函数
//             double thisAmount = r.getCharge();
//
//             // add frequent renter points
//             frequentRenterPoints++;
//
//             // add bonus for a two day new release rental
//             if (r.getMovie().getPriceCode() == Movie.NEW_RELEASE &&
//                     r.getDaysRented() > 1) {
//                 frequentRenterPoints++;
//             }
//
//             // show figures for this rental
//             result.append("\t").append(r.getMovie().getTitle()).append("\t").append(thisAmount).append("\n");
//             totalAmount += thisAmount;
//         }
//
//         // add footer lines
//         result.append("Amount owed is ").append(totalAmount).append("\n");
//         result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
//
//         return result.toString();
//     }
//
// }

/**
 * 重构第四步: 移除多余临时变量
 * thisAmount
 */
// class NewCust3 extends NewCust2 {
//
//     public NewCust3(String name) {
//         super(name);
//     }
//
//     /**
//      * 尽量除去临时变量. 临时变量往往引发问题, 它们会导致大量参数被传来传去, 而其实完全没必要.
//      * 你很容易跟丢它们, 尤其在长长的函数之中更是如此.
//      * 当然, 这么做也需要付出性能上的代价, 例如本例的 费用 就被计算了两次.
//      * 但是, 这很容易在 Rental 类中被优化. 而且如果代码有合理的组织和管理, 优化就会有很好的效果.
//      */
//     @Override
//     public String statement() {
//         double totalAmount = 0;
//         int frequentRenterPoints = 0;
//         StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");
//
//         for (Rental each : rentals) {
//
//             // 移除 thisAmount
//             // 替换新函数
//             // double thisAmount = each.getCharge();
//
//             // add frequent renter points
//             frequentRenterPoints++;
//
//             // add bonus for a two day new release rental
//             if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE &&
//                     each.getDaysRented() > 1) {
//                 frequentRenterPoints++;
//             }
//
//             // show figures for this rental
//             result.append("\t").append(each.getMovie().getTitle()).append("\t").append(each.getCharge()/*thisAmount*/).append("\n");
//             totalAmount += each.getCharge()/*thisAmount*/;
//         }
//
//         // add footer lines
//         result.append("Amount owed is ").append(totalAmount).append("\n");
//         result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
//
//         return result.toString();
//     }
// }

/**
 * 重构第五步: 提炼 "常客积分计算" 代码
 */
// class NewCust4 extends NewCust3 {
//     public NewCust4(String name) {
//         super(name);
//     }
//
//     @Override
//     public String statement() {
//         double totalAmount = 0;
//         int frequentRenterPoints = 0;
//         StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");
//
//         for (Rental each : rentals) {
//
//             // 移除 thisAmount
//             // 替换新函数
//             // double thisAmount = each.getCharge();
//
//
//             /*// add frequent renter points
//             frequentRenterPoints++;
//
//             // add bonus for a two day new release rental
//             if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE &&
//                     each.getDaysRented() > 1) {
//                 frequentRenterPoints++;
//             }*/
//
//             // 重构: 替换方法
//             frequentRenterPoints += each.getFrequentRenterPoints();
//
//             // show figures for this rental
//             result.append("\t")
//                     .append(each.getMovie().getTitle())
//                     .append("\t")
//                     .append(r.getCharge()/*thisAmount*/)
//                     .append("\n");
//
//             totalAmount += r.getCharge()/*thisAmount*/;
//         }
//
//         // add footer lines
//         result.append("Amount owed is ").append(totalAmount).append("\n");
//         result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
//
//         return result.toString();
//     }
// }

// class NewRental1 extends NewRental {
//     public NewRental1(Movie movie, int daysRented) {
//         super(movie, daysRented);
//     }
//
//     @Override
//     protected int getFrequentRenterPoints() {
//         if (getMovie().getPriceCode() == Movie.NEW_RELEASE && getDaysRented() > 1) {
//             return 2;
//         }
//         return 1;
//     }
// }

// 重构第六步: Replace Temp with Query
// 移除临时变量: 由于任何函数都可以调用 tatalAmount 查询函数, 所以它能促成干净的设计, 减少冗长复杂的函数
// class NewCust5 extends NewCust4 {
//
//     public NewCust5(String name) {
//         super(name);
//     }
//
//     @Override
//     public String statement() {
//         // double totalAmount = 0;
//         // int frequentRenterPoints = 0;
//         StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\r\n");
//
//         for (Rental each : rentals) {
//
//             // 抽取 常客积分计算 方法
//             // frequentRenterPoints += each.getFrequentRenterPoints();
//
//             // show figures for this rental
//             result.append("\t")
//                     .append(each.getMovie().getTitle())
//                     .append("\t")
//                     .append(each.getCharge()/*thisAmount*/)
//                     .append("\n");
//
//             // 抽取 计算总金额 方法
//             // totalAmount += r.getCharge()/*thisAmount*/;
//         }
//
//         // add footer lines
//         result.append("Amount owed is ").append(getTotalCharge()/*totalAmount*/).append("\n");
//         result.append("You earned ").append(getFrequentRenterPoints()/*frequentRenterPoints*/).append(" frequent renter points");
//
//         return result.toString();
//     }
//
//     private int getFrequentRenterPoints() {
//         int result = 0;
//         for (Rental each : rentals) {
//             result += each.getFrequentRenterPoints();
//         }
//         return result;
//     }
//
//     private double getTotalCharge() {
//         double result = 0;
//         for (Rental each : rentals) {
//             result += each.getCharge();
//         }
//         return result;
//     }
// }


// 运用多态取代与加个相关的条件逻辑

/**
 * 继续: 重构第七步
 *
 * .@see rebuild.NewRental#getCharge()
 * 最好不要在另一个对象的属性基础上运用 switch 语句. 如果不得不使用, 也应该在对象自己的数据上使用,
 * 而不是在别人的数据上使用.
 * 这暗示 #getCharge() 函数应该移到 Movie 类中去
 */
// class NewMovie extends Movie {
//     public NewMovie(String title, int priceCode) {
//         super(title, priceCode);
//     }
//
//     /**
//      * 为了让它得以运行, 必须把租期长度作为参数传递进去. 当然, 租期长度来自
//      * Rental 对象. 计算费用时需要两项数据: 租期长度和影片类型. 为什么我选择将租期长度传给 Movie 对象, 而不是将
//      * 影片类型传给 Rental 对象呢? 因为系统可能发生的变化是加入新影片类型, 这种变化带有不稳定倾向. 如果影片类型有所
//      * 变化, 我希望尽量控制它中造成的影响, 所有选择在 Movie 对象内计算费用.
//      * @see rebuild.NewRental#getCharge
//      * @param daysRented 租期
//      * @return 金额
//      */
//     @Override
//     public double getCharge(int daysRented) {
//         double result = 0;
//         switch (getPriceCode()) {
//             case Movie.REGULAR:
//                 result += 2;
//                 if (daysRented > 2) {
//                     result += (daysRented - 2) * 1.5;
//                 }
//                 break;
//             case Movie.NEW_RELEASE:
//                 result += daysRented * 3;
//                 break;
//             case Movie.CHILDREN:
//                 result += 1.5;
//                 if (daysRented > 3) {
//                     result += (daysRented - 3) * 1.5;
//                 }
//                 break;
//         }
//         return result;
//     }
//
//     // 迁移 getCharge 后, 以相同手法处理常客积分计算. 这样把根据影片类型而变化的所有东西,
//     // 都放在影片类型所属的类中.
//     @Override
//     protected int getFrequentRenterPoints(int daysRented) {
//         return getPriceCode() == NEW_RELEASE && daysRented > 1 ? 2 : 1;
//     }
//
// }

// 委托
// class NewRental2 extends NewRental1 {
//     public NewRental2(Movie movie, int daysRented) {
//         super(movie, daysRented);
//     }
//
//     @Override
//     public double getCharge() {
//         return movie.getCharge(daysRented);
//     }
//
//     @Override
//     protected int getFrequentRenterPoints() {
//         return movie.getFrequentRenterPoints(daysRented);
//     }
// }
