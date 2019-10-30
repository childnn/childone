package test.swing.simple.table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 16:32
 */
public class RowDeleteDemo0 extends JFrame {

    private JPanel contentPane;
    private JTable table;

    public RowDeleteDemo0() throws HeadlessException {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                doWindowActivated(e);
            }
        });
        setTitle("图书信息表");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(700, 400, 450, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        JButton button = new JButton("删除");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doActionPerformed(e);
            }
        });
        panel.add(button);
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollPane.setViewportView(table);

        setVisible(true);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new RowDeleteDemo0();
    }

    private void doActionPerformed(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();    // 获得表格模型
        int[] selectedRows = table.getSelectedRows();
        for (int selectedRow : selectedRows) {
            model.removeRow(selectedRow);
        }
        table.setModel(model);
    }

    private void doWindowActivated(WindowEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();    // 获得表格模型
        tableModel.setRowCount(0);    // 清空表格中的数据
        tableModel.setColumnIdentifiers(new Object[]{"书名", "出版社", "出版时间", "丛书类别", "定价"});    // 设置表头
        tableModel.addRow(new Object[]{"Java从入门到精通（第2版）", "清华大学出版社", "2010-07-01", "软件工程师入门丛书", "59.8元"});    // 增加列
        tableModel.addRow(new Object[]{"PHP从入门到精通（第2版）", "清华大学出版社", "2010-07-01", "软件工程师入门丛书", "69.8元"});
        tableModel.addRow(new Object[]{"Visual Basic从入门到精通（第2版）", "清华大学出版社", "2010-07-01", "软件工程师入门丛书", "69.8元"});
        tableModel.addRow(new Object[]{"Visual C++从入门到精通（第2版）", "清华大学出版社", "2010-07-01", "软件工程师入门丛书", "69.8元"});

        table.setRowHeight(30);
        table.setModel(tableModel);    // 应用表格模型
    }

}
