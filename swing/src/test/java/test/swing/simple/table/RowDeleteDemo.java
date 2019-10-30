package test.swing.simple.table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Vector;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 14:47
 */
public class RowDeleteDemo extends JFrame {

    private JPanel contentPane;
    private JTable table;

    public RowDeleteDemo(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(700, 400, 400, 200);

        // 此时 table 还未创建, 如何实现 listener 中的 table 如何得到? 异步?
        addWindowListener(new MyWindowListener());

        // 设置 面板
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // 构建 table
        table = new JTable();
        // 都支持 shift 连续多选
        // SINGLE_INTERVAL_SELECTION: 不支持 ctrl 间隔多选, 支持 ctrl 单个连续多选
        // MULTIPLE_INTERVAL_SELECTION: ctrl/shift 单选/多选 都支持
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION/*SINGLE_INTERVAL_SELECTION*/);

        // 滚动条
        JScrollPane scrollPane = new JScrollPane(table/*, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED*/);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        //scrollPane.setViewportView(table);

        // 按钮
        JPanel panel = new JPanel();
        JButton bt = new JButton("删除");
        panel.add(bt);

        bt.addActionListener(new MyBtActionListener());

        contentPane.add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new RowDeleteDemo("图书信息表");
    }

    private class MyBtActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int[] selectedRows = table.getSelectedRows();
            for (int selectedRow : selectedRows) {
                model.removeRow(selectedRow);
            }
            table.setModel(model);
        }
    }

    private class MyWindowListener extends WindowAdapter {

        @Override
        public void windowActivated(WindowEvent e) {
            //DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // 获得表格模型
            Vector<String> columnNames = new Vector<>(Arrays.asList("书名", "出版社", "出版时间", "丛书类别", "定价"));
            Vector<String> column2Data = new Vector<>(Arrays.asList("Java从入门到精通（第2版）", "清华大学出版社",
                    "2010-07-01", "软件工程师入门丛书", "59.8元"));
            Vector<String> column3Data = new Vector<>(Arrays.asList("PHP从入门到精通（第2版）", "清华大学出版社",
                    "2010-07-01", "软件工程师入门丛书", "69.8元"));
            Vector<String> column4Data = new Vector<>(Arrays.asList("Visual Basic从入门到精通（第2版）", "清华大学出版社",
                    "2010-07-01", "软件工程师入门丛书", "69.8元"));
            Vector<String> column5Data = new Vector<>(Arrays.asList("Visual C++从入门到精通（第2版）", "清华大学出版社",
                    "2010-07-01", "软件工程师入门丛书", "69.8元"));


            TableModel tableModel = new DefaultTableModel(new Vector<>(Arrays.asList(column2Data, column3Data, column4Data, column5Data)), columnNames);

            table.setRowHeight(40);
            table.setModel(tableModel);
        }
    }

}
