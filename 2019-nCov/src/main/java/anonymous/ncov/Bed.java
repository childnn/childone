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
}
