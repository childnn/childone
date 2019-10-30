package org.anonymous.tree;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 14:43
 * 前序遍历的第一个节点是 树根
 * 拿着树根在中序中找.
 */
public class TreeTraversal {
    public static void main(String[] args) {
        TreeCreator creator = new TreeCreator();
        TreeTraversal traversal = new TreeTraversal();
        System.out.println("前序: ");
        traversal.preOrder(creator.createSampleTree());

        System.out.println();
        System.out.println("中序: ");
        traversal.inOrder(creator.createSampleTree());

        System.out.println();
        System.out.println("后序: ");
        traversal.postOrder(creator.createSampleTree());

        System.out.println("\n=================");
        traversal.postOrder(creator.createTree("ABDEGCF", "DBGEACF"));
        System.out.println("\n=================");
        traversal.postOrder(creator.createTree("", ""));
        System.out.println("\n=================");
        traversal.postOrder(creator.createTree("AB", "BA"));
        System.out.println("\n======================\n");
        System.out.println(traversal.postOrder("ABDEGCF", "DBGEACF"));
        System.out.println("======================");
        System.out.println(traversal.postOrder("", ""));
        System.out.println("======================");
        System.out.println(traversal.postOrder("AB", "AB"));
    }

    // 前序遍历
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.getValue());
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    // 中序遍历
    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getValue());
        inOrder(root.getRight());
    }

    // 后序遍历
    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getValue());
    }

    public String postOrder(String preOrder, String inOrder) {
        if (preOrder.isEmpty()) {
            return "";
        }
        char root = preOrder.charAt(0);
        int rootIndex = inOrder.indexOf(root);

        return postOrder(
                preOrder.substring(1, 1 + rootIndex),
                inOrder.substring(0, rootIndex)) +
                postOrder(
                        preOrder.substring(1 + rootIndex),
                        inOrder.substring(1 + rootIndex)) +
                root;
    }

}
