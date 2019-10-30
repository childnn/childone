package org.anonymous.tree;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 14:39
 * A
 * B      C
 * D   E      F
 * G
 */
public class TreeCreator {
    public TreeNode createSampleTree() {
        TreeNode root = new TreeNode('A');
        root.setLeft(new TreeNode('B'));
        root.getLeft().setLeft(new TreeNode('D'));
        root.getLeft().setRight(new TreeNode('E'));
        root.getLeft().getRight().setLeft(new TreeNode('G'));

        root.setRight(new TreeNode('C'));
        root.getRight().setRight(new TreeNode('F'));

        return root;
    }

    public TreeNode createTree(String preOrder, String inOrder) {
        if (preOrder.isEmpty()) {
            return null;
        }
        char rootValue = preOrder.charAt(0);
        int rootIndex = inOrder.indexOf(rootValue); // 根节点的位置, 也是左子树的长度
        TreeNode root = new TreeNode(rootValue);
        root.setLeft(
                createTree(
                        preOrder.substring(1, 1 + rootIndex),
                        inOrder.substring(0, rootIndex)));
        root.setRight(
                createTree(
                        preOrder.substring(1 + rootIndex),
                        inOrder.substring(1 + rootIndex)));
        return root;
    }

}
