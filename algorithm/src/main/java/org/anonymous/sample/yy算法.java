package org.anonymous.sample;

import java.util.Stack;

public class yy算法 {
    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 3, 4, 5, 6};
        ListNode node = new ListNode(data);
        test(node, 3);
        node = new ListNode(data);
        test(node, 4);

    }

    private static void test(ListNode node, int k) {
        ListNode next = revert(node);
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode result = next;
        int i = 1;
        while (next != null) {
            next = next.next;
            i++;
            if (next == null) {
                stack.push(result);
            } else if (i == k) {
                ListNode t = next.next;
                next.next = null;
                stack.push(result);
                result = t;
                next = result;
                i = 1;
            }
        }
        while (!stack.empty()) {
            ListNode pop = stack.pop();
            System.out.print(pop + " -> ");
        }
        System.out.println();
    }


    public static ListNode revert(ListNode node) {
        if (node == null) {
            return null;
        }
        ListNode head = node;
        ListNode prev = null;
        ListNode next = node;
        while (next != null) {
            next = next.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public static class ListNode {
        public int val;
        public ListNode next;

        ListNode(int x) {
            val = x;
        }

        public ListNode(int[] data) {
            this.val = data[0];
            ListNode next = this;
            for (int i = 1; i < data.length; i++) {
                next.next = new ListNode(data[i]);
                next = next.next;
            }
        }

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            res.append(val).append(" -> ");
            ListNode next = this.next;
            while (next != null) {
                res.append(next.val).append(" -> ");
                next = next.next;
            }
            res.append("NULL");
            return res.toString();
        }
    }

}
