package org.anonymous.se.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.Queue#add
 * @see java.util.Queue#offer
 * --
 * @see java.util.Queue#element()
 * @see java.util.Queue#peek()
 * @see java.util.Queue#poll()
 * @see java.util.Queue#remove
 * --
 * @see java.util.PriorityQueue
 * @see java.util.Deque
 * @see java.util.ArrayDeque
 * @see java.util.LinkedList implements Deque
 * @since 2021/1/14 9:25
 * 有的方法功能类似, 详细解释请查看 API 文档
 */
public class QueueTest {

    /**
     * @see java.util.Deque#addFirst
     * @see java.util.Deque#push
     * @see java.util.Deque#addLast
     * @see java.util.Deque#offerFirst
     * @see java.util.Deque#offerLast
     * @see java.util.Deque#descendingIterator()
     * @see java.util.Deque#getFirst()
     * @see java.util.Deque#getLast()
     * @see java.util.Deque#peekFirst()
     * @see java.util.Deque#peekLast()
     * @see java.util.Deque#pollFirst()
     * @see java.util.Deque#pollLast()
     * @see java.util.Deque#pop()
     * @see java.util.Deque#removeFirst()
     * @see java.util.Deque#removeFirstOccurrence
     * @see java.util.Deque#removeLast()
     * @see java.util.Deque#removeLastOccurrence
     * <p>
     * Deque 双端队列, 同时具备 Queue 和 Stack 的功能
     */
    @Test
    public void deque() {

    }

    @Test
    public void arrayDeque() {
        ArrayDeque<String> stack = new ArrayDeque<>();
        //... 类似 Stack: push, peek, pop
    }

    /**
     * @see java.util.PriorityQueue
     * 存储顺序不是按加入队列的顺序, 而是按队列元素大小重新排序
     * 1. 自然排序, 必须实现 Comparable 接口
     * 2. 定制排序, 不要求实现 Comparable 接口, 创建队列时, 指定 Comparator 比较器
     */
    @Test
    public void priorityQueueTest() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 查看源码: PriorityQueue 的 add 方法直接调用 offer 方法
        pq.add(100); // add: 如果当前 queue 存在容量限制, 则添加失败时抛出 IllegalStateException
        pq.offer(-10); // 不同与 add, 不会抛异常
        pq.offer(20);
        pq.offer(18);
        pq.offer(33);

        // toString 展示的并不是实际的存储顺序
        System.out.println("pq = " + pq);

        // 多次调用 poll, 可知元素是按自然顺序排列的
        Integer e;
        while (null != (e = pq.poll())) {
            System.out.println(e);
        }
    }

    @Test
    public void comparator() {

        PriorityQueue<Student> students = new PriorityQueue<>(Comparator.comparingInt(Student::getAge));
        students.offer(new Student(33, "杰克"));
        students.offer(new Student(13, "肉丝"));
        students.offer(new Student(113, "李白"));

        Student s;
        while (null != (s = students.poll())) {
            System.out.println("s = " + s);
        }

    }

}

class Student {

    private final int age;

    private final String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}