配置redis

## redis主从复制配置

 tar zxvf redis-5.0.5.tar.gz

进入解压目录进行编译安装操作

make

make install

直接安装，不指定路径的话启动文件默认会在 src 目录下生成

复制几份

cp -a redis-master redis-slave1

cp -a redis-master redis-slave2

修改配置文件

master的配置

```
logfile "/root/redis-master/log/redis_6379.log"
port 6379
pidfile /var/run/redis_6379.pid
daemonize yes

```

slave1的配置

需要新建log文件夹  mkdir log

```
port 6380
pidfile /var/run/redis_6380.pid
logfile "/root/redis-slave1/log/redis_6380.log"
daemonize yes
#定义master信息
slaveof 192.168.1.103 6379 
```

slave2的配置

需要新建log文件夹  mkdir log

```
port 6381
pidfile /var/run/redis_6381.pid
logfile "/root/redis-slave2/log/redis_6381.log"
daemonize yes
#定义master信息
slaveof 192.168.1.103 6379  
```

启动各个redis

`./src/redis-server redis.conf`

查看日志

执行：info replication 查看当前库状态

## 哨兵模式(sentinel)

- **配置哨兵模式**

1. 选择你要当做master(主机)的redis服务，在安装目录下创建sentinel.conf文件

   touch sentinel.conf

   （哨兵配置文件，注意：**在redis的解压目录也有一个sentinel.conf文件**，要区分开来）

2. 在sentinel.conf文件里面添加内容格式：`sentinel monitor [master-group-name] [ip] [port] [quorum]`

master哨兵配置：

master哨兵配置：

```
sentinel monitor mymaster 192.168.1.103 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1
```

slave1哨兵配置

```
sentinel monitor mymaster 192.168.1.103 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1
logfile /root/redis-slave1/log/sentinel26380.log
port 26380
```

slave2哨兵配置

```
sentinel monitor mymaster 192.168.1.103 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1
logfile /root/redis-slave2/log/sentinel26381.log
port 26381
```

启动哨兵

1. redis-sentinel sentinel.conf
2. 查看哨兵信息  info Sentinel

## redis cluster集群

```
mkdir /usr/local/redis-cluster  #创建集群文件目录
cd  /usr/local/redis-cluster/  #进入集群文件目录
mkdir -p 8001/data 8002/data 8003/data 8004/data 8005/data 8006/data  #创建集群存放数据目录
说明：
这里用一台服务器模拟reids cluster 集群,集群的端口号从8001-8006表示6个不同的redis

1.下载redis安装包
   wget http://download.redis.io/releases/redis-3.2.9.tar.gz
2.解压redis
   tar -zxvf redis-3.2.9.tar.gz
3.安装redis
   cd redis-3.2.9
   make  
   cd src
   make install PREFIX=/usr/local/redis  
4.将配置文件移动到安装目录下
   cd ../
   mkdir /usr/local/redis/etc
   mv redis.conf /usr/local/redis/etc
5. 将安装好的redis，复制一个新的实例到集群8001目录
   cp -r /usr/local/redis    /usr/local/redis-cluster/8001
6.修改配置文件
   vi  /usr/local/redis-cluster/8001/redis/etc/redis.conf   #进入redis8001配置文件
   port 8001    #第一个reids端口号
   daemonize yes  #后台运行模式
   bind 192.168.1.103  #绑定当前机器 IP
   dir /usr/local/redis-cluster/8001/data/   #数据文件存放位置
   pidfile /var/run/redis_8001.pid   #pid 8001和port要对应
   cluster-enabled yes  #启动集群模式
   cluster-config-file nodes8001.conf  #8001和port要对应
   cluster-node-timeout 15000  #超时时间
   appendonly yes  #以aof方式持久化数据
 
创建bin目录，并将集群脚本复制到该目录
cd /usr/local/redis-cluster   
mkdir bin  #创建bin目录
cd /home/redis/redis-3.2.9/src  
cp mkreleasehdr.sh redis-benchmark redis-check-aof  redis-cli redis-server redis-trib.rb /usr/local/redis-cluster/bin  #复制脚本


1.复制5个节点，以8001为例，其他依次类推
    \cp -rf /usr/local/redis-cluster/8001/*   /usr/local/redis-cluster/8002
    
2.分别修改5个节点的配置信息
    vi /usr/local/redis-cluster/8002/redis/etc/redis.conf   #进入8002redis配置文件
    :%s/8001/8002   #通过搜索命令，全局替换配置文件中的8001，改成8002
   其他依次类推
    说明：以下是被修改的地方
    port 8001
    dir /usr/local/redis-cluster/8001/data/
    cluster-config-file nodes-8001.conf
    pidfile /var/run/redis_8001.pid
    
    
    cd /usr/local/redis-cluster/bin  #进入redis集群 bin目录
    启动8001-8006六个节点redis
    
  yum install ruby
  yum install rubygems
  *redis-3.2.1.gem下载地址： https://rubygems.org/gems/redis/versions/3.2.1  
  gem install -l redis-3.2.1.gem   #安装redis-3.2.1.gem
  说明：
  由于 Redis 集群需要使用 ruby 命令，所以我们需要安装 ruby 和相关接口
  
   /usr/local/redis-cluster/bin/redis-trib.rb create --replicas 1 192.168.1.103:8001 192.168.1.103:8002 192.168.1.103:8003 192.168.1.103:8004 192.168.1.103:8005 192.168.1.103:8006
  说明：replicas 1 为每个主节点分配一个从节点，这里创建的是3主3从的集群模式
  
  *注意这个地方千万不要写127.0.0.1,否则本机访问虚拟机会报错
  
  测试一下redis cluster集群是否部署成功
  ./redis-cli -c -h 192.168.1.103 -p 8001
```

