package test.swing.simple.table;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import java.awt.Container;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 13:47
 */
public class JTableDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("学生成绩表");
        jf.setBounds(400, 500, 500, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = jf.getContentPane();

        Object[][] tableDate = new Object[5][8];
        for (int i = 0; i < 5; i++) {
            tableDate[i][0] = "1000" + i;
            for (int j = 1; j < 8; j++) {
                tableDate[i][j] = 0;
            }
        }
        String[] name = {"学号", "软件工程", "Java", "网络", "数据结构", "数据库", "总成绩", "平均成绩"};
        JTable table = new JTable(tableDate, name);

        contentPane.add(new JScrollPane(table));

        jf.setVisible(true);
    }

}
