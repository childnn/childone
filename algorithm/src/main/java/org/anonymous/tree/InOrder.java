package org.anonymous.tree;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 15:52
 * 寻找中序遍历的下一个节点
 */
public class InOrder {

    public static void main(String[] args) {
        TreeCreator creator = new TreeCreator();
        InOrder inOrder = new InOrder();

        TreeNode sampleTree = creator.createSampleTree();
        inOrder.traverse(sampleTree);
        inOrder.traverse(creator.createTree("", ""));
        inOrder.traverse(creator.createTree("AB", "BA"));
        inOrder.traverse(creator.createTree("A", "A"));
        inOrder.traverse(creator.createTree("ABCD", "ABCD"));
        inOrder.traverse(creator.createTree("ABCD", "DCBA"));
    }

    public TreeNode next(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.getRight() != null) {
            return first(node.getRight());
        } else {
            while (node.getParent() != null &&
                    node.getParent().getRight() == node
                /*node.getParent().getLeft() != node*/) {
                node = node.getParent();
            }
            // node.getParent() == null
            // || node is left child of ths parent
            return node.getParent();
        }
    }

    public TreeNode first(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode curNode = node;
        while (curNode.getLeft() != null) {
            curNode = curNode.getLeft();
        }
        return curNode;
    }

    public void traverse(TreeNode root) {
        for (TreeNode node = first(root); node != null; node = next(node)) {
            System.out.print(node.getValue());
        }
        System.out.println();
    }

}
