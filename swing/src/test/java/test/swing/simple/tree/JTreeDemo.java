package test.swing.simple.tree;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 17:27
 * 树
 */
public class JTreeDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("教师学历信息");
        //frame.setBounds(700, 400, 500, 500);
        frame.setLocation(700, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JTreeDemo().createComponent());

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createComponent() {
        JPanel panel = new JPanel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("父节点");

        String[][] Teachers = new String[3][];
        Teachers[0] = new String[]{"王鹏", "李曼", "韩小国", "穆保龄", "尚凌云", "范超峰"};
        Teachers[1] = new String[]{"胡会强", "张春辉", "宋芳", "阳芳", "朱山根", "张茜", "宋媛媛"};
        Teachers[2] = new String[]{"刘丹", "张小芳", "刘华亮", "聂来", "吴琼"};
        String[] gradeNames = {"硕士学历", "博士学历", "博士后学历"};
        DefaultMutableTreeNode node;
        DefaultMutableTreeNode childNode;
        int length = 0;
        for (int i = 0; i < 3; i++) {
            length = Teachers[i].length;
            node = new DefaultMutableTreeNode(gradeNames[i]);
            for (int j = 0; j < length; j++) {
                childNode = new DefaultMutableTreeNode(Teachers[i][j]);
                node.add(childNode);
            }
            root.add(node);
        }
        JTree tree = new JTree(root);
        panel.add(tree);
        //panel.setVisible(true);
        return panel;
    }
}
