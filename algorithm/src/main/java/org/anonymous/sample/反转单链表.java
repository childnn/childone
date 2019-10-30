package org.anonymous.sample;

public class 反转单链表 {
    // 倒置传入的单链表
    public static SingleLinkedNode getReverseSingleLinked(SingleLinkedNode node) {
        SingleLinkedNode reverseSingleLinked = new SingleLinkedNode(
                node.getValue(), null);
        SingleLinkedNode lastN = reverseSingleLinked;
        while (node.getNext() != null) {
            node = node.getNext();
            reverseSingleLinked = new SingleLinkedNode(node.getValue(), lastN);
            lastN = reverseSingleLinked;
        }
        return lastN;
    }

    public static void listLinked(SingleLinkedNode node) {
        System.out.print(node.getValue());
        while (node.getNext() != null) {
            node = node.getNext();
            System.out.print(node.getValue());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int c = 75;
        SingleLinkedNode root = new SingleLinkedNode(String.valueOf((char) c),
                null);
        SingleLinkedNode lastN = root;
        // 生成单链表
        for (int i = c - 1; i > 64; i--) {
            SingleLinkedNode n = new SingleLinkedNode(String.valueOf((char) i), lastN);
            lastN = n;
        }
        listLinked(lastN);
        listLinked(getReverseSingleLinked(lastN));
    }
}

/*
 * 单链表Node
 */
class SingleLinkedNode {
    private String value;
    // 指向下一个Node
    private SingleLinkedNode next;

    public SingleLinkedNode(String value, SingleLinkedNode next) {
        this.value = value;
        this.next = next;
    }

    public String getValue() {
        return value;
    }

    public SingleLinkedNode getNext() {
        return next;
    }

    public void setNext(SingleLinkedNode node) {
        this.next = node;
    }
}
