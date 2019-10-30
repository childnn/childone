package org.anonymous.sample;

public class 反转单链表while {
    public static void main(String[] args) {
        SingleLinkNode headNode3 = new SingleLinkNode("3", null);
        SingleLinkNode headNode1 = new SingleLinkNode("2", headNode3);
        SingleLinkNode headNode2 = new SingleLinkNode("1", headNode1);
        SingleLinkNode reverse = reverse(headNode2);
        System.out.println("66666666");

    }
//  1---->   2  ------> ---->3---> null
    //head = null,
    //1----> head
    // next = 2

    static SingleLinkNode reverse(SingleLinkNode headNode) {
        SingleLinkNode newHead = null;
        SingleLinkNode next = null;
        while (headNode != null) {
            next = headNode.next;
            headNode.next = newHead;
            newHead = headNode;
            headNode = next;
        }
        return newHead;
    }

    /*
     * 单链表Node
     */
    static class SingleLinkNode {
        private String value;
        // 指向下一个Node
        private SingleLinkNode next;

        public SingleLinkNode(String value, SingleLinkNode next) {
            this.value = value;
            this.next = next;
        }

        public String getValue() {
            return value;
        }

        public SingleLinkNode getNext() {
            return next;
        }

        public void setNext(SingleLinkNode node) {
            this.next = node;
        }
    }
}
