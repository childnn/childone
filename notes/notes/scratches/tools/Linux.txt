package org.linux;

/**
 * xshell 传输文件:
 *
 * yum安装lrzsz工具：其命令为：yum -y install lrzsz
 * 查看是否安装成功，其命令为：rpm -qa|grep lrzsz
 * 上传: rz
 * 下载: sz 文件名
 *
 *
 *
 * windows 查询指定端口的 pid/强杀指定 pid：
 *      netstat -ano | findstr 1099
 *      taskkill -f -pid 3756
	 1、lsof -i:端口号
     2、netstat -tunlp|grep 端口号
	 文件上传目录： /home/fdfs_storage/data
	 /usr/local/fastdfs/fastdfs-nginx-module/src
	 
	强制关闭 yum 进程： rm -f /var/run/yum.pid

	都可以查看指定端口被哪个进程占用的情况
 * @author child
 * 2019/4/13 9:07
 *  linux 学习目标: 在 Linux 系统上安装 jdk,mysql,tomcat 等软件,搭建 web 环境
 *  常用命令(掌握)
 *    磁盘命令(cd/ll)
 *    文件夹/文件创建命令(mkdir/touch)
 *    文件浏览(cat/more/less/head/tail/find)
 *    文件操作(cp/mv/rm)
 *    文件编辑(vi/vim 编辑模式(io) 命令模式)
 *      (esc 终止编写内容且可以进行复制(yy)/粘贴(p)/删除(dd))
 *    底行模式(shift+: 保存退出(wq)或者不保存强制退出(q!))
 *    压缩解压(tar: -cvf/-xvf  tar.gz: -zcvf/-zxvf)
 *    权限命令(chmod)
 *    其他命令(ps/kill/ifconfig/grep/|)
 *
 * windows 操作系统的 缺点:
 *  1. 正版收费
 *  2. 系统长时间运行不稳定,变慢,容易死机
 *  3. 病毒攻击
 *
 * 根据原生程度不同.分类:
 *  1.内核版本: 在 Linux 之父领导下的内核小组开发维护的系统内核版本号
 *  2.发行版本: 一些个人/组织/公司 在内核版本基础上进行二次开发而重新发行的版本号
 * 虚拟机:
 *   一种软件,可以使你在一台真实pc上同时运行多个 windows/linux 操作系统
 *
 * linux 目录结构
 *  命令 pwd: 显示当前用户所在位置(从根目录到当前位置的路径)
 *  /: 根目录(只有一个根盘符 -- 可以认为只有一个 C 盘)
 *   根目录下的文件夹(主要介绍 4 个):
 *      root: 超级管理员(root)的家 -- 只要是 root 用户登录进来的, 自动在 root 目录下
 *      home: 其他用户的家 -- 只要是其他用户登录进来的,自动在 home 目录下
 *      etc: 配置文件 -- 存放所有 linux 配置文件的目录
 *      usr: 用户安装软件的目录
 *
 * 一. 操作磁盘命令:(cd/ll)
 *     1. cd: 改变目录
 *       cd /               进入根目录
 *       cd /目录名          进入根目录下的指定目录
 *       cd 目录名           进入当前目录下的指定目录
 *       cd ~               回家 (cd root 用户: /root  其他用户: /home)
 *       cd -               后退(退到上一次的操作目录)
 *       cd ..              进入上一级目录
 *       cd ../etc          上一层指定目录里面找
 *       注意:
 *          cd /etc 进入根盘符下的 etc 目录 -- 绝对路径
 *          cd etc 进入当前目录下的 etc 目录 -- 相对路径
 *     2.ls: 展示当前目录下的资源
 *        ls -a      展示所有资源   包含 隐藏文件/文件夹
 *        ls -l (简写 ll)     展示非隐藏文件/文件夹的详细信息    以杠 - 开头的就是文件, 以 d 开头的就是文件夹
 *        ls -la      展示所有隐藏文件/文件夹的详细信息
 *        记住一个即可:
 *           ll    显示当前目录下的所有非隐藏文件/文件夹的详细信息
 * 二. 文件/文件夹 创建命令
 *     1. mkdir  创建文件夹
 *       mkdir 目录名                  在当前目录下创建指定目录名的文件夹
 *       mkdir -p 目录名/子目录名/..    在当前目录下创建指定目录名的多级文件夹
 *     2. 文件创建
 *        touch 文件名(包含后缀名)                 在当前目录下创建指定目录名的文件
 * 三. 文件浏览(查看文件内容)
 *   了解:
 *    1. cat 文件名          只能查看文件最后一部分内容(虚拟机屏幕大小的内容)
 *    2. more 文件名         分页查看所有内容
 *          回车  下一行
 *          空格  下一页
 *          退出   q
 *    掌握:
 *     3. less 文件名         分页查看文件所有内容
 *         上箭头  上一行
 *         下箭头  下一行
 *          空格   下一页
 *          b      上一页
 *          q      退出
 *        参数:
 *          less -N 文件名   显示行号的查看文件所有内容
 *          less -m 文件名   以百分比形式查看文件所有内容
 *     4. head 文件名         查看文件头部信息(默认 前 10 行)
 *       tail 文件名         查看文件尾部信息(默认 最后 10 行)
 *       指定查看行数:
 *          head/tail -行数 文件名       查看指定行数的信息
 *     5. find / -name '文件名'           查找文件位置(显示从根盘符开始的绝对路径)(文件名可以使用 * 号补全)
 *  基本操作:
 *      clear 清空当前页面
 *      tab键 补全
 *      远程连接 Linux 服务器软件 : crt/ssh
 * 四. 文件操作命令
 *   1.cp 拷贝(copy)
 *      cp 文件名 ../              将当前目录下的指定文件复制到 上一级目录
 *      cp 文件名 ../新文件名       将当前目录下的指定文件复制到 上一级目录,并重命名为 指定的新文件名
 *      cp 文件名 指定目录名        将当前目录下的指定文件复制到 指定目录(当前目录下的指定目录:下级目录)
 *      cp 文件名 指定目录名/新文件名  将当前目录下的指定文件复制到 指定目录 并重命名为 指定新文件名
 *      cp 文件名 新文件名           将当前目录下的指定文件复制到 当前目录下 并重命名为 指定新文件名
 *      cp -r 目录名 指定目录名     递归整个目录到 指定目录下(包含目录和目录下的所有文件/文件夹)
 *           如果 指定目录 存在,则会将包括 目标目录 本身在内的所有文件夹/文件, 复制到指定目录下
 *           如果 指定目录不存在,则会将 目标目录下的所有文件/文件夹(不包括目标目录本身), 复制到指定目录下
 *   2.mv 剪切 (move)
 *      mv 文件名 ../              将当前目录下的指定文件 移动到(剪切) 到上一级目录
 *      .... 同 cp 复制命令
 *      mv 文件名 新文件名          将当前目录下的指定文件 重命名为 新文件名 (目录也可以)
 *      mv 目录名 指定目录名        将当前目录下的 指定目录 剪切到 指定目录下(不需要递归)
 *   3. rm 删除 (remove)
 *      删除当前目录下的指定文件/文件夹
 *      rm 文件                    带询问的删除当前目录下的指定文件  y/n
 *      rm -f 文件                 不带询问的删除
 *      rm -r 目录名                带询问的递归删除当前目录下的指定目录(包括指定文件夹)
 *      rm -rf 目录名               不带询问的递归删除
 *      只删除目录下的所有文件/文件夹,而不删除 该目录
 *      rm -rf ./*                 删除当前目录下的所有文件(不包括当前目录)
 *      rm -rf /*                  删除根目录下的所有文件
 * 五. 文件编辑命令(操作文件内容并保存)
 *    vi 或 vim
 *    编辑模式: 对具体内容进行操作(写入内容)
 *    命令模式: 对内容进行操作 复制整行内容 粘贴整行内容 删除整行内容
 *    底行模式: 保存编辑 退出
 *
 *    vim 文件名(打开文件) --> i 或 o --> 编辑模式(编写内容) --> esc 键 --> 命令模式(终止编辑模式,可以进行复制粘贴 yy 复制, p 粘贴, dd 删除)
 *     --> : (冒号) --> 底行模式(保存退出或者不保存退出)
 *     底行模式:
 *      wq    保存并退出
 *      q!    强制退出不保存
 *    如果在编辑文件过程中没有保存,强制退出后,再次进入编辑该文件时,会报错(上一次的编辑文件未保存),此时,可以选择 e 重新进入编辑
 *    也可以 q 退出, 命令 ls -la (查看所有隐藏文件详细信息) --然后--  rm -rf 文件名   删除 .swp 结尾的隐藏文件,此时,
 *    再次进入编辑该文件,就可以编辑了(vim 文件名), 之前的内容不会保存
 *
 *     命令模式常用快捷键
 *      dd    删除当前行
 *      yy    复制当前行
 *      p     粘贴
 *    替换文本中的指定字符串
 *    底行模式下:
 *    :s/orignal_string/dest_string     # 替换当前行第一个 orignal_string 为 dest_string
 *    :s/orignal_string/dest_string/g   # 替换当前行所有 orignal_string 为 dest_string
 *    :n,$s/orignal_string/dest_string  # 替换第 n 行开始到最后一行 每行 第一个 orignal_string 为 dest_string
 *    :n,$s/orignal_string/dest_string/g  # 替换第 n 行开始到最后一行 每行 所有 orignal_string 为 dest_string
 *     # n 为数字, 若 n 为 . 表示当前行
 *     # 可以使用 # 作为分隔符,此时中间出现的 / 不会作为分隔符而作为替换字符
 *     :%s#/usr/bin#/bin#g   # 把文件暗中所有路径 /usr/bin 替换为 /bin
 *     -----
 *     直接替换文中的字符串,不用打开文件,可以批量替换文件
 *     $ perl -p -i.bak -e 's/\bfoo\b/bar/g' *.c
 *     		# 替换所有 .c 文件中 foo 为 bar, 旧文件备份成 .bak
 *     $ perl -p -i -e 's/test/demo/g' ./aa.txt ./aa.txt.bak
 *     		# 将当前文件夹下的 aa.txt 和 aa.txt.bak 中的 test 都替换为 demo
 *     $ perl -i.bak -pe 's/(\d+)/ 1 + $1 /ge' file1 file2
 *     	    # 将每个文件中出现的数值加一
 *     	-a    自动分隔模式，用空格分隔$_并保存到@F中。相当于@F = split ”。分隔符可以使用-F参数指定
 * 		-F    指定-a的分隔符，可以使用正则表达式
 * 		-e    执行指定的脚本。
 * 		-i<扩展名>   原地替换文件，并将旧文件用指定的扩展名备份。不指定扩展名则不备份。
 * 		-l    对输入内容自动chomp，对输出内容自动添加换行
 * 		-n    自动循环，相当于 while(<>) { 脚本; }
 * 		-p    自动循环+自动输出，相当于 while(<>) { 脚本; print; }
 *    还有 sed 命令.....有需要再备注
 *
 * 六. 压缩/解压命令: linux 系统的压缩文件  .tar/.tar.gz
 *   .tar : 打包文件
 *   .tar.gz : 打包压缩文件
 *   命令: (主要掌握解压)
 *      tar -cvf 包名.tar 被打包文件/文件夹名      -- 打包 文件为 .tar
 *      tar -xvf 包名.tar                        -- 解包
 *      tar -zcvf 包名.tar.gz 被打包文件/文件夹    -- 打包 文件为 .tar.gz
 *      tar -zxvf 包名.tar.gz                     -- 解包
 * 七. 文件操作权限(了解) 查看文件详细信息 (ll 命令)
 *   - rw- r-- r-- 1 root root 1.txt
 *   按符号的序号
 *   1 : 文件类型    d 表示目录 - 表示文件
 *   234: 当前登录用户对该资源的操作权限(对 root 用户无效) :  rwx 读写执行 --- 不可读不可写不可执行
 *   567: 当前组中成员的操作权限
 *   8910: 其他用户的操作权限
 *   第一个root: 当前登录用户
 *   第二个root: 当前用户下定义的组: root 用户下 的 默认 root 组 (root 用户也可以自定义组)
 *    读: r  设置时用 4 表示
 *    写: w  设置时用 2 表示
 *    执行: x  设置时用 1 表示
 *   在设置时, 使用三个数字的和的形式 设置权限
 *    如: 3 表示 可写 可执行(1+2)
 *
 *   命令: chmod 当前用户/组内用户/其他用户
 *    eg: chmod 777 文件名    表示所有用户对此文件 可读可写可执行
 *  八. 其他命令
 *     ps -ef                       查看所有进程
 *     kill -9 pid(进程号)           杀死指定进程
 *     ifconfig                     查看当前 ip
 *     grep 指定内容 文件名           在指定文件中查找指定内容,区分大小写
 *     grep -i 指定内容 文件名        查找指定内容, 不区分大小写
 *     管道命令:
 *     |                            管道命令: 必须和 grep 命令一起使用
 *          管道命令 和 grep 经常一起使用, 查所有文件的指定文件,查所有进程的指定进程,查所有软件的指定软件等
 *     格式: 命令1 | 命令2    把 命令1 的输出(结果) 作为 命令2 的 输入 (开始)
 *      命令2 要想执行,必须等到 命令1 执行完毕,并以 命令1 的结果作为 条件
 *     eg: ll | grep 1.txt    查当前目录的所有文件,然后在所有文件中查 1.txt
 *       ps -ef | grep vim    在所有进程中 找 vim 进程  -- 这个命令一定会有结果, 它会把 命令 grep vim 当成一个进程
 *  重启: reboot
 *  关机: halt/poweroff
 *
 * day02
 * 一:主机名配置
 *    查看主机名：命令  hostname
 *    临时修改主机名： hostname 新主机名   -- 该命令临时有效，只要系统重启，主机名就会还原
 *    永久修改主机名： hostnamectl set-hostname 新主机名
 *
 * 二:ip 地址配置
 *    查看网络配置： ifconfig
 *    手动设置固定 ip： /etc/sysconfig/network-scripts/ifcfg-eno16777736    -- 该配置文件名不固定（找 ifcfg-en 开头即可）
 *    TYPE=Ethernet             -- 网卡类型 以太网
 *    BOOTPROTO=dhcp            -- 启动网卡时指定获取 IP 地址的方式
 *                               取值： dhcp - 自动获取 ip 地址,网关,子网掩码等信息无需设置
 *                                     static - 静态 ip, 如需访问网络,需要自己设置 ip 地址等信息
 *                                     none - 固定 ip
 *    DEFROUTE=yes
 *    IPV4_FAILURE_FATAL=no
 *    IPV6INIT=yes
 *    IPV6_AUTOCONF=yes
 *    IPV6_DEFROUTE=yes
 *    IPV6_FAILURE_FATAL=no
 *    NAME=eno16777736
 *    UUID=ad808d2a-55d3-4895-88ef-3530f9c52fe6
 *    ONBOOT=yes                                    --是否开机就使用此网卡
 *    HWADDR=00:0C:29:61:26:56
 *    PEERDNS=yes
 *    PEERROUTES=yes
 *    IPV6_PEERDNS=yes
 *    IPV6_PEERROUTES=yes
 *   修改:
 *     BOOTPROTO=static
 *    添加:
 *      IPADDR=192.168.*.* -- IP 地址
 *      GATEWAY=192.168.**.** -- 网关(子网ip)
 *      NETMASK=255.255.255.0 -- 子网掩码
 *    ps: 在 vm 客户端 找 -> 编辑 -> 虚拟网络编辑器 -> VMnet8, 其中有 网卡/子网掩码
 *   修改完毕需要重启网卡:  service network restart
 *
 * 三:域名映射
 *    将 Linux 的 ip 和 自定义域名绑定
 *    修改 /etc 下的 hosts 文件, 添加 ip 和域名的映射
 *    eg: 192.168.*.*  自定义域名 (默认 localhost)
 *
 * 四.网络服务配置 (对应 windows 中的服务)
 *    服务命令
 *      service 服务名称 status  或  service status 服务名称     -- 查看服务状态
 *      service 服务名称 start   或  service start 服务名称      -- 开启服务
 *      service 服务名称 stop    或  service stop 服务名称       -- 关闭服务
 *      service 服务名称 restart 或  service restart 服务名称    -- 重启服务
 *   配置服务开机自启
 *      chkconfig network off  -- 关闭服务的开机自启
 *      chkconfig network on   -- 开启服务的开机自启
 *     或
 *      systemctl enable NetworkManager  -- 开启 network 服务的开机自启
 *      systemctl disable NetworkManager -- 关闭 开机自启
 *   进程端口查看:    netstat -apn
 *      端口占用: kill -9 pid  -- 强杀指定 pid 的进程
 *
 * 五.防火墙配置
 *    centos5.x 和 6.x 使用 iptables 作为防火墙, 在 centos7.x 移除 iptables,使用 firewall
 *    作为防火墙. 在 linux7.x 中,也可以安装 iptables,关闭 firewall. (iptables 使用简单,应用广泛)
 *   1. 安装 iptables 作为防火墙
 *      yum install iptables-services       -- 通过 yum install 命令可以从网上下载安装 iptables
 *   2. 停止 firewall 及其开机自启
 *     systemctl stop firewall.service      -- 停止 firewall
 *     systemctl disable firewall.service   -- 禁止 firewall 开机自启
 *   3. 启用 iptables,配置 开机自启
 *      systemctl start iptables.service    --启动 iptables
 *      systemctl enable iptables.servcie   -- 开机自启 iptables
 *   4. 查看 iptables 是否安装成功: systemctl status iptables
 *      关闭 iptables 防火墙: systemctl stop iptables   -- 不建议使用
 *      重启 iptables 防火墙: systemctl restart iptables //service iptables restart
 *   ps: 只要安装好了 iptables 这个防火墙,这个防火墙的配置文件就是 /etc/sysconfig/iptables
 *    iptables 配置文件: 允许某个端口访问
 *    后期修改 iptables 配置文件,设置 8080 端口(tomcat) 可以被外界访问
 *   必须安装了 iptables 防火墙,才有 iptables 配置文件
 *    linux 默认开放 22 端口
 *    修改: -A INPUT -p tcp -m state --state NEW -m tcp --dport 22 -j ACCEPT (复制(yy)粘贴(p)本行, 修改端口)
 *
 * 六:SSH免密登录
 *   在一台 linux 系统中登录另一台 linux 系统: ssh 需要登录的主机 ip
 *   退出: exit
 *   免密登录:
 *      基于一对密钥: 登录方创建 一对密钥(公钥+私钥), 把 公钥 给 被登录方
 *    密钥生成命令: ssh-keygen
 *     三个回车
 *    把 公钥 交给 被登录方: ssh-copy-id 指定ip
 *    再次登录另一台 linux: ssh 指定ip, 就不再需要密码了
 *
 * 七: nginx 服务器
 *    作用: 部署静态资源项目, 让所有人通过浏览器访问
 *  web 服务器: 部署静态和动态(servlet/jsp)资源项目,让所有人通过浏览器访问
 *  nginx 服务器: 主要用来为 web 服务器做 负载均衡(将浏览器的访问流量 均衡到 多个 web 服务器)
 *     同时, 代理 web 服务器, 保证 web 服务器的相对安全(相当于浏览器不能直接访问到 web 服务器, 必须通过中间的 nginx 服务器)
 *  安装:
 *     1. 安装 c 语言环境(gcc)
 *       命令: yum install gcc-c++         -- 安装期间有提示,一律yes
 *     2. 安装 nginx 依赖环境, -y 表示所有提示默认选择 y
 *      yum -y install pcre pcre-devel
 *      yum -y install zlib zlib-devel
 *      yum -y install openssl openssl-devel
 *     3. 安装目录: /usr/local
 *       解压: tar -zxvf 压缩文件名(.tar.gz)
 *       进入解压目录: cd 目录名
 *     4. 编译并安装
 *       ./configure
 *       make
 *       make install
 *       ps: 安装成功后,会在 /usr/local 下多出一个 nginx 目录
 *     5. 启动/停止 nginx
 *        进入nginx 的 bin目录 /usr/local/nginx/sbin 目录
 *        ./nginx           -- 启动
 *        ./nginx -s stop   -- 停止
 *      查看进程: ps -ef | grep nginx
 *
 * 八: 用户和组的命令
 *   1. 创建新用户
 *      命令: useradd -m 新用户名
 *     ps: 在 home 目录下会多一个用户,默认有一个组,就是该用户名
 *   2. 设置新用户密码
 *      命令: passwd 用户名
 *   3. 使用新用户登录
 *      ps: # 代表超级管理员, $ 代表普通用户
 *   4. 修改用户名
 *      命令: usermod -l 新用户名 就用户名
 *      ps: 修改用户名,组名不会变
 *   5. 创建新用户组
 *      命令: groupadd 组名
 *   6. 修改用户组名
 *      命令: groupmod -n 新组名 旧组名
 *   7. 将用户添加到新用户组中
 *      命令: usermod -g 组名 用户名
 *   8. 从新用户组中删除用户
 *      命令: gpassmod -d 用户名 组名
 *
 */
