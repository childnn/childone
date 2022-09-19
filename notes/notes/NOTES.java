package miaowan;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.无变量的字符串拼接，在编译期间值都确定了，所以 javac 工具帮我们把它直接编译成一个字符常量。
 * 2.有变量的字符串拼接，在编译期间变量的值无法确定，所以运行期间会生成一个StringBuilder 对象。
 * 3.循环中使用字符串拼接，循环内，每循环一次就会产生一个新的 StringBuilder 对象，对资源有一定的损耗。
 * 4.循环外使用 StringBuilder，循环内再执行 append() 方法拼接字符串，只会成一个 StringBuilder 对象。
 *   因此，对于有循环的字符串拼接操作，建议使用 StringBuilder 和 StringBuffer，对性能会有一定的提升。
 * ---------------------
 * 2019年2月15日11:02:34
 * List<E>接口：索引，重复，有序
 *      父接口：Collection<E>
 *             Iterable<E>
 *      List的子类特点
 *             ArrayList
 *                      底层数据结构是【数组】，查询快，增删慢。
 *                      线程不安全，效率高。
 *             Vector
 *                      底层数据结构是【数组】，查询快，增删慢。
 *                      线程安全，效率低。
 *             LinkedList
 *                       底层数据结构是【链表】，查询慢，增删快。
 *                       线程不安全，效率高。
 *            到底使用谁呢?看需求?
 *                 分析：
 *                     要安全吗?
 *                         要：Vector(即使要，也不使用这个)
 *                         不要：ArrayList或者LinkedList
 *                             查询多；ArrayList
 *                             增删多：LinkedList
 *
 *                 什么都不知道，就用ArrayList
 *
 *      1.创建容器：多态，ArrayList<E>,LinkedList<E>,Vector
 *      2.增删改查：
 *          曾：
 *              void add(int index, E element) 将指定的元素插入此列表中的指定位置（可选操作）。
 *              boolean add (E e):追加
 *          删：
 *              boolean remove(E e)
 *              E remove(int index):从列表中删除指定元素的第一个出现（如果存在）（可选操作）。
 *              void clear() 从此列表中删除所有元素（可选操作）。
 *          改：
 *              E set(int index, E e):替换//区别于add的插入方法。 返回的是被修改的元素
 *          查：
 *              get(int index)
 *              int size()
 *              int indexOf(Object o):返回此列表中指定元素的第一次出现的索引，如果此列表不包含元素，则返回-1。
 *              boolean contains(Object o)
 *              boolean isEmpty​() 如果此列表不包含元素，则返回 true 。
 *          implements Iterable<T>的子类共有
 *                 Iterator<E> iterator​() 以正确的顺序返回该列表中的元素的迭代器。
 *          List及其子类特有： ListIterator<E> listIterator​() 返回列表中的列表迭代器（按适当的顺序）。
 *                             ListIterator<E> listIterator​(int index) 从列表中的指定位置开始，返回列表中的元素（按正确顺序）的列表迭代器
 *
 *         遍历：
 *              1.for: size() + get(index)
 *              2.超级for
 *              3.Iterator
 *              4.ListIterator
 *              5.toArray()
 *                  <T> T[] toArray​(T[] a) 以正确的顺序返回一个包含此列表中所有元素的数组（从第一个到最后一个元素）; 返回的数组的运行时类型是指定数组的运行时类型。
 *                  Object[] toArray​() 以正确的顺序（从第一个到最后一个元素）返回一个包含此列表中所有元素的数组。
 *
 * "【ctrl+shift+alt+鼠标左键多处编辑】", "ctrl+w/ctrl+shift+w选择蔓延",
 * "ctrl+shift+f7,高亮 + f3 跳转查找", "ctrl+shift+]/[选中光标所在的后/前代码块", "ctrl+delete/backspace",
 * "【F2 / Shift + F2 Next/previous highlighted error跳转error】",
 * "【f11书签/shift+f11打开书签】", "alt + shift + c 近期更改",
 * "ctrl+E 近期class文件", "ctrl+alt+方向键:跳转最近编辑点", "alt + f3 查找选中元素",
 * "ctrl+shift+i 打开选中方法/变量的定义位置", "alt + f7 查找 class, method, variable 在项目中出现的位置",
 * "ctrl+f12 当前类的结构","serialVersionUID-->settings-->editor-->inspections",
 *  "ctrl+shift+. 折叠代码块", "ctrl+[] 快速跳转大括号的另一端"
 *  "ctrl+shift+u 大小写转换","ctrl+u 跳转重写方法的父类"
 *  ctrl+R 替换
 *  shift+f4: 分离当前类到新窗口打开(保留原窗口的位置)
 *  alt + q： 查看方法声明
 *
 *  ctrl+q: document
 *  ctrl+p： 参数
 *  ctrl+b: ctrl+左键
 *
 *
 */
@SuppressWarnings("all")
public class NOTES {
    public static void main(String[] args) {
        /**
         * debug：
         F9            resume programe 恢复程序
         Alt+F10       show execution point 显示执行断点
         F8            Step Over 相当于eclipse的f6      跳到下一步
         F7            Step Into 相当于eclipse的f5就是  进入到代码
         Alt+shift+F7  Force Step Into 这个是强制进入代码
         Shift+F8      Step Out  相当于eclipse的f8跳到下一个断点，也相当于eclipse的f7跳出函数
         Atl+F9        Run To Cursor 运行到光标处
         ctrl+shift+F9   debug运行java类
         ctrl+shift+F10  正常运行java类
         alt+F8          debug时选中查看值
         */

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        System.out.println("list = " + list);
        Integer remove = list.remove(1);
        System.out.println(list);
        Integer set = list.set(0, 23);
        System.out.println("set = " + set); //1
    }
}
