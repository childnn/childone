package org.anonymous.sample;

/**
 * 1.redis replication的核心机制
 * 2.redis 两种数据丢失的情况
 * 3.解决异步复制和脑裂导致的数据丢失
 * 4.
 * <p>
 * 5.redis cluster集群模式的原理
 * 6.redis cluster的核心原理分析：gossip通信、jedis smart定位、主备切换
 * 7.你能说说redis的并发竞争问题该如何解决吗？
 * 8.
 * <p>
 * <p>
 * 1、redis replication的核心机制
 * <p>
 * （1）redis采用异步方式复制数据到slave节点，不过redis 2.8开始，slave node会周期性地确认自己每次复制的数据量
 * （2）一个master node是可以配置多个slave node的
 * （3）slave node也可以连接其他的slave node
 * （4）slave node做复制的时候，是不会block master node的正常工作的
 * （5）slave node在做复制的时候，也不会block对自己的查询操作，它会用旧的数据集来提供服务; 但是复制完成的时候，需要删除旧数据集，加载新数据集，这个时候就会暂停对外服务了
 * （6）slave node主要用来进行横向扩容，做读写分离，扩容的slave node可以提高读的吞吐量
 * <p>
 * <p>
 * 2.主备切换的过程，可能会导致数据丢失
 * <p>
 * （1）异步复制导致的数据丢失
 * <p>
 * 因为master -> slave的复制是异步的，所以可能有部分数据还没复制到slave，master就宕机了，此时这些部分数据就丢失了
 * <p>
 * （2）脑裂导致的数据丢失
 * <p>
 * 脑裂，也就是说，某个master所在机器突然脱离了正常的网络，跟其他slave机器不能连接，但是实际上master还运行着
 * <p>
 * 此时哨兵可能就会认为master宕机了，然后开启选举，将其他slave切换成了master
 * <p>
 * 这个时候，集群里就会有两个master，也就是所谓的脑裂
 * <p>
 * 此时虽然某个slave被切换成了master，但是可能client还没来得及切换到新的master，还继续写向旧master的数据可能也丢失了
 * <p>
 * 因此旧master再次恢复的时候，会被作为一个slave挂到新的master上去，自己的数据会清空，重新从新的master复制数据
 * <p>
 * <p>
 * 3、解决异步复制和脑裂导致的数据丢失
 * <p>
 * min-slaves-to-write 1
 * min-slaves-max-lag 10
 * <p>
 * 要求至少有1个slave，数据复制和同步的延迟不能超过10秒
 * <p>
 * 如果说一旦所有的slave，数据复制和同步的延迟都超过了10秒钟，那么这个时候，master就不会再接收任何请求了
 * <p>
 * 上面两个配置可以减少异步复制和脑裂导致的数据丢失
 * <p>
 * （1）减少异步复制的数据丢失
 * <p>
 * 有了min-slaves-max-lag这个配置，就可以确保说，一旦slave复制数据和ack延时太长，就认为可能master宕机后损失的数据太多了，
 * 那么就拒绝写请求，这样可以把master宕机时由于部分数据未同步到slave导致的数据丢失降低的可控范围内
 * <p>
 * （2）减少脑裂的数据丢失
 * <p>
 * 如果一个master出现了脑裂，跟其他slave丢了连接，那么上面两个配置可以确保说，如果不能继续给指定数量的slave发送数据，
 * 而且slave超过10秒没有给自己ack消息，那么就直接拒绝客户端的写请求
 * <p>
 * 这样脑裂后的旧master就不会接受client的新数据，也就避免了数据丢失
 * <p>
 * 上面的配置就确保了，如果跟任何一个slave丢了连接，在10秒后发现没有slave给自己ack，那么就拒绝新的写请求
 * <p>
 * 因此在脑裂场景下，最多就丢失10秒的数据
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 5.redis cluster集群模式的原理
 * redis cluster
 * <p>
 * （1）自动将数据进行分片，每个master上放一部分数据
 * （2）提供内置的高可用支持，部分master不可用时，还是可以继续工作的
 * <p>
 * 在redis cluster架构下，每个redis要放开两个端口号，比如一个是6379，另外一个就是加10000的端口号，比如16379
 * <p>
 * 16379端口号是用来进行节点间通信的，也就是cluster bus的东西，集群总线。cluster bus的通信，用来进行故障检测，
 * 配置更新，故障转移授权
 * <p>
 * cluster bus用了另外一种二进制的协议，主要用于节点间进行高效的数据交换，占用更少的网络带宽和处理时间
 * <p>
 * 2、最老土的hash算法和弊端（大量缓存重建）
 * <p>
 * 3、一致性hash算法（自动缓存迁移）+虚拟节点（自动负载均衡）
 * <p>
 * 4、redis cluster的hash slot算法
 * <p>
 * redis cluster有固定的16384个hash slot，对每个key计算CRC16值，然后对16384取模，可以获取key对应的hash slot
 * <p>
 * redis cluster中每个master都会持有部分slot，比如有3个master，那么可能每个master持有5000多个hash slot
 * <p>
 * hash slot让node的增加和移除很简单，增加一个master，就将其他master的hash slot移动部分过去，减少一个master，
 * 就将它的hash slot移动到其他master上去
 * <p>
 * 移动hash slot的成本是非常低的
 * <p>
 * 客户端的api，可以对指定的数据，让他们走同一个hash slot，通过hash tag来实现
 * <p>
 * <p>
 * <p>
 * 6.redis cluster的核心原理分析：gossip通信、jedis smart定位、主备切换
 * <p>
 * 一、节点间的内部通信机制
 * <p>
 * 1、基础通信原理
 * <p>
 * （1）redis cluster节点间采取gossip协议进行通信
 * <p>
 * 跟集中式不同，不是将集群元数据（节点信息，故障，等等）集中存储在某个节点上，而是互相之间不断通信，
 * 保持整个集群所有节点的数据是完整的
 * <p>
 * 维护集群的元数据用得，集中式，一种叫做gossip
 * <p>
 * 集中式：好处在于，元数据的更新和读取，时效性非常好，一旦元数据出现了变更，立即就更新到集中式的存储中，其他节点读取的时候立即就可以感知到;
 * 不好在于，所有的元数据的跟新压力全部集中在一个地方，可能会导致元数据的存储有压力
 * <p>
 * gossip：好处在于，元数据的更新比较分散，不是集中在一个地方，更新请求会陆陆续续，打到所有节点上去更新，有一定的延时，降低了压力;
 * 缺点，元数据更新有延时，可能导致集群的一些操作会有一些滞后
 * <p>
 * 我们刚才做reshard，去做另外一个操作，会发现说，configuration error，达成一致
 * <p>
 * （2）10000端口
 * <p>
 * 每个节点都有一个专门用于节点间通信的端口，就是自己提供服务的端口号+10000，比如7001，那么用于节点间通信的就是17001端口
 * <p>
 * 每隔节点每隔一段时间都会往另外几个节点发送ping消息，同时其他几点接收到ping之后返回pong
 * <p>
 * （3）交换的信息
 * <p>
 * 故障信息，节点的增加和移除，hash slot信息，等等
 * <p>
 * 2、gossip协议
 * <p>
 * gossip协议包含多种消息，包括ping，pong，meet，fail，等等
 * <p>
 * meet: 某个节点发送meet给新加入的节点，让新节点加入集群中，然后新节点就会开始与其他节点进行通信
 * <p>
 * redis-trib.rb add-node
 * <p>
 * 其实内部就是发送了一个gossip meet消息，给新加入的节点，通知那个节点去加入我们的集群
 * <p>
 * ping: 每个节点都会频繁给其他节点发送ping，其中包含自己的状态还有自己维护的集群元数据，互相通过ping交换元数据
 * <p>
 * 每个节点每秒都会频繁发送ping给其他的集群，ping，频繁的互相之间交换数据，互相进行元数据的更新
 * <p>
 * pong: 返回ping和meet，包含自己的状态和其他信息，也可以用于信息广播和更新
 * <p>
 * fail: 某个节点判断另一个节点fail之后，就发送fail给其他节点，通知其他节点，指定的节点宕机了
 * <p>
 * 3、ping消息深入
 * <p>
 * ping很频繁，而且要携带一些元数据，所以可能会加重网络负担
 * <p>
 * 每个节点每秒会执行10次ping，每次会选择5个最久没有通信的其他节点
 * <p>
 * 当然如果发现某个节点通信延时达到了cluster_node_timeout / 2，那么立即发送ping，避免数据交换延时过长，落后的时间太长了
 * <p>
 * 比如说，两个节点之间都10分钟没有交换数据了，那么整个集群处于严重的元数据不一致的情况，就会有问题
 * <p>
 * 所以cluster_node_timeout可以调节，如果调节比较大，那么会降低发送的频率
 * <p>
 * 每次ping，一个是带上自己节点的信息，还有就是带上1/10其他节点的信息，发送出去，进行数据交换
 * <p>
 * 至少包含3个其他节点的信息，最多包含总节点-2个其他节点的信息
 * <p>
 * -------------------------------------------------------------------------------------------------------
 * <p>
 * 二、面向集群的jedis内部实现原理
 * <p>
 * 开发，jedis，redis的java client客户端，redis cluster，jedis cluster api
 * <p>
 * jedis cluster api与redis cluster集群交互的一些基本原理
 * <p>
 * 1、基于重定向的客户端
 * <p>
 * redis-cli -c，自动重定向
 * <p>
 * （1）请求重定向
 * <p>
 * 客户端可能会挑选任意一个redis实例去发送命令，每个redis实例接收到命令，都会计算key对应的hash slot
 * <p>
 * 如果在本地就在本地处理，否则返回moved给客户端，让客户端进行重定向
 * <p>
 * cluster keyslot mykey，可以查看一个key对应的hash slot是什么
 * <p>
 * 用redis-cli的时候，可以加入-c参数，支持自动的请求重定向，redis-cli接收到moved之后，会自动重定向到对应的节点执行命令
 * <p>
 * （2）计算hash slot
 * <p>
 * 计算hash slot的算法，就是根据key计算CRC16值，然后对16384取模，拿到对应的hash slot
 * <p>
 * 用hash tag可以手动指定key对应的slot，同一个hash tag下的key，都会在一个hash slot中，比如set mykey1:{100}和set mykey2:{100}
 * <p>
 * （3）hash slot查找
 * <p>
 * 节点间通过gossip协议进行数据交换，就知道每个hash slot在哪个节点上
 * <p>
 * 2、smart jedis
 * <p>
 * （1）什么是smart jedis
 * <p>
 * 基于重定向的客户端，很消耗网络IO，因为大部分情况下，可能都会出现一次请求重定向，才能找到正确的节点
 * <p>
 * 所以大部分的客户端，比如java redis客户端，就是jedis，都是smart的
 * <p>
 * 本地维护一份hashslot -> node的映射表，缓存，大部分情况下，直接走本地缓存就可以找到hashslot -> node，不需要通过节点进行moved重定向
 * <p>
 * （2）JedisCluster的工作原理
 * <p>
 * 在JedisCluster初始化的时候，就会随机选择一个node，初始化hashslot -> node映射表，同时为每个节点创建一个JedisPool连接池
 * <p>
 * 每次基于JedisCluster执行操作，首先JedisCluster都会在本地计算key的hashslot，然后在本地映射表找到对应的节点
 * <p>
 * 如果那个node正好还是持有那个hashslot，那么就ok; 如果说进行了reshard这样的操作，可能hashslot已经不在那个node上了，就会返回moved
 * <p>
 * 如果JedisCluter API发现对应的节点返回moved，那么利用该节点的元数据，更新本地的hashslot -> node映射表缓存
 * <p>
 * 重复上面几个步骤，直到找到对应的节点，如果重试超过5次，那么就报错，JedisClusterMaxRedirectionException
 * <p>
 * jedis老版本，可能会出现在集群某个节点故障还没完成自动切换恢复时，频繁更新hash slot，频繁ping节点检查活跃，导致大量网络IO开销
 * <p>
 * jedis最新版本，对于这些过度的hash slot更新和ping，都进行了优化，避免了类似问题
 * <p>
 * （3）hashslot迁移和ask重定向
 * <p>
 * 如果hash slot正在迁移，那么会返回ask重定向给jedis
 * <p>
 * jedis接收到ask重定向之后，会重新定位到目标节点去执行，但是因为ask发生在hash slot迁移过程中，所以JedisCluster API收到ask是不会更新hashslot本地缓存
 * <p>
 * 已经可以确定说，hashslot已经迁移完了，moved是会更新本地hashslot->node映射表缓存的
 * <p>
 * -------------------------------------------------------------------------------------------------------
 * <p>
 * 三、高可用性与主备切换原理
 * <p>
 * redis cluster的高可用的原理，几乎跟哨兵是类似的
 * <p>
 * 1、判断节点宕机
 * <p>
 * 如果一个节点认为另外一个节点宕机，那么就是pfail，主观宕机
 * <p>
 * 如果多个节点都认为另外一个节点宕机了，那么就是fail，客观宕机，跟哨兵的原理几乎一样，sdown，odown
 * <p>
 * 在cluster-node-timeout内，某个节点一直没有返回pong，那么就被认为pfail
 * <p>
 * 如果一个节点认为某个节点pfail了，那么会在gossip ping消息中，ping给其他节点，如果超过半数的节点都认为pfail了，那么就会变成fail
 * <p>
 * 2、从节点过滤
 * <p>
 * 对宕机的master node，从其所有的slave node中，选择一个切换成master node
 * <p>
 * 检查每个slave node与master node断开连接的时间，如果超过了cluster-node-timeout * cluster-slave-validity-factor，那么就没有资格切换成master
 * <p>
 * 这个也是跟哨兵是一样的，从节点超时过滤的步骤
 * <p>
 * 3、从节点选举
 * <p>
 * 哨兵：对所有从节点进行排序，slave priority，offset，run id
 * <p>
 * 每个从节点，都根据自己对master复制数据的offset，来设置一个选举时间，offset越大（复制数据越多）的从节点，选举时间越靠前，优先进行选举
 * <p>
 * 所有的master node开始slave选举投票，给要进行选举的slave进行投票，如果大部分master node（N/2 + 1）都投票给了某个从节点，那么选举通过，那个从节点可以切换成master
 * <p>
 * 从节点执行主备切换，从节点切换为主节点
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 7、你能说说redis的并发竞争问题该如何解决吗？
 */
public class Redis3 {


}
