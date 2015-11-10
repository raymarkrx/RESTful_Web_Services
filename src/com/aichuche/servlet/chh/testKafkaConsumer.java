package com.aichuche.servlet.chh;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import org.apache.commons.collections.CollectionUtils;

import com.chh.utils.PrintUtils;
 

public class testKafkaConsumer {
 
  public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException {
 
    Properties properties = new Properties();
    properties.put("zookeeper.connect", "180.97.232.56:2181/kafka");
    properties.put("auto.commit.enable", "true");
    properties.put("auto.commit.interval.ms", "60000");
    properties.put("group.id", "test");
 
    ConsumerConfig consumerConfig = new ConsumerConfig(properties);
 
    ConsumerConnector javaConsumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
 
    //topic的过滤器
    Whitelist whitelist = new Whitelist("aichuche-topic");
    List<KafkaStream<byte[], byte[]>> partitions = javaConsumerConnector.createMessageStreamsByFilter(whitelist);
 
    if (CollectionUtils.isEmpty(partitions)) {
      System.out.println("empty!");
      TimeUnit.SECONDS.sleep(1);
    }
 
    //消费消息
    for (KafkaStream<byte[], byte[]> partition : partitions) {
 
      ConsumerIterator<byte[], byte[]> iterator = partition.iterator();
      while (iterator.hasNext()) {
        MessageAndMetadata<byte[], byte[]> next = iterator.next();
//        System.out.println("topic:	"+ next.topic());
//        System.out.println("partiton:	" + next.partition());
//        System.out.println("offset:	" + next.offset());
//        System.out.println("message:	" + new String(next.message(), "utf-8"));
        PrintUtils.print("topic:	"+ next.topic()+",partiton:" + next.partition()+",offset:" + next.offset()+" ,message:	" + new String(next.message(), "utf-8"));
      }
    } 
  }
}

