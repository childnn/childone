package org.anonymous.loop;

import org.anonymous.Node;
import org.anonymous.recursion.LinkedListCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 12:55
 */
public class LinkedListReverser {

    public static void main(String[] args) {
        LinkedListReverser reverser = new LinkedListReverser();
        LinkedListCreator creator = new LinkedListCreator();
        Node.printLinkedList(reverser.reverseLinkedList(
                creator.createLinkedList(new ArrayList<>())));
        Node.printLinkedList(reverser.reverseLinkedList(
                creator.createLinkedList(Collections.singletonList(1))));
        Node.printLinkedList(reverser.reverseLinkedList(
                creator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5))));
        // 如果此处用递归反转, 会出现 stack over flow
        reverser.reverseLinkedList(creator.createLargeLinkedList(1000000));
        System.out.println("done");
    }

    public Node reverseLinkedList(Node head) {
        Node newHead = null; // newHead points to the linked list already reversed.
        Node curHead = head; // curHead points to the linked list not yet reversed.
        while (curHead != null) {
            Node next = curHead.getNext();
            curHead.setNext(newHead);
            newHead = curHead;
            curHead = next;
        }
        return newHead;
    }

}
