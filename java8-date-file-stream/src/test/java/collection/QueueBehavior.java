package collection;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/25 20:52
 */
public class QueueBehavior {
    static Stream<String> strings() {
        return Arrays.stream(
                ("one two three four five six seven " +
                        "eight nine ten").split(" "));
    }

    static void test(int id, Queue<String> queue) {
        System.out.print(id + ": ");
        strings().map(queue::offer).count();
        while (queue.peek() != null)
            System.out.print(queue.remove() + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        int count = 10;
        test(1, new LinkedList<>());
        test(2, new PriorityQueue<>());
        test(3, new ArrayBlockingQueue<>(count));
        test(4, new ConcurrentLinkedQueue<>());
        test(5, new LinkedBlockingQueue<>());
        test(6, new PriorityBlockingQueue<>());
        test(7, new ArrayDeque<>());
        test(8, new ConcurrentLinkedDeque<>());
        test(9, new LinkedBlockingDeque<>());
        test(10, new LinkedTransferQueue<>());
        test(11, new SynchronousQueue<>());
    }
}

// 优先级队列
// 考虑一个待办事项列表，其中每个对象包含一个 **String** 以及主要和次要优先级值。通过实现 **Comparable** 接口来控制此待办事项列表的顺序
class ToDoItem implements Comparable<ToDoItem> {
    private char primary;
    private int secondary;
    private String item;

    ToDoItem(String td, char pri, int sec) {
        primary = pri;
        secondary = sec;
        item = td;
    }

    @Override
    public int compareTo(ToDoItem arg) {
        if (primary > arg.primary)
            return +1;
        if (primary == arg.primary)
            if (secondary > arg.secondary)
                return +1;
            else if (secondary == arg.secondary)
                return 0;
        return -1;
    }

    @Override
    public String toString() {
        return Character.toString(primary) +
                secondary + ": " + item;
    }
}

class ToDoList {
    public static void main(String[] args) {
        PriorityQueue<ToDoItem> toDo =
                new PriorityQueue<>();
        toDo.add(new ToDoItem("Empty trash", 'C', 4));
        toDo.add(new ToDoItem("Feed dog", 'A', 2));
        toDo.add(new ToDoItem("Feed bird", 'B', 7));
        toDo.add(new ToDoItem("Mow lawn", 'C', 3));
        toDo.add(new ToDoItem("Water lawn", 'A', 1));
        toDo.add(new ToDoItem("Feed cat", 'B', 1));
        while (!toDo.isEmpty())
            System.out.println(toDo.remove());
    }
}


class CountString implements Supplier<String> {
    private int n = 0;

    CountString() {
    }

    CountString(int start) {
        n = start;
    }

    @Override
    public String get() {
        return Integer.toString(n++);
    }
}

// **Deque** （双端队列）就像一个队列，但是可以从任一端添加和删除元素。 Java 6为 **Deque** 添加了一个显式接口。
class SimpleDeques {
    static void test(Deque<String> deque) {
        CountString s1 = new CountString(),
                s2 = new CountString(20);
        for (int n = 0; n < 8; n++) {
            deque.offerFirst(s1.get());
            deque.offerLast(s2.get()); // Same as offer()
        }
        System.out.println(deque);
        StringBuilder result = new StringBuilder();
        while (deque.size() > 0) {
            System.out.print(deque.peekFirst() + " ");
            result.append(deque.pollFirst()).append(" ");
            System.out.print(deque.peekLast() + " ");
            result.append(deque.pollLast()).append(" ");
        }
        System.out.println("\n" + result);
    }

    // **LinkedBlockingDeque** 仅填充到它的限制大小为止，然后忽略额外的添加。
    public static void main(String[] args) {
        int count = 10;
        System.out.println("LinkedList");
        test(new LinkedList<>());
        System.out.println("ArrayDeque");
        test(new ArrayDeque<>());
        System.out.println("LinkedBlockingDeque");
        test(new LinkedBlockingDeque<>(count));
        System.out.println("ConcurrentLinkedDeque");
        test(new ConcurrentLinkedDeque<>());
    }
}