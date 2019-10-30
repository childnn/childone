package org.anonymous.recursion;

import org.anonymous.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 10:28
 */
public class LinkedListReverser {

    public static void main(String[] args) {
        LinkedListCreator creator = new LinkedListCreator();
        LinkedListReverser reverser = new LinkedListReverser();
        Node.printLinkedList(reverser.reverseLinkedList(creator.createLinkedList(new ArrayList<>())));
        Node.printLinkedList(reverser.reverseLinkedList(creator.createLinkedList(Collections.singletonList(1))));
        Node.printLinkedList(reverser.reverseLinkedList(creator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5))));
    }

    /**
     * Reverses a linked list.
     *
     * @param head the linked list to reverse
     * @return head of the reversed linked linked
     */
    public Node reverseLinkedList(Node head) {
        // size == 0
        //if (head == null) {
        //    return null;
        //}
        //// size == 1
        //if (head.getNext() == null) {
        //    return head;
        //}
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node newHead = reverseLinkedList(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return newHead;
    }

}
