package test.basic;

/**
 * 计算-10.8~5.9之间的，绝对值大于6或者绝对值小于2.1的整数个数
 */
public class MathTest {

    public static void main(String[] args) {
        double min = -10.8;
        double max = 5.9;
        int count = 0;
        // 只需要整数，直接 int 强转
        for (int i = (int) min; i < max; i++) {
            int abs = Math.abs(i);
            if (abs > 6 || abs < 2.1) {
                System.out.print(i + " ");
                count++;
            }
        }
        System.out.println("满足条件的有"+ count + "个");

    }

}
