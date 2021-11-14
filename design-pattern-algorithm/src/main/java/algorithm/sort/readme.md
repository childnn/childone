数据的逻辑结构分为两种:
-- 线性的: 一条线的结构. 数组, 链表, 队列, 栈 etc.
-- 非线性的: 数据间的关系非线性. 堆, 树, 图 etc.

链表:
-- 链表是一种物理存储单元上非连续、非顺序的存储结构, 数据元素的逻辑顺序是通过链表中的指针链接次序实现的, 一般用于增/删.
-- 单向链表:
    每个节点通过 "指针" 链接起来, 每个节点有 2 个组成部分, 一部分是 数据域, 另一部分是 后继指针(用来存储后一个节点的地址)
    在这条链中, 最开始的节点称为 Head, 最末尾节点的指针指向 NULL.
-- 双向链表: 
    双向链表与单项链表的区别是前者是 2 个方向都有指针, 后者只有 1 个方向的指针. 双向链表的每个节点都有 2 个指针,
    一个指向 前节点, 一个指向 后节点. 双向链表在操作的时候比 单项链表的效率要高很多, 但是由于多一个指针空间, 内存会多.
-- 循环链表:
    其实循环链表就是一种特殊的单向链表, 只不过在单向链表的基础上, 将尾节点的指针指向了 Head 节点, 使之首位相连.


算法名称    

快速排序-QuickSort
归并排序-MergeSort
堆排序-HeapSort
冒泡排序-BubbleSort
插入排序-InsertionSort
选择排序-SelectionSort
希尔排序-ShellSort
桶排序-BucketSort
基数排序-RadixSort
TimSort-结合了合并排序（合并排序）和插入排序（插入排序）而得出的排序算法

分类:
1. 根据待排序的数据量规模分类:
   内部排序: 在排序过程中, 待排序的数据能够被全部加载进内存中;
   外部排序: 待排序的数据太大, 不能全部同时放入内存, 排序过程中需要内存与外部存储交换数据.
2. 根据排序的稳定性分类:
   稳定性排序: 冒泡排序, 插入排序, 归并排序;
   不稳定排序: 快速排序, 选择排序, 希尔排序, 堆排序.
3. 根据排序时间复杂度分类:
    O(N): 桶排序, 基数排序, 计数排序;
    O(NlogN): 快速排序, 希尔排序, 归并排序, 堆排序;
    O(N*N): 冒泡排序, 插入排序, 选择排序.
4. 算法思想分类:
    分治: 快速排序, 归并排序;
    插入: 希尔排序, 插入排序;
    选择: 堆排序, 选择排序;
    交换: 冒泡排序, 快速排序.

排序算法质量判定:
1. 时间复杂度:
   这是衡量算法性能的常规方法, 对于排序算法也不例外, 这是衡量排序算法最重要的一个指标.
   在排序算法中常用的操作就是 "比较" 和 "移动" 元素, 因此想优化某个排序算法的时间复杂度就是要
   减少去 "比较" 和 "移动" 元素的次数.
   同时, 由于需排序的数据不同会导致即使同一个算法也有着完全不同的时间消耗, 因此还应进一步分析
   排序算法的最好时间复杂度、最坏时间复杂度, 以及平均时间复杂度, 以做到对排序算法特性的充分了解.
2. 控件复杂度:
   这也是评价算法的另一个常规指标. 需要分析执行算法所需要的辅助存储空间(原有数据已占用的空间不算).
   如果空间复杂度为 O(1) 则说明执行算法的辅助存储空间为常量级别, 很优秀.
   对于 冒泡/插入/选择 等排序算法, 空间复杂度都是 O(1).
3. 排序稳定性:
   排序的稳定性是一个新的指标, 对于排序算法来说非常重要.
   通俗的讲: 假如在待排序的数组中有相等的元素, 则经过排序后, 这些相等的元素之间的原有顺序不变.
4. 算法的复杂度:
   算法本身的复杂度也会影响算法的性能(这里不是指的时间空间复杂度), 这里指 算法设计思想的复杂度(难易程度).