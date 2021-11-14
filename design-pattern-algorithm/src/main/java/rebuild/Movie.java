package rebuild;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/24 15:51
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

    // /**
    //  * 重构第七步添加
    //  * @see NewRental2#getCharge()
    //  */
    // protected double getCharge(int daysRented) {
    //     return 0;
    // }
    // /**
    //  * 重构第七步添加
    //  * @see NewRental2#getFrequentRenterPoints
    //  */
    // protected int getFrequentRenterPoints(int daysRented) {
    //     return 0;
    // }

}

// 使用 多态 取代 switch
// Replace Type Code with State/Strategy
// Replace Conditional with Polymorphism
// class Movie1 extends Movie {
//     private Price price;
//
//     public Movie1(String title, int priceCode) {
//         super(title, priceCode);
//         this.title = title;
//         setPriceCode(priceCode);
//     }
//
//     @Override
//     protected double getCharge(int daysRented) {
//         return price.getCharge(daysRented);
//     }
//
//     @Override
//     protected int getFrequentRenterPoints(int daysRented) {
//         return price.getFrequentRenterPoints(daysRented);
//     }
//
//     @Override
//     public void setPriceCode(int priceCode) {
//         switch (priceCode) {
//             case REGULAR:
//                 price = new RegularPrice();
//                 break;
//             case CHILDREN:
//                 price = new ChildrenPrice();
//                 break;
//             case NEW_RELEASE:
//                 price = new NewReleasePrice();
//                 break;
//             default:
//                 throw new IllegalArgumentException("Incorrect Price Code.");
//         }
//     }
//
// }

// abstract class Price {
//     abstract int getPriceCode();
//
//     /**
//      * @see NewMovie#getCharge(int)
//      */
//     abstract double getCharge(int daysRented);
//
//     /**
//      * @see NewMovie#getFrequentRenterPoints(int)
//      */
//     public int getFrequentRenterPoints(int daysRented) {
//         // 默认实现返回 1
//         return 1;
//     }
// }
//
// class ChildrenPrice extends Price {
//     @Override
//     int getPriceCode() {
//         return Movie.CHILDREN;
//     }
//
//     @Override
//     double getCharge(int daysRented) {
//         double result = 1.5;
//         if (daysRented > 3) {
//             result += (daysRented - 3) * 1.5;
//         }
//         return result;
//     }
//
// }
//
// class NewReleasePrice extends Price {
//     @Override
//     int getPriceCode() {
//         return Movie.NEW_RELEASE;
//     }
//
//     @Override
//     double getCharge(int daysRented) {
//         return daysRented * 3;
//     }
//
//     /**
//      * @see NewMovie#getFrequentRenterPoints(int)
//      */
//     @Override
//     public int getFrequentRenterPoints(int daysRented) {
//         return daysRented > 1 ? 2 : 1;
//     }
//
// }
//
// class RegularPrice extends Price {
//     @Override
//     int getPriceCode() {
//         return Movie.REGULAR;
//     }
//
//     @Override
//     double getCharge(int daysRented) {
//         double result = 2;
//         if (daysRented > 2) {
//             result += (daysRented - 2) * 1.5;
//         }
//         return result;
//     }
// }
