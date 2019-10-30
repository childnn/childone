package test.swing.simple.tree;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 17:27
 * 树
 */
public class JTreeDemo0 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("教师学历信息");
        //frame.setBounds(700, 400, 500, 500);
        frame.setLocation(700, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JTreeDemo0().createComponent());

        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createComponent() {
        JPanel panel = new JPanel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("父节点");

        Map<String, String[]> data = new HashMap<>();
        data.put("硕士学历", new String[]{"王鹏", "李曼", "韩小国", "穆保龄", "尚凌云", "范超峰"});
        data.put("博士学历", new String[]{"胡会强", "张春辉", "宋芳", "阳芳", "朱山根", "张茜", "宋媛媛"});
        data.put("博士后学历", new String[]{"刘丹", "张小芳", "刘华亮", "聂来", "吴琼"});

        data.forEach((father, childData) -> {
            DefaultMutableTreeNode fatherNode = new DefaultMutableTreeNode(father);
            for (String childDatum : childData) {
                fatherNode.add(new DefaultMutableTreeNode(childDatum));
            }
            root.add(fatherNode);
        });

        panel.add(new JTree(root));

        //panel.setVisible(true);
        return panel;
    }

}
