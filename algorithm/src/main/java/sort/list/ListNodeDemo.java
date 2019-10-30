package sort.list;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/11 17:16
 * 单向链表.
 * @see java.util.LinkedList 双向链表.
 */
public class ListNodeDemo {

    public static ListNode reserveList(ListNode head) {
        // 定义一个前置节点变量, 默认是 null, 因为对于第一个节点而言没有前置节点.
        ListNode pre = null;
        // 定义一个当前节点变量, 首先将头节点赋值给它.
        ListNode curr = head;

        // 遍历整个链表, 直到当前指向的节点为空, 也就是最后一个节点了.
        while (null != curr) {
            // 在循环体里会去改变当前节点的指针方向, 本来当前节点的指针是指向的下一个节点.
            ListNode temp = curr.next;
            // 开始处理当前节点, 将当前节点的指针指向前面一个节点.
            curr.next = pre;
            // 将当前节点赋值给变量 pre, 也就是让 pre 移动一步, pre 指向了当前节点.
            pre = curr;
            // 将之前保存的临时节点(后面一个节点) 赋值给当前节点变量.
            curr = temp;
        }

        // 完成, 时间复杂度为 O(n).
        return pre;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = null;

        ListNode list = reserveList(head);
        while (null != list) {
            int val = list.val;
            System.out.println(val);
            list = list.next;
        }


    }

    static class ListNode {
        private int val;
        private ListNode next;

        public ListNode(int x) {
            val = x;
        }

       /* public ListNode setNext(ListNode next) {
            this.next = next;
            return this;
        }
*/
/*        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        */
    }

}
