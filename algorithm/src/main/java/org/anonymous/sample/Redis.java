package org.anonymous.sample;

/**
 * Redis 持久化
 * RDB文件保存过程
 * (1)redis调用fork,现在有了子进程和父进程。
 * (2)父进程继续处理client请求，子进程负责将内存内容写入到临时文件。
 * 由于os的写时复制机制（copy on write)父子进程会共享相同的物理页面，当父进程处理写请求时os会为父进程要修改的页面创建副本，
 * 而不是写共享的页面。所以子进程的地址空间内的数据是fork时刻整个数据库的一个快照。
 * (3)当子进程将快照写入临时文件完毕后，用临时文件替换原来的快照文件，然后子进程退出
 * AOF重写
 * 你可以会想，每一条写命令都生成一条日志，那么AOF文件是不是会很大？答案是肯定的，AOF文件会越来越大，所以Redis又提供了一个功能，
 * 叫做AOF rewrite。其功能就是重新生成一份AOF文件，新的AOF文件中一条记录的操作只会有一次，而不像一份老文件那样，可能记录了对同一个值的多次操作。
 * 手动触发： bgrewriteaof
 * 自动触发就是根据配置规则来触发，当然自动触发的整体时间还跟Redis的定时任务频率有关系。
 * <p>
 * Redis为什么这么快？
 * Redis采用的是基于内存的单进程单线程模型的 KV 数据库，由C语言编写，官方提供的数据是可以达到100000+的QPS（每秒内查询次数）。
 * 这个数据不比采用单进程多线程的同样基于内存的 KV 数据库 Memcached 差！原因如下：
 * 1、完全基于内存，绝大部分请求是纯粹的内存操作，非常快速；
 * 2、数据结构简单，对数据操作也简单，Redis中的数据结构是专门进行设计的；
 * 3、采用单线程，避免了不必要的上下文切换和竞争条件，也不存在多进程或者多线程导致的切换而消耗CPU，
 * 不用去考虑各种锁的问题，不存在加锁释放锁的操作，也不可能出现死锁而导致的性能消耗；
 * 4、使用多路I/O复用模型，非阻塞IO；
 * 5、使用的底层模型不同，底层实现方式以及与客户端之间通信的应用协议不一样，Redis直接构建了自己的VM机制。
 * 多路I/O复用模型
 * 多路I/O复用模型是利用 select、poll、epoll 可以同时监察多个流的 I/O 事件的能力，在空闲的时候，会把当前线程阻塞掉，
 * 当有一个或多个流有 I/O 事件时，就从阻塞态中唤醒，于是程序就会轮询一遍所有的流（epoll 是只轮询那些真正发出了事件的流），
 * 并且只依次顺序的处理就绪的流，这种做法就避免了大量的无用操作。这里“多路”指的是多个网络连接，
 * “复用”指的是复用同一个线程。采用多路 I/O 复用技术可以让单个线程高效的处理多个连接请求（尽量减少网络 IO 的时间消耗）。
 * Redis事务
 * Redis中的事务(transaction)是一组命令的集合。事务同命令一样都是Redis最小的执行单位，一个事务中的命令要么都执行，要么都不执行。
 * Redis事务的实现需要 MULTI 和 EXEC 两个命令，事务开始的时候先向Redis服务器发送 MULTI 命令，然后依次发送需要在本次事务中处理的命令，
 * 最后再发送 EXEC 命令表示事务命令结束。
 * <p>
 * <p>
 * <p>
 * 2.1.4  redis主从复制原理、断点续传、无磁盘化复制、过期key处理
 * 1、主从架构的核心原理
 * <p>
 * 当启动一个slave node的时候，它会发送一个PSYNC命令给master node
 * <p>
 * 如果这是slave node重新连接master node，那么master node仅仅会复制给slave部分缺少的数据;
 * 否则如果是slave node第一次连接master node，那么会触发一次full resynchronization
 * <p>
 * 开始full resynchronization的时候，master会启动一个后台线程，开始生成一份RDB快照文件，
 * 同时还会将从客户端收到的所有写命令缓存在内存中。RDB文件生成完毕之后，master会将这个RDB发送给slave，
 * slave会先写入本地磁盘，然后再从本地磁盘加载到内存中。然后master会将内存中缓存的写命令发送给slave，slave也会同步这些数据。
 * <p>
 * slave node如果跟master node有网络故障，断开了连接，会自动重连。master如果发现有多个slave node都来重新连接，
 * 仅仅会启动一个rdb save操作，用一份数据服务所有slave node。
 * <p>
 * 2、主从复制的断点续传
 * <p>
 * 从redis 2.8开始，就支持主从复制的断点续传，如果主从复制过程中，网络连接断掉了，
 * 那么可以接着上次复制的地方，继续复制下去，而不是从头开始复制一份
 * <p>
 * master node会在内存中常见一个backlog，master和slave都会保存一个replica offset还有一个master id，
 * offset就是保存在backlog中的。如果master和slave网络连接断掉了，slave会让master从上次的replica offset开始继续复制
 * <p>
 * 但是如果没有找到对应的offset，那么就会执行一次resynchronization
 * <p>
 * 3、无磁盘化复制
 * <p>
 * master在内存中直接创建rdb，然后发送给slave，不会在自己本地落地磁盘了
 * <p>
 * repl-diskless-sync
 * repl-diskless-sync-delay，等待一定时长再开始复制，因为要等更多slave重新连接过来
 * <p>
 * 4、过期key处理
 * <p>
 * slave不会过期key，只会等待master过期key。如果master过期了一个key，或者通过LRU淘汰了一个key，那么会模拟一条del命令发送给slave。
 * <p>
 * 2.1.5 redis replication的完整流运行程和原理
 * <p>
 * 1、复制的完整流程
 * <p>
 * （1）slave node启动，仅仅保存master node的信息，包括master node的host和ip，但是复制流程没开始
 * <p>
 * master host和ip是从哪儿来的，redis.conf里面的slaveof配置的
 * <p>
 * （2）slave node内部有个定时任务，每秒检查是否有新的master node要连接和复制，如果发现，就跟master node建立socket网络连接
 * （3）slave node发送ping命令给master node
 * （4）口令认证，如果master设置了requirepass，那么salve node必须发送masterauth的口令过去进行认证
 * （5）master node第一次执行全量复制，将所有数据发给slave node
 * （6）master node后续持续将写命令，异步复制给slave node
 * <p>
 * 2、数据同步相关的核心机制
 * <p>
 * 指的就是第一次slave连接msater的时候，执行的全量复制，那个过程里面你的一些细节的机制
 * <p>
 * （1）master和slave都会维护一个offset
 * <p>
 * master会在自身不断累加offset，slave也会在自身不断累加offset
 * slave每秒都会上报自己的offset给master，同时master也会保存每个slave的offset
 * <p>
 * 这个倒不是说特定就用在全量复制的，主要是master和slave都要知道各自的数据的offset，才能知道互相之间的数据不一致的情况
 * <p>
 * （2）backlog
 * <p>
 * master node有一个backlog，默认是1MB大小
 * master node给slave node复制数据时，也会将数据在backlog中同步写一份
 * backlog主要是用来做全量复制中断候的增量复制的
 * <p>
 * （3）master run id
 * <p>
 * info server，可以看到master run id
 * 如果根据host+ip定位master node，是不靠谱的，如果master node重启或者数据出现了变化，那么slave node应该根据不同的run id区分，
 * run id不同就做全量复制
 * 如果需要不更改run id重启redis，可以使用redis-cli debug reload命令
 * <p>
 * （4）psync
 * <p>
 * 从节点使用psync从master node进行复制，psync runid offset
 * master node会根据自身的情况返回响应信息，可能是FULLRESYNC runid offset触发全量复制，可能是CONTINUE触发增量复制
 * <p>
 * 3、全量复制
 * <p>
 * （1）master执行bgsave，在本地生成一份rdb快照文件
 * （2）master node将rdb快照文件发送给salve node，如果rdb复制时间超过60秒（repl-timeout），
 * 那么slave node就会认为复制失败，可以适当调节大这个参数
 * （3）对于千兆网卡的机器，一般每秒传输100MB，6G文件，很可能超过60s
 * （4）master node在生成rdb时，会将所有新的写命令缓存在内存中，在salve node保存了rdb之后，再将新的写命令复制给salve node
 * （5）client-output-buffer-limit slave 256MB 64MB 60，如果在复制期间，内存缓冲区持续消耗超过64MB，或者一次性超过256MB，那么停止复制，复制失败
 * （6）slave node接收到rdb之后，清空自己的旧数据，然后重新加载rdb到自己的内存中，同时基于旧的数据版本对外提供服务
 * （7）如果slave node开启了AOF，那么会立即执行BGREWRITEAOF，重写AOF
 * <p>
 * rdb生成、rdb通过网络拷贝、slave旧数据的清理、slave aof rewrite，很耗费时间
 * <p>
 * 如果复制的数据量在4G~6G之间，那么很可能全量复制时间消耗到1分半到2分钟
 * <p>
 * 4、增量复制
 * <p>
 * （1）如果全量复制过程中，master-slave网络连接断掉，那么salve重新连接master时，会触发增量复制
 * （2）master直接从自己的backlog中获取部分丢失的数据，发送给slave node，默认backlog就是1MB
 * （3）msater就是根据slave发送的psync中的offset来从backlog中获取数据的
 * <p>
 * 5、heartbeat
 * <p>
 * 主从节点互相都会发送heartbeat信息
 * <p>
 * master默认每隔10秒发送一次heartbeat，salve node每隔1秒发送一个heartbeat
 * <p>
 * 6、异步复制
 * <p>
 * master每次接收到写命令之后，现在内部写入数据，然后异步发送给slave node
 * <p>
 * 2.1.6 redis 压力测试
 * <p>
 * 你如果要对自己刚刚搭建好的redis做一个基准的压测，测一下你的redis的性能和QPS（query per second）
 * <p>
 * redis自己提供的redis-benchmark压测工具，是最快捷最方便的，当然啦，这个工具比较简单，用一些简单的操作和场景去压测
 * <p>
 * 1、对redis读写分离架构进行压测，单实例写QPS+单实例读QPS
 * <p>
 * redis-3.2.8/src
 * <p>
 * ./redis-benchmark -h 192.168.31.187
 * <p>
 * -c <clients>       Number of parallel connections (default 50)
 * -n <requests>      Total number of requests (default 100000)
 * -d <size>          Data size of SET/GET value in bytes (default 2)
 * <p>
 * 根据你自己的高峰期的访问量，在高峰期，瞬时最大用户量会达到10万+，-c 100000，-n 10000000，-d 50
 * <p>
 * 各种基准测试，直接出来
 * <p>
 * <p>
 * 2.1.7 redis 怎么保证高可用？
 * 1、什么是99.99%高可用？
 * <p>
 * 架构上，高可用性，99.99%的高可用性
 * <p>
 * 讲的学术，99.99%，公式，系统可用的时间 / 系统故障的时间，365天，在365天 * 99.99%的时间内，你的系统都是可以哗哗对外提供服务的，那就是高可用性，99.99%
 * <p>
 * 系统可用的时间 / 总的时间 = 高可用性，然后会对各种时间的概念，说一大堆解释
 * <p>
 * 2、redis不可用是什么？单实例不可用？主从架构不可用？不可用的后果是什么？
 * <p>
 * 3、redis怎么才能做到高可用？
 * <p>
 * <p>
 * 2.1.8   redis哨兵架构的相关基础知识
 * <p>
 * 1、哨兵的介绍
 * <p>
 * sentinal，中文名是哨兵
 * <p>
 * 哨兵是redis集群架构中非常重要的一个组件，主要功能如下
 * <p>
 * （1）集群监控，负责监控redis master和slave进程是否正常工作
 * （2）消息通知，如果某个redis实例有故障，那么哨兵负责发送消息作为报警通知给管理员
 * （3）故障转移，如果master node挂掉了，会自动转移到slave node上
 * （4）配置中心，如果故障转移发生了，通知client客户端新的master地址
 * <p>
 * 哨兵本身也是分布式的，作为一个哨兵集群去运行，互相协同工作
 * <p>
 * （1）故障转移时，判断一个master node是宕机了，需要大部分的哨兵都同意才行，涉及到了分布式选举的问题
 * （2）即使部分哨兵节点挂掉了，哨兵集群还是能正常工作的，因为如果一个作为高可用机制重要组成部分的故障转移系统本身是单点的，那就很坑爹了
 * <p>
 * 目前采用的是sentinal 2版本，sentinal 2相对于sentinal 1来说，重写了很多代码，主要是让故障转移的机制和算法变得更加健壮和简单
 * <p>
 * 2、哨兵的核心知识
 * <p>
 * （1）哨兵至少需要3个实例，来保证自己的健壮性
 * （2）哨兵 + redis主从的部署架构，是不会保证数据零丢失的，只能保证redis集群的高可用性
 * （3）对于哨兵 + redis主从这种复杂的部署架构，尽量在测试环境和生产环境，都进行充足的测试和演练
 * <p>
 * 3、为什么redis哨兵集群只有2个节点无法正常工作？
 * <p>
 * 哨兵集群必须部署2个以上节点
 * <p>
 * 如果哨兵集群仅仅部署了个2个哨兵实例，quorum=1
 * <p>
 * +----+         +----+
 * | M1 |---------| R1 |
 * | S1 |         | S2 |
 * +----+         +----+
 * <p>
 * Configuration: quorum = 1
 * <p>
 * master宕机，s1和s2中只要有1个哨兵认为master宕机就可以还行切换，同时s1和s2中会选举出一个哨兵来执行故障转移
 * <p>
 * 同时这个时候，需要majority，也就是大多数哨兵都是运行的，2个哨兵的majority就是2（2的majority=2，3的majority=2，5的majority=3，4的majority=2），2个哨兵都运行着，就可以允许执行故障转移
 * <p>
 * 但是如果整个M1和S1运行的机器宕机了，那么哨兵只有1个了，此时就没有majority来允许执行故障转移，虽然另外一台机器还有一个R1，但是故障转移不会执行
 * <p>
 * 4、经典的3节点哨兵集群
 * <p>
 * +----+
 * | M1 |
 * | S1 |
 * +----+
 * |
 * +----+    |    +----+
 * | R2 |----+----| R3 |
 * | S2 |         | S3 |
 * +----+         +----+
 * <p>
 * Configuration: quorum = 2，majority
 * <p>
 * 如果M1所在机器宕机了，那么三个哨兵还剩下2个，S2和S3可以一致认为master宕机，然后选举出一个来执行故障转移
 * <p>
 * 同时3个哨兵的majority是2，所以还剩下的2个哨兵运行着，就可以允许执行故障转移
 * <p>
 * 2.1.9   redis哨兵主备切换的数据丢失问题：异步复制、集群脑裂
 * 1、两种数据丢失的情况
 * 2、解决异步复制和脑裂导致的数据丢失
 * <p>
 * ------------------------------------------------------------------
 * <p>
 * 1、两种数据丢失的情况
 * <p>
 * 主备切换的过程，可能会导致数据丢失
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
 * ------------------------------------------------------------------
 * <p>
 * 2、解决异步复制和脑裂导致的数据丢失
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
 * 2.2.0   redis哨兵的多个核心底层原理的深入解析（包含slave选举算法）
 * <p>
 * 1、sdown和odown转换机制
 * <p>
 * sdown和odown两种失败状态
 * <p>
 * sdown是主观宕机，就一个哨兵如果自己觉得一个master宕机了，那么就是主观宕机
 * <p>
 * odown是客观宕机，如果quorum数量的哨兵都觉得一个master宕机了，那么就是客观宕机
 * <p>
 * sdown达成的条件很简单，如果一个哨兵ping一个master，超过了is-master-down-after-milliseconds指定的毫秒数之后，就主观认为master宕机
 * <p>
 * sdown到odown转换的条件很简单，如果一个哨兵在指定时间内，收到了quorum指定数量的其他哨兵也认为那个master是sdown了，那么就认为是odown了，客观认为master宕机
 * <p>
 * 2、哨兵集群的自动发现机制
 * <p>
 * 哨兵互相之间的发现，是通过redis的pub/sub系统实现的，每个哨兵都会往__sentinel__:hello这个channel里发送一个消息，这时候所有其他哨兵都可以消费到这个消息，并感知到其他的哨兵的存在
 * <p>
 * 每隔两秒钟，每个哨兵都会往自己监控的某个master+slaves对应的__sentinel__:hello channel里发送一个消息，内容是自己的host、ip和runid还有对这个master的监控配置
 * <p>
 * 每个哨兵也会去监听自己监控的每个master+slaves对应的__sentinel__:hello channel，然后去感知到同样在监听这个master+slaves的其他哨兵的存在
 * <p>
 * 每个哨兵还会跟其他哨兵交换对master的监控配置，互相进行监控配置的同步
 * <p>
 * 3、slave配置的自动纠正
 * <p>
 * 哨兵会负责自动纠正slave的一些配置，比如slave如果要成为潜在的master候选人，哨兵会确保slave在复制现有master的数据; 如果slave连接到了一个错误的master上，比如故障转移之后，那么哨兵会确保它们连接到正确的master上
 * <p>
 * 4、slave->master选举算法
 * <p>
 * 如果一个master被认为odown了，而且majority哨兵都允许了主备切换，那么某个哨兵就会执行主备切换操作，此时首先要选举一个slave来
 * <p>
 * 会考虑slave的一些信息
 * <p>
 * （1）跟master断开连接的时长
 * （2）slave优先级
 * （3）复制offset
 * （4）run id
 * <p>
 * 如果一个slave跟master断开连接已经超过了down-after-milliseconds的10倍，外加master宕机的时长，那么slave就被认为不适合选举为master
 * <p>
 * (down-after-milliseconds * 10) + milliseconds_since_master_is_in_SDOWN_state
 * <p>
 * 接下来会对slave进行排序
 * <p>
 * （1）按照slave优先级进行排序，slave priority越低，优先级就越高
 * （2）如果slave priority相同，那么看replica offset，哪个slave复制了越多的数据，offset越靠后，优先级就越高
 * （3）如果上面两个条件都相同，那么选择一个run id比较小的那个slave
 * <p>
 * 5、quorum和majority
 * <p>
 * 每次一个哨兵要做主备切换，首先需要quorum数量的哨兵认为odown，然后选举出一个哨兵来做切换，这个哨兵还得得到majority哨兵的授权，才能正式执行切换
 * <p>
 * 如果quorum < majority，比如5个哨兵，majority就是3，quorum设置为2，那么就3个哨兵授权就可以执行切换
 * <p>
 * 但是如果quorum >= majority，那么必须quorum数量的哨兵都授权，比如5个哨兵，quorum是5，那么必须5个哨兵都同意授权，才能执行切换
 * <p>
 * 6、configuration epoch
 * <p>
 * 哨兵会对一套redis master+slave进行监控，有相应的监控的配置
 * <p>
 * 执行切换的那个哨兵，会从要切换到的新master（salve->master）那里得到一个configuration epoch，这就是一个version号，
 * 每次切换的version号都必须是唯一的
 * <p>
 * 如果第一个选举出的哨兵切换失败了，那么其他哨兵，会等待failover-timeout时间，然后接替继续执行切换，此时会重新获取一个新的configuration epoch，作为新的version号
 * <p>
 * 7、configuraiton传播
 * <p>
 * 哨兵完成切换之后，会在自己本地更新生成最新的master配置，然后同步给其他的哨兵，就是通过之前说的pub/sub消息机制
 * <p>
 * 这里之前的version号就很重要了，因为各种消息都是通过一个channel去发布和监听的，所以一个哨兵完成一次新的切换之后，新的master配置是跟着新的version号的
 * <p>
 * 其他的哨兵都是根据版本号的大小来更新自己的master配置的
 * <p>
 * 2.2.1   redis cluster的自动化slave迁移
 * <p>
 * slave的自动迁移
 * <p>
 * 比如现在有10个master，每个有1个slave，然后新增了3个slave作为冗余，有的master就有2个slave了，有的master出现了salve冗余
 * <p>
 * 如果某个master的slave挂了，那么redis cluster会自动迁移一个冗余的slave给那个master
 * <p>
 * 只要多加一些冗余的slave就可以了
 * <p>
 * 为了避免的场景，就是说，如果你每个master只有一个slave，万一说一个slave死了，然后很快，master也死了，那可用性还是降低了
 * <p>
 * 但是如果你给整个集群挂载了一些冗余slave，那么某个master的slave死了，冗余的slave会被自动迁移过去，
 * 作为master的新slave，此时即使那个master也死了
 * <p>
 * 还是有一个slave会切换成master的
 * <p>
 * 之前有一个master是有冗余slave的，直接让其他master其中的一个slave死掉，然后看有冗余slave会不会自动挂载到那个master
 * <p>
 * <p>
 * 2.2.2   redis cluster的核心原理分析：gossip通信、jedis smart定位、主备切换
 * <p>
 * 一、节点间的内部通信机制
 * <p>
 * 1、基础通信原理
 * <p>
 * （1）redis cluster节点间采取gossip协议进行通信
 * <p>
 * 跟集中式不同，不是将集群元数据（节点信息，故障，等等）集中存储在某个节点上，而是互相之间不断通信，保持整个集群所有节点的数据是完整的
 * <p>
 * 维护集群的元数据用得，集中式，一种叫做gossip
 * <p>
 * 集中式：好处在于，元数据的更新和读取，时效性非常好，一旦元数据出现了变更，立即就更新到集中式的存储中，
 * 其他节点读取的时候立即就可以感知到; 不好在于，所有的元数据的跟新压力全部集中在一个地方，可能会导致元数据的存储有压力
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
 * jedis接收到ask重定向之后，会重新定位到目标节点去执行，但是因为ask发生在hash slot迁移过程中，
 * 所以JedisCluster API收到ask是不会更新hashslot本地缓存
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
 * 4、与哨兵比较
 * <p>
 * 整个流程跟哨兵相比，非常类似，所以说，redis cluster功能强大，直接集成了replication和sentinal的功能
 * <p>
 * <p>
 * 2.2.3   redis在实践中的一些常见问题以及优化思路
 * <p>
 * 1、fork耗时导致高并发请求延时
 * <p>
 * RDB和AOF的时候，其实会有生成RDB快照，AOF rewrite，耗费磁盘IO的过程，主进程fork子进程
 * <p>
 * fork的时候，子进程是需要拷贝父进程的空间内存页表的，也是会耗费一定的时间的
 * <p>
 * 一般来说，如果父进程内存有1个G的数据，那么fork可能会耗费在20ms左右，如果是10G~30G，那么就会耗费20 * 10，甚至20 * 30，也就是几百毫秒的时间
 * <p>
 * info stats中的latest_fork_usec，可以看到最近一次form的时长
 * <p>
 * redis单机QPS一般在几万，fork可能一下子就会拖慢几万条操作的请求时长，从几毫秒变成1秒
 * <p>
 * 优化思路
 * <p>
 * fork耗时跟redis主进程的内存有关系，一般控制redis的内存在10GB以内，slave -> master，全量复制
 * <p>
 * 2、AOF的阻塞问题
 * <p>
 * redis将数据写入AOF缓冲区，单独开一个线程做fsync操作，每秒一次
 * <p>
 * 但是redis主线程会检查两次fsync的时间，如果距离上次fsync时间超过了2秒，那么写请求就会阻塞
 * <p>
 * everysec，最多丢失2秒的数据
 * <p>
 * 一旦fsync超过2秒的延时，整个redis就被拖慢
 * <p>
 * 优化思路
 * <p>
 * 优化硬盘写入速度，建议采用SSD，不要用普通的机械硬盘，SSD，大幅度提升磁盘读写的速度
 * <p>
 * 3、主从复制延迟问题
 * <p>
 * 主从复制可能会超时严重，这个时候需要良好的监控和报警机制
 * <p>
 * 在info replication中，可以看到master和slave复制的offset，做一个差值就可以看到对应的延迟量
 * <p>
 * 如果延迟过多，那么就进行报警
 * <p>
 * 4、主从复制风暴问题
 * <p>
 * 如果一下子让多个slave从master去执行全量复制，一份大的rdb同时发送到多个slave，会导致网络带宽被严重占用
 * <p>
 * 如果一个master真的要挂载多个slave，那尽量用树状结构，不要用星型结构
 * <p>
 * 5、vm.overcommit_memory
 * <p>
 * 0: 检查有没有足够内存，没有的话申请内存失败
 * 1: 允许使用内存直到用完为止
 * 2: 内存地址空间不能超过swap + 50%
 * <p>
 * 如果是0的话，可能导致类似fork等操作执行失败，申请不到足够的内存空间
 * <p>
 * cat /proc/sys/vm/overcommit_memory
 * echo "vm.overcommit_memory=1" >> /etc/sysctl.conf
 * sysctl vm.overcommit_memory=1
 * <p>
 * 6、swapiness
 * <p>
 * cat /proc/version，查看linux内核版本
 * <p>
 * 如果linux内核版本<3.5，那么swapiness设置为0，这样系统宁愿swap也不会oom killer（杀掉进程）
 * 如果linux内核版本>=3.5，那么swapiness设置为1，这样系统宁愿swap也不会oom killer
 * <p>
 * 保证redis不会被杀掉
 * <p>
 * echo 0 > /proc/sys/vm/swappiness
 * echo vm.swapiness=0 >> /etc/sysctl.conf
 * <p>
 * 7、最大打开文件句柄
 * <p>
 * ulimit -n 10032 10032
 * <p>
 * 自己去上网搜一下，不同的操作系统，版本，设置的方式都不太一样
 * <p>
 * 8、tcp backlog
 * <p>
 * cat /proc/sys/net/core/somaxconn
 * echo 511 > /proc/sys/net/core/somaxconn
 */
public class Redis {

}
