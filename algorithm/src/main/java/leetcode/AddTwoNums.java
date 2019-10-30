package leetcode;

import org.anonymous.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/21 12:19
 * you are given two non-empty linked lists representing two
 * non-negative integers. the digits are stored in reverse order
 * and each of their nodes contain a single digit. add the two
 * numbers and return it as a linked list.
 * input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * output: 7 -> 0 -> 8
 * 解释: 相当于两个倒序的整数(低位在前,高位在后)相加
 * 当前位的结果: 两位数 + 进位 % 10
 * 当前位的进位: 和 / 10
 * 注:
 * 1. 两个 list 长度可能不一样
 * 2. 处理好进位问题: 位数增加
 */
public class AddTwoNums {

    public static void main(String[] args) {
        Node.printLinkedList(addTwoNums(generateNode(Arrays.asList(1, 2, 3)), generateNode(Arrays.asList(1, 2, 3))));
        Node.printLinkedList(addTwoNums(generateNode(Collections.singletonList(1)), generateNode(Arrays.asList(1, 2, 3))));
        Node.printLinkedList(addTwoNums(generateNode(Collections.emptyList()), generateNode(Arrays.asList(1, 2, 3))));
        Node.printLinkedList(addTwoNums(generateNode(Arrays.asList(9, 9, 9)), generateNode(Arrays.asList(1, 2, 3))));
        System.out.println("===============================");
        Node.printLinkedList(addTwoNums0(generateNode(Arrays.asList(1, 2, 3)), generateNode(Arrays.asList(1, 2, 3))));
        Node.printLinkedList(addTwoNums0(generateNode(Collections.singletonList(1)), generateNode(Arrays.asList(1, 2, 3))));
        Node.printLinkedList(addTwoNums0(generateNode(Collections.emptyList()), generateNode(Arrays.asList(1, 2, 3))));
        Node.printLinkedList(addTwoNums0(generateNode(Arrays.asList(9, 9, 9)), generateNode(Arrays.asList(1, 2, 3))));
    }

    static Node generateNode(List<Integer> data) {
        if (data.isEmpty()) {
            return null;
        }
        Node head = new Node(data.get(0));
        head.setNext(generateNode(data.subList(1, data.size())));
        return head;
    }

    /**
     * 1. whether one of the two node is null, replace it with 0;
     * 2. keep the while loop going when at least one of the three conditions is met.
     *
     * @param n1
     * @param n2
     * @return
     */
    static Node addTwoNums0(Node n1, Node n2) {
        Node prev = new Node(0); // 变化
        Node head = prev; // 不变
        int carry = 0;
        while (n1 != null || n2 != null || carry != 0) {
            int sum = (n1 == null ? 0 : n1.getValue()) + (n2 == null ? 0 : n2.getValue()) + carry;
            Node curr = new Node(sum % 10);
            carry = sum / 10;
            prev.setNext(curr);
            prev = curr;

            n1 = (n1 == null) ? n1 : n1.getNext();
            n2 = (n2 == null) ? n2 : n2.getNext();
        }

        return head.getNext();
    }

    static Node addTwoNums(Node n1, Node n2) {
        if (n1 == null) {
            return n2;
        }
        if (n2 == null) {
            return n1;
        }
        Node result = new Node(0);
        Node curr = result;
        int carry = 0;
        // 同等位数
        while (n1 != null && n2 != null) {
            int sum = n1.getValue() + n2.getValue() + carry;
            int val = sum % 10;
            carry = sum / 10;
            curr.setNext(new Node(val));
            curr = curr.getNext(); //
            n1 = n1.getNext();
            n2 = n2.getNext();
        }

        // 处理多余位数
        while (n1 != null) {
            int n1Value = n1.getValue();
            int val = (n1Value + carry) % 10;
            carry = (n1Value + carry) / 10;
            curr.setNext(new Node(val));
            curr = curr.getNext(); //
            n1 = n1.getNext();
        }

        while (n2 != null) {
            int n2Value = n2.getValue();
            int val = (n2Value + carry) % 10;
            carry = (n2Value + carry) / 10;
            curr.setNext(new Node(val));
            curr = curr.getNext(); //
            n2 = n2.getNext();
        }

        if (carry != 0) {
            curr.setNext(new Node(carry));
        }
        return result.getNext();

    }

    // carryNode 单节点: 初始值为 0
    static Node addTwoNums(Node n1, Node n2, Node carryNode) {
        if (n1 == null) {
            return n2;
        }
        if (n2 == null) {
            return n1;
        }
        int n1Value = n1.getValue();
        int n2Value = n2.getValue();
        int carry = 0; // 原始进位: 0
        int sum = n1Value + n2Value + carry;
        int val = sum % 10; // 和
        carry = sum / 10; // 进位
        Node head = new Node(val);


        while ((n1 = n1.getNext()) != null && (n2 = n2.getNext()) != null) {
            sum = n1.getValue() + n2.getValue() + carry;
            val = sum % 10; // 和
            carry = sum / 10; // 进位
            //head.setNext();
            head = head.getNext();
        }
        return head;
    }

}
