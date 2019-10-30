package org.anonymous.sample;

/**
 * 1.Kafka是利用零拷贝和页缓存技术实现高性能读取的(没有采用流式微批处理技术)
 * 流式微批处理技术可以降低吞吐量  （高吞吐，高延迟）
 * os cache写 + 磁盘顺序写，0.01毫秒，低延迟，高吞吐，每毫秒可以处理100条数据，
 * 每秒可以处理10万条数据，不需要依托类似spark straeming那种batch微批处理的机制  （高吞吐，低延迟）
 * <p>
 * <p>
 * 2.Kafka是如何通过精心设计消息格式节约磁盘空间占用开销的？
 * kafka的消息格式如下：
 * 最新名称叫做RecordBatch
 * crc32，magic，attribute，时间戳，key长度，key，value长度，value
 * <p>
 * kafka是直接通过NIO的ByteBuffer以二进制的方式来保存消息的，这种二级制紧凑保存格式可以比使用Java对象保存消息要节约40%的内存空间
 * <p>
 * 然后这个消息实际上是封装在一个log entry里的，你可以认为是一个日志条目吧，在kafka里认为每个partition实际上就是一个磁盘上的日志文件，写到parttion里去的消息就是一个日志，所以log entry就是一个日志
 * <p>
 * 这个日志条目包含了一个offset，一个消息的大小，然后是消息自身，就是上面那个数据结构，但是这里要注意的一点，就是这个message里可能会包含多条消息压缩在一起，所以可能找一条消息，需要从这个压缩数据里遍历搜索
 * <p>
 * 而且这里还有一个概念就是消息集合，一个消息集合里包含多个日志，最新名称叫做RecordBatch
 * <p>
 * 后来消息格式演化为了如下所示：
 * <p>
 * （1）消息总长度
 * （2）属性：废弃了，已经不用
 * （3）时间戳增量：跟RecordBatch的时间戳的增量差值
 * （4）offset增量：跟RecordBatch的offset的增量差值
 * （5）key长度
 * （6）key
 * （7）value长度
 * （8）value
 * （9）header个数
 * （10）header：自定义的消息元数据，key-value对
 * <p>
 * 通过时间戳、offset、key长度等都用可变长度来尽可能减少空间占用，v2版本的数据格式比v1版本的数据格式要节约很多磁盘开销
 * <p>
 * <p>
 * 3.如何实现分布式存储数据？
 * borker（一个进程）   一个topic  有多个 partition
 * <p>
 * 4.多副本冗余机制
 * partition可以有leader flower   只有leader 可以写 flower只能同步数据读
 * <p>
 * 5.ISR 机制保证数据不丢失
 * 每个leader 会维护一个ISR列表  必须要保证每次写数据的同时，往ISR列表中的 flower 也写成功 这条数据才是成功的
 * <p>
 * 6.各个leader 如何实现负载均衡
 * kafka 尽可能的把各个leader 均匀的 分配在不同的broker上
 * <p>
 * 7. 元数据存储到zk中，broker 做到无状态  基于zk做故障感知
 * <p>
 * <p>
 * 8. offset  每个数据的位置
 * leo    即将要写入的数据的位置
 * HW   高水位  指flower节点 同步的数据的位置  只有HW 前边的数据才能被消费者消费到
 * <p>
 * 9. 数据同步细节
 * leader 会维护每个flower 的 LEO的值， 每次fetch 的时候 flower 会带上 LEO的值  这个时候更新 leader 维护的每个flower 的 LEO的值
 * <p>
 * 10. 什么时候更新 HW ？
 * flower  HW 更新
 * 每次flower 从 leader fetch 数据， leader 都会把自己的HW返回给flower ，
 * flower 会拿自己的leo  和 leader的 HW 做一个对比，取最小值作为自己的HW
 * <p>
 * leader  HW 更新
 * 所有的flower 的 LEO 的共同的最大值
 * <p>
 * 11.leader 切换时导致的数据丢失问题
 * Kafka 0.11.x版本引入leader epoch机制解决高水位机制弊端
 * 记录leader的 版本号 还有一开始写入数据的offset
 * <p>
 * 12.Kafka0.8.2.x版本的ISR机制在生产环境有什么缺陷？
 * 之前说的那套ISR机制是kafka 0.8.x系列的机制，其实是有缺陷的，那个参数默认的值是4000，
 * 也就是follower落后4000条数据就认为是out-of-sync，但是这里有一个问题，就是这个数字是固定死的
 * 0.9 以后改成了按照时间 默认10秒钟没有同步数据  提出ISR 列表
 */
public class Kafka {

}