public class LinuxDemo {
    /**
     * 安装文档：
     * 该笔记时在 linux 上安装 jdk, mysql, redis, tomcat 软件, 以便在 linux 系统下建立 web 环境
     *
     * 一: java
     *   1. 查看当前 linux 系统是否安装 jdk
     *      rpm -qa | grep java
     *   2. 将 jdk 压缩包放入 /usr/local/jdk 目录下
     *   3. 解压: tar -zxvf ..
     *   4. 配置环境变量
     *      vi /etc/profile
     *      在文件最后添加:
     *      export JAVA_HOME=/usr/local/jdk/jdk1.8.0_181   -- 安装路径
     *      export PATH=$JAVA_HOME/bin:$PATH
     *   5. 重新加载配置文件
     *      source /etc/profile
     *    测试: java -version
     * 二: tomcat
     *   1. 查看是否安装 tomcat
     *      rpm -qa | grep tomcat
     *   2. 解压 tomcat /usr/local/tomcat
     *   3. 进入 /usr/local/tomcat/apache-tomcat-xxx/bin 启动 tomcat
     *      ./startup.sh
     *   4. 开放防火墙端口, 开放 8080
     *      -A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT
     *   5. 重启 防火墙
     *      service iptables restart
     * 三: mysql 安装
     *   1. 查看当前系统是否安装 mysql
     *      rpm -qa | grep mysql
     *      虽然没有安装 mysql, 但是有自带的数据库: mariadb
     *   2. 查看自带数据库,并卸载
     *      rpm -qa | grep mariadb
     *      rpm -e --nodeps mariadb-lib-xx   -- 复制查出来的信息
     *   3. 解压 mysql 到目录: /usr/local/mysql
     *   4. 先安装 server(服务器端)
     *     命令: rpm -ivh MySQL-server-xxx.rpm
     *     ps: 会缺依赖
     *     libaio.so.1
     *     libc.so.6
     *     libgcc_s.so.1 (版本冲突,先卸载,再安装)
     *     libstdc++.so.6 (版本冲突,先卸载,再安装)
     *    安装:
     *       yum install libaio.so.1
     *       yum install libc.so.6
     *    卸载 libgcc_s.so.1:
     *       rpm -qa | grep libgcc    -- 查看
     *       rpm -e --nodeps libgcc-4.8.5-28.el7_5.1.x86_64
     *     安装 libgcc_s.so.1:
     *       yum install libgcc_s.so.1
     *     卸载 libstdc++.so.6
     *        rpm -qa | grep libstdc  -- 查看
     *        rpm -e --nodeps libstdc++-devel-4.8.5-28.el7_5.1.x86_64
     *        rpm -e --nodeps libstdc++-4.8.5-28.el7_5.1.x86_64
     *     安装  libstdc++.so.6
     *        yum install libstdc++.so.6
     *   5. 重新安装 mysql-server
     *      命令 rpm -ivh  MySQL-server-5.5.49-1.linux2.6.i386.rpm
     *      ps:在安装的过程中,记得复制这段设置密码的格式文本---用来设置登录密码的
     *          设置登录密码的格式：/usr/bin/mysqladmin -u root password '要设置的登录密码'
     *   6. 安装 mysql-client
     *      命令 rpm -ivh MySQL-client-5.5.49-1.linux2.6.i386.rpm
     *      ps: 会缺依赖, libncurses.so.5
     *      执行安装依赖命令:yum install libncurses.so.5
     *     再次执行: rpm -ivh MySQL-client-5.5.49-1.linux2.6.i386.rpm
     *   7. 启动 MySQL 服务
     *      service mysql start
     *   8. 将 mysql 设置开机自启
     *      命令 systemctl enable mysql
     *   9. 设置密码
     *      命令: /usr/bin/mysqladmin -u root password '密码'
     *   10. 登录 mysql
     *      mysql -uroot -p密码
     *   11. 修改防火墙配置文件: 开放 3306
     *      vi /etc/sysconfig/iptables
     *      -A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
     *     重启防火墙: service iptables restart
     *  12. 允许远程连接 mysql -- 开放 可视化界面的连接权限
     *      登录 mysql
     *      执行命令: grant all privileges on *.* to '用户名'@'%' identified by '密码';
     *         刷新权限: flush privileges;
     *   13. 中文乱码
     *      查看 mysql 编码
     *       show variables like '%char%';
     *     1) 停止 mysql 服务
     *     2) 将 /usr/share/mysql/my-medium.cnf 复制到 /etc 目录下, 重命名为 my.cnf
     *     3) 编辑 my.cnf, 在 [mysqlid] 下添加一行 'character-set-server=utf8', 保存退出
     *     4) 重启 mysql 服务, 新建数据库查看编码 (改变编码之前建的数据库编码不会改变)
     * 四: redis
     *   1. yum install gcc-c++   // redis 是 c 语言编写,需要 c 语言环境
     *   2. 解压 /usr/local/redis
     *   3. 安装: 进入解压目录
     *      命令: make
     *           make PREFIX=/usr/local/redis install
     *     安装成功后,在 redis 目录下多出来一个 bin 目录
     *   4. 启动服务
     *     将 redis-3.0.7 目录下的 redis.conf 文件复制到 /usr/local/redis/bin 目录下
     *     修改 redis.conf 配置文件,设置为 启动服务加载配置文件
     *     命令: vi redis.conf
     *     搜索 /daemonize, 将其值改为 yes
     *   5. 使用 redis 客户端连接 redis 服务器
     *      指定主机,指定端口
     *      ./redis-cli -h localhost -p 6379
     *   6. 修改防火墙配置,开放可视化工具权限
     *      vi /etc/sysconfig/iptables
     *      -A INPUT -m state --state NEW -m tcp -p tcp --dport 6379 -j ACCEPT
     *   7. 重启防火墙服务
     *      service iptables restart
     *   8. 关闭服务
     *      ./redis-cli -h localhost shutdown
     *     或 kill -9 pid
     *
     */
    /**
     * mysql: 3306
     * nginx: 80
     * tomcat: 8080
     * redis: 6379
     * oracle: 1521
	 * activeMQ: 8161
	 * mongodb: 27017
     */
    /**
     * 配置Nginx的反向代理
     * 步骤一：修改nginx的配置文件
     * 命令 vim nginx.conf
     * 步骤二：增加或修改如下内容
     * server上增加代理
     *
     * upstream tomcat{server localhost:8080;} #为谁代理服务
     * server {
     * 　　　　listen 80;
     * 　　　　server_name localhost;
     * ​
     * 　　　　location / {
     *            # root   html;
     *            # index  index.html index.htm;
     *           
     *            proxy_pass http://tomcat; # 访问tomcat
     *         }
     * }
     * 步骤三：重启nginx
     * 命令 ./nginx -s reload
     */
    /**
     * 虚拟设备：
     *     -VMnet0：用于虚拟桥接网络下的虚拟交换机
     *     -VMnet1：用于虚拟host-only网络下的虚拟交换机
     *     -VMnet8：用于虚拟NAT网络下的虚拟交换机
     *     -VMware Network Adapter VMnet1：Host用于与host-only虚拟网络进行通信的虚拟网卡
     *     -VMware Network Adapter VMnet8：Host用于与NAT虚拟网络进行通信的虚拟网卡
     * 网络配置：
     *    1. 桥接模式
     *      将虚拟机当成局域网中一台电脑，需要占用一个 ip
     *    2.nat 模式
     *      让虚拟系统借助 nat(网络地址转换)功能,通过宿主机器所在的网络来访问公网
     *      也就是说,nat 模式可以实现在虚拟系统里访问互联网. nat 模式下的虚拟系统的
     *      tcp/ip 配置信息是由 VMnet8(nat) 虚拟网络的 DHCP 服务器提供的
     *      换句话说,就是共享本级的 ip 地址
     *    3. host-noly
     *      在某些特殊的网络调试环境中,要求将真实环境和虚拟环境隔离开,这时就可以采用 host-only 模式
     *      在 host-only 模式中,所有的虚拟系统是可以互相通信的,但是虚拟系统和真实的网络是被隔离开的,
     *      可以利用 winXP 自带的 internet 连接共享(实际上是一个简单的路由 nat), 来让虚拟机通过主机真实的网卡
     *      来进行外网的访问. 虚拟系统的 tcp/ip 配置信息(如 ip地址网关地址 dns 服务器等),都是由 VMnet1(host-only)
     *      虚拟网络的 DHCP 服务器来动态分配的
     */
}
