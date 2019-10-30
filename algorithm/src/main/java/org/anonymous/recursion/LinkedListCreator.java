package org.anonymous.recursion;

import org.anonymous.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/3 20:38
 */
public class LinkedListCreator {

    public static void main(String[] args) {
        LinkedListCreator creator = new LinkedListCreator();
        Node.printLinkedList(creator.createLinkedList(new ArrayList<>()));
        Node.printLinkedList(creator.createLinkedList(Collections.singletonList(1)));
        Node.printLinkedList(creator.createLinkedList(Arrays.asList(1, 2, 3, 4, 5)));
    }

    /**
     * Creates a linked list from the giving data.
     *
     * @param data the data to create the list
     * @return head of the linked list. The returned linked list
     * ends with last node with getNext() == null.
     * @throws NullPointerException if data == null.
     */
    public Node createLinkedList(List<Integer> data) {
        if (data.isEmpty()) {
            return null;
        }
        Node head = new Node(data.get(0));
        // (If <tt>fromIndex</tt> and <tt>toIndex</tt> are equal, the returned list is empty.
        head.setNext(createLinkedList(data.subList(1, data.size())));
        return head;
    }

    public Node createLargeLinkedList(int size) {
        Node prev = null;
        Node head = null;
        for (int i = 1; i < size; i++) {
            Node node = new Node(i);
            if (prev != null) {
                prev.setNext(node);
            } else {
                head = node;
            }
            prev = node;
        }
        return head;
    }

}
