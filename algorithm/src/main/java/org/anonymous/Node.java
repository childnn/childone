package org.anonymous;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/3 20:35
 */
public class Node {
    private final int value;
    private Node next;

    public Node(int value) {
        this.value = value;
        next = null; // 默认, 每一个新建的节点都指向 null
    }

    public static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.value);
            System.out.print(" ");
            head = head.next;
        }
        System.out.println();
    }

    public int getValue() {
        return value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
