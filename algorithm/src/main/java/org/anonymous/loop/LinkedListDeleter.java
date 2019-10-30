package org.anonymous.loop;

import org.anonymous.Node;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 13:32
 */
public class LinkedListDeleter {

    public Node deleteIfEquals(Node head, int value) {
        /*if (head.getValue() == value) {
            head = head.getNext();
        }*/
        while (head != null && head.getValue() == value) {
            head = head.getNext();
        }
        if (head == null) {
            return null;
        }
        Node prev = head;
        while (prev.getNext() != null) {
            if (prev.getNext().getValue() == value) {
                // delete it
                prev.setNext(prev.getNext().getNext());
            } else {
                prev = prev.getNext();
            }
        }
        return head;
    }
}
