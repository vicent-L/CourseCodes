创建zookeeper伪集群

下载：

wget http://archive.apache.org/dist/zookeeper/zookeeper-3.4.6/zookeeper-3.4.6.tar.gz

解压：

```
tar -zxvf zookeeper-3.4.6.tar.gz
```

重命名

```
mv zookeeper-3.4.6 zookeeper1
cp -r zookeeper1 zookeeper2
cp -r zookeeper1 zookeeper3
```

创建目录

```
mkdir data
mkdir logs
```

配置目录

```
dataDir=/root/zookeeper1/data
dataLogDir=/root/zookeeper1/logs
```

修改zoo.cfg文件

```
cd /usr/local/zookeeper/zookeeper1/conf/
cp zoo_sample.cfg zoo.cfg
```

```
server.1=192.168.1.104:2888:3888 
server.2=192.168.1.104:2889:3889 
server.3=192.168.1.104:2890:3890
```

修改clientPort端口

data 文件夹下创建myid文件，分别修改各个myid文件内容为1,2,3

**kafka集群环境搭建**

下载地址：https://archive.apache.org/dist/kafka/2.7.0/kafka_2.12-2.7.0.tgz

```
tar -zxvf kafka_2.12-2.7.0.tgz
```

修改kafka配置文件

```
broker.id=0
log.dirs=/tmp/kafka-logs
listeners=PLAINTEXT://localhost:9092
```

```
broker.id=1
log.dirs=/tmp/kafka-logs1
listeners=PLAINTEXT://localhost:9093
```

```
broker.id=2
log.dirs=/tmp/kafka-logs2
listeners=PLAINTEXT://localhost:9094
```

采用的是zookeeper集群模式，因此修改三个配置文件的zookeeper connect为以下

```
192.168.1.104:2181,192.168.1.104:2182,192.168.1.104:2183
```

使用springboot连接虚拟机kafka时候报错，查找application.yml地址都已经配置了

> 2021-08-01 21:41:01.947  WARN 56528 --- [ntainer#0-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-kafka2-1, groupId=kafka2] Connection to node 0 (localhost/127.0.0.1:9092) could not be established. Broker may not be available.
> 2021-08-01 21:41:04.048  WARN 56528 --- [ntainer#0-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-kafka2-1, groupId=kafka2] Connection to node 2 (localhost/127.0.0.1:9094) could not be established. Broker may not be available.
> 2021-08-01 21:41:06.151  WARN 56528 --- [ntainer#0-0-C-1] org.apache.kafka.clients.NetworkClient   : [Consumer clientId=consumer-kafka2-1, groupId=kafka2] Connection to node 1 (localhost/127.0.0.1:9093) could not be established. Broker may not be available.

advertised.listeners才是真正的对外代理地址

解决方法

修改各个server.properties文件的

> advertised.listeners=PLAINTEXT://192.168.1.104:9092

>advertised.listeners=PLAINTEXT://192.168.1.104:9093

>advertised.listeners=PLAINTEXT://192.168.1.104:9094

