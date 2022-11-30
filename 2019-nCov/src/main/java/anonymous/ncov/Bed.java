package anonymous.ncov;

/**
 * 床位
 *
 * @ClassName: Bed
 * @Description: 床位
 * @author: Bruce Young
 * @date: 2020年02月02日 21:00
 */
public class Bed extends Point {
    /**
     * 是否占用了该床位
     */
    private boolean isEmpty = true;

    public Bed(int x, int y) {
        super(x, y);
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }


    public static void main(String[] args) {
        Integer i1 = 1000;
        Integer i2 = 1000;
        System.out.println(i1 == i2);

        i1 = 100;
        i2 = 100;
        System.out.println(i1 == i2);

        System.out.println(hash("sdfaf"));
        System.out.println(0 & hash("sdfaf"));
        System.out.println(1 & hash("sdfaf"));
        System.out.println(2 & hash("aaaa"));
        System.out.println(hash("通话"));
        System.out.println(hash("重地"));

    }


    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
