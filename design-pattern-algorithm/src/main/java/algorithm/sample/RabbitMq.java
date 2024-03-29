package algorithm.sample;

/**
 * 1.你说一下你们在项目里是怎么用消息队列的？
 * 2.那你说说用消息队列都有什么优点和缺点？
 * 3.kafka、activemq、rabbitmq、rocketmq都有什么区别？
 * 4.那你们是如何保证消息队列的高可用啊？
 * 5.如何保证消息不被重复消费啊？如何保证消费的时候是幂等的啊？
 * 6.如何保证消息的可靠性传输啊？要是消息丢失了怎么办啊？
 * 7.那如何保证消息的顺序性？
 * 8.如果让你写一个消息队列，该如何进行架构设计啊？说一下你的思路
 * <p>
 * 1.你说一下你们在项目里是怎么用消息队列的？
 * <p>
 * 每一个MQ的优点和缺点是什么？每一个MQ没有绝对的好坏，
 * 但是就是看用在哪个场景可以扬长避短，利用其优势，规避其劣势。
 * <p>
 * 2.那你说说用消息队列都有什么优点和缺点？
 * 优点：异步 解耦 削峰
 * 缺点：1.系统可用性降低 2.系统复杂性提高 3.一直性问题
 * <p>
 * 3.kafka、activemq、rabbitmq、rocketmq都有什么区别？
 * 单机吞吐量：
 * ActiveMQ：万级，吞吐量比RocketMQ和Kafka要低了一个数量级
 * rabbitmq：万级，吞吐量比RocketMQ和Kafka要低了一个数量级
 * rocketmq：10万级，RocketMQ也是可以支撑高吞吐的一种MQ
 * kafka：10万级别，这是kafka最大的优点，就是吞吐量高。一般配合大数据类的系统来进行实时数据计算、日志采集等场景
 * topic数量对吞吐量的影响：
 * ActiveMQ：
 * rabbitmq：
 * rocketmq：topic可以达到几百，几千个的级别，吞吐量会有较小幅度的下降 这是RocketMQ的一大优势，在同等机器下，可以支撑大量的topic
 * kafka：topic从几十个到几百个的时候，吞吐量会大幅度下降 所以在同等机器下，kafka尽量保证topic数量不要过多。如果要支撑大规模topic，需要增加更多的机器资源
 * 时效性：
 * ActiveMQ：ms级
 * rabbitmq：微秒级，这是rabbitmq的一大特点，延迟是最低的
 * rocketmq：ms级
 * kafka：ms级
 * 可用性：
 * ActiveMQ：高，基于主从架构实现高可用性
 * rabbitmq：高，基于主从架构实现高可用性
 * rocketmq：非常高，分布式架构
 * kafka：非常高，kafka是分布式的，一个数据多个副本，少数机器宕机，不会丢失数据，不会导致不可用
 * 消息可靠性：
 * ActiveMQ：有较低的概率丢失数据
 * rabbitmq：
 * rocketmq：经过参数优化配置，可以做到0丢失
 * kafka：经过参数优化配置，消息可以做到0丢失
 * 功能支持：
 * ActiveMQ：MQ领域的功能极其完备
 * rabbitmq：基于erlang开发，所以并发能力很强，性能极其好，延时很低
 * rocketmq：MQ功能较为完善，还是分布式的，扩展性好
 * kafka：功能较为简单，主要支持简单的MQ功能，在大数据领域的实时计算以及日志采集被大规模使用，是事实上的标准
 * <p>
 * <p>
 * 4.那你们是如何保证消息队列的高可用啊？
 * （1）RabbitMQ的高可用性
 * RabbitMQ是比较有代表性的，因为是基于主从做高可用性的，我们就以他为例子讲解第一种MQ的高可用性怎么实现。
 * rabbitmq有三种模式：单机模式，普通集群模式，镜像集群模式
 * 1）单机模式
 * 就是demo级别的，一般就是你本地启动了玩玩儿的，没人生产用单机模式
 * 2）普通集群模式
 * 意思就是在多台机器上启动多个rabbitmq实例，每个机器启动一个。但是你创建的queue，
 * 只会放在一个rabbtimq实例上，但是每个实例都同步queue的元数据。完了你消费的时候，实际上如果连接到了另外一个实例，
 * 那么那个实例会从queue所在实例上拉取数据过来。
 * <p>
 * 这种方式确实很麻烦，也不怎么好，没做到所谓的分布式，就是个普通集群。
 * 因为这导致你要么消费者每次随机连接一个实例然后拉取数据，要么固定连接那个queue所在实例消费数据，前者有数据拉取的开销，后者导致单实例性能瓶颈。
 * <p>
 * 而且如果那个放queue的实例宕机了，会导致接下来其他实例就无法从那个实例拉取，如果你开启了消息持久化，让rabbitmq落地存储消息的话，
 * 消息不一定会丢，得等这个实例恢复了，然后才可以继续从这个queue拉取数据。
 * 所以这个事儿就比较尴尬了，这就没有什么所谓的高可用性可言了，这方案主要是提高吞吐量的，就是说让集群中多个节点来服务某个queue的读写操作。
 * <p>
 * 3）镜像集群模式
 * <p>
 * 这种模式，才是所谓的rabbitmq的高可用模式，跟普通集群模式不一样的是，你创建的queue，无论元数据还是queue里的消息都会存在于多个实例上，
 * 然后每次你写消息到queue的时候，都会自动把消息到多个实例的queue里进行消息同步。
 * <p>
 * 这样的话，好处在于，你任何一个机器宕机了，没事儿，别的机器都可以用。
 * 坏处在于，
 * 第一，这个性能开销也太大了吧，消息同步所有机器，导致网络带宽压力和消耗很重！
 * 第二，这么玩儿，就没有扩展性可言了，如果某个queue负载很重，你加机器，新增的机器也包含了这个queue的所有数据，并没有办法线性扩展你的queue
 * <p>
 * 那么怎么开启这个镜像集群模式呢？我这里简单说一下，避免面试人家问你你不知道，其实很简单rabbitmq有很好的管理控制台，
 * 就是在后台新增一个策略，这个策略是镜像集群模式的策略，
 * 指定的时候可以要求数据同步到所有节点的，也可以要求就同步到指定数量的节点，然后你再次创建queue的时候，
 * 应用这个策略，就会自动将数据同步到其他的节点上去了。
 * <p>
 * （2）kafka的高可用性
 * <p>
 * kafka一个最基本的架构认识：多个broker组成，每个broker是一个节点；你创建一个topic，这个topic可以划分为多个partition，
 * 每个partition可以存在于不同的broker上，每个partition就放一部分数据。
 * <p>
 * 这就是天然的分布式消息队列，就是说一个topic的数据，是分散放在多个机器上的，每个机器就放一部分数据。
 * <p>
 * 实际上rabbitmq之类的，并不是分布式消息队列，他就是传统的消息队列，只不过提供了一些集群、HA的机制而已
 * ，因为无论怎么玩儿，rabbitmq一个queue的数据都是放在一个节点里的，镜像集群下，也是每个节点都放这个queue的完整数据。
 * <p>
 * kafka 0.8以前，是没有HA机制的，就是任何一个broker宕机了，那个broker上的partition就废了，没法写也没法读，
 * 没有什么高可用性可言。
 * <p>
 * kafka 0.8以后，提供了HA机制，就是replica副本机制。每个partition的数据都会同步到吉他机器上，
 * 形成自己的多个replica副本。然后所有replica会选举一个leader出来，那么生产和消费都跟这个leader打交道，
 * 然后其他replica就是follower。写的时候，leader会负责把数据同步到所有follower上去，读的时候就直接读leader上数据即可。
 * 只能读写leader？很简单，要是你可以随意读写每个follower，那么就要care数据一致性的问题，系统复杂度太高，很容易出问题。
 * kafka会均匀的将一个partition的所有replica分布在不同的机器上，这样才可以提高容错性。
 * <p>
 * 这么搞，就有所谓的高可用性了，因为如果某个broker宕机了，没事儿，那个broker上面的partition在其他机器上都有副本的，
 * 如果这上面有某个partition的leader，那么此时会重新选举一个新的leader出来，大家继续读写那个新的leader即可。这就有所谓的高可用性了。
 * <p>
 * 写数据的时候，生产者就写leader，然后leader将数据落地写本地磁盘，
 * 接着其他follower自己主动从leader来pull数据。一旦所有follower同步好数据了，就会发送ack给leader，
 * leader收到所有follower的ack之后，就会返回写成功的消息给生产者。（当然，这只是其中一种模式，还可以适当调整这个行为）
 * <p>
 * 消费的时候，只会从leader去读，但是只有一个消息已经被所有follower都同步成功返回ack的时候，这个消息才会被消费者读到。
 * <p>
 * 实际上这块机制，讲深了，是可以非常之深入的，但是我还是回到我们这个课程的主题和定位，聚焦面试，
 * 至少你听到这里大致明白了kafka是如何保证高可用机制的了，对吧？不至于一无所知，现场还能给面试官画画图。
 * 要遇上面试官确实是kafka高手，深挖了问，那你只能说不好意思，太深入的你没研究过。
 * <p>
 * 但是大家一定要明白，这个事情是要权衡的，你现在是要快速突击常见面试题体系，而不是要深入学习kafka，要深入学习kafka，
 * 你是没那么多时间的。你只能确保，你之前也许压根儿不知道这块，但是现在你知道了，面试被问到，
 * 你大概可以说一说。然后很多其他的候选人，也许还不如你，没看过这个，被问到了压根儿答不出来，相比之下，你还能说点出来，大概就是这个意思了。
 * <p>
 * <p>
 * 5.如何保证消息不被重复消费啊？如何保证消费的时候是幂等的啊？
 * （1）比如你拿个数据要写库，你先根据主键查一下，如果这数据都有了，你就别插入了，update一下好吧
 * <p>
 * （2）比如你是写redis，那没问题了，反正每次都是set，天然幂等性
 * <p>
 * （3）比如你不是上面两个场景，那做的稍微复杂一点，你需要让生产者发送每条数据的时候，里面加一个全局唯一的id，
 * 类似订单id之类的东西，然后你这里消费到了之后，先根据这个id去比如redis里查一下，之前消费过吗？如果没有消费过，
 * 你就处理，然后这个id写redis。如果消费过了，那你就别处理了，保证别重复处理相同的消息即可。
 * <p>
 * <p>
 * 6.如何保证消息的可靠性传输啊？要是消息丢失了怎么办啊？
 * 这个丢数据，mq一般分为两种，要么是mq自己弄丢了，要么是我们消费的时候弄丢了。咱们从rabbitmq和kafka分别来分析一下吧
 * <p>
 * （1）rabbitmq
 * <p>
 * 1）生产者弄丢了数据
 * <p>
 * 生产者将数据发送到rabbitmq的时候，可能数据就在半路给搞丢了，因为网络啥的问题，都有可能。
 * <p>
 * 此时可以选择用rabbitmq提供的事务功能，就是生产者发送数据之前开启rabbitmq事务（channel.txSelect），
 * 然后发送消息，如果消息没有成功被rabbitmq接收到，那么生产者会收到异常报错，此时就可以回滚事务（channel.txRollback），然后重试发送消息；
 * 如果收到了消息，那么可以提交事务（channel.txCommit）。但是问题是，rabbitmq事务机制一搞，基本上吞吐量会下来，因为太耗性能。
 * <p>
 * 所以一般来说，如果你要确保说写rabbitmq的消息别丢，可以开启confirm模式，在生产者那里设置开启confirm模式之后，
 * 你每次写的消息都会分配一个唯一的id，然后如果写入了rabbitmq中，rabbitmq会给你回传一个ack消息，告诉你说这个消息ok了。
 * 如果rabbitmq没能处理这个消息，会回调你一个nack接口，告诉你这个消息接收失败，你可以重试。
 * 而且你可以结合这个机制自己在内存里维护每个消息id的状态，如果超过一定时间还没接收到这个消息的回调，那么你可以重发。
 * <p>
 * 事务机制和cnofirm机制最大的不同在于，事务机制是同步的，你提交一个事务之后会阻塞在那儿，但是confirm机制是异步的，你发送个消息之后就可以发送下一个消息，然后那个消息rabbitmq接收了之后会异步回调你一个接口通知你这个消息接收到了。
 * <p>
 * 所以一般在生产者这块避免数据丢失，都是用confirm机制的。
 * <p>
 * 2）rabbitmq弄丢了数据
 * <p>
 * 就是rabbitmq自己弄丢了数据，这个你必须开启rabbitmq的持久化，就是消息写入之后会持久化到磁盘，哪怕是rabbitmq自己挂了，
 * 恢复之后会自动读取之前存储的数据，一般数据不会丢。除非极其罕见的是，rabbitmq还没持久化，自己就挂了，可能导致少量数据会丢失的，但是这个概率较小。
 * <p>
 * 设置持久化有两个步骤，
 * 第一个是创建queue的时候将其设置为持久化的，这样就可以保证rabbitmq持久化queue的元数据，但是不会持久化queue里的数据；
 * 第二个是发送消息的时候将消息的deliveryMode设置为2，就是将消息设置为持久化的，此时rabbitmq就会将消息持久化到磁盘上去。
 * 必须要同时设置这两个持久化才行，rabbitmq哪怕是挂了，再次重启，也会从磁盘上重启恢复queue，恢复这个queue里的数据。
 * <p>
 * 而且持久化可以跟生产者那边的confirm机制配合起来，只有消息被持久化到磁盘之后，才会通知生产者ack了，
 * 所以哪怕是在持久化到磁盘之前，rabbitmq挂了，数据丢了，生产者收不到ack，你也是可以自己重发的。
 * <p>
 * 哪怕是你给rabbitmq开启了持久化机制，也有一种可能，就是这个消息写到了rabbitmq中，但是还没来得及持久化到磁盘上，结果不巧，
 * 此时rabbitmq挂了，就会导致内存里的一点点数据会丢失。
 * <p>
 * 3）消费端弄丢了数据
 * <p>
 * rabbitmq如果丢失了数据，主要是因为你消费的时候，刚消费到，还没处理，结果进程挂了，比如重启了，那么就尴尬了
 * rabbitmq认为你都消费了，这数据就丢了。
 * <p>
 * 这个时候得用rabbitmq提供的ack机制，简单来说，就是你关闭rabbitmq自动ack，可以通过一个api来调用就行，
 * 然后每次你自己代码里确保处理完的时候，再程序里ack一把。这样的话，如果你还没处理完，不就没有ack？
 * 那rabbitmq就认为你还没处理完，这个时候rabbitmq会把这个消费分配给别的consumer去处理，消息是不会丢的。
 * <p>
 * <p>
 * 7.那如何保证消息的顺序性？
 * 先看看顺序会错乱的俩场景
 * <p>
 * （1）rabbitmq：一个queue，多个consumer，这不明显乱了
 * （2）kafka：一个topic，一个partition，一个consumer，内部多线程，这不也明显乱了
 * <p>
 * 那如何保证消息的顺序性呢？简单简单
 * <p>
 * （1）rabbitmq：拆分多个queue，每个queue一个consumer，就是多一些queue而已，确实是麻烦点；或者就一个queue但是对应一个consumer，
 * 然后这个consumer内部用内存队列做排队，然后分发给底层不同的worker来处理
 * <p>
 * （2）kafka：一个topic，一个partition，一个consumer，内部单线程消费，写N个内存queue，然后N个线程分别消费一个内存queue即可
 * <p>
 * <p>
 * <p>
 * 8.如果让你写一个消息队列，该如何进行架构设计啊？说一下你的思路
 * （1）首先这个mq得支持可伸缩性吧，就是需要的时候快速扩容，就可以增加吞吐量和容量，那怎么搞？
 * 设计个分布式的系统呗，参照一下kafka的设计理念，broker -> topic -> partition，每个partition放一个机器，就存一部分数据。
 * 如果现在资源不够了，简单啊，给topic增加partition，然后做数据迁移，增加机器，不就可以存放更多数据，提供更高的吞吐量了？
 * <p>
 * （2）其次你得考虑一下这个mq的数据要不要落地磁盘吧？那肯定要了，落磁盘，才能保证别进程挂了数据就丢了。那落磁盘的时候怎么落啊？
 * 顺序写，这样就没有磁盘随机读写的寻址开销，磁盘顺序读写的性能是很高的，这就是kafka的思路。
 * <p>
 * （3）其次你考虑一下你的mq的可用性啊？这个事儿，具体参考我们之前可用性那个环节讲解的kafka的高可用保障机制。
 * 多副本 -> leader & follower -> broker挂了重新选举leader即可对外服务。
 * <p>
 * （4）能不能支持数据0丢失啊？可以的，参考我们之前说的那个kafka数据零丢失方案
 */
public class RabbitMq {
}
