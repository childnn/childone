package test.nowcoder;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/06/06
 */
public class Words {

    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        // int length = countLastWord(sc.nextLine());
        // System.out.println(length);
        int i = Integer.parseInt("0xAA", 16);
        System.out.println("i = " + i);
    }

    public static int countLastWord(String words) {
        if (words == null || words.isEmpty()) {
            return 0;
        }
        String[] array = words.split(" ");

        return array[array.length - 1].length();
    }

}
