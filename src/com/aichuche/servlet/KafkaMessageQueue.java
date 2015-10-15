// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KafkaMessageQueue.java

package com.aichuche.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;

import org.codehaus.jackson.JsonParser.Feature;

import com.aichuche.servlet.chh.ReportDataServlet;
import com.chh.utils.PropertiesUtils;

import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.message.MessageAndMetadata;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaMessageQueue
{
    private Producer producer;
    private Producer mapProducer;
    private String topic;
    private Class messageClass;
    private boolean enableProducer;
    private boolean enableConsumer;
    private static String brokerList;
    private static String brokerZkStr;
    private String groupId;
    private static String DEFAULT_GROUP_ID = "bfrd";
    private static Properties properties= new Properties();

	
	static{
		  		try{
		  			brokerList = PropertiesUtils.getValue("brokerList");
		  		brokerZkStr =brokerList;
			}
			 catch (Exception e)
		    {
		      e.printStackTrace();
		 }
		
		
	
	}
	
    static class Dummy
        implements Serializable
    {

        public String toString()
        {
            return (new StringBuilder("Dummy[a = ")).append(a).append(", b = ").append(b).append("]").toString();
        }

        private static final long serialVersionUID = 1L;
        private int b;
        private String a;

        public Dummy(String a, int b)
        {
            this.a = a;
            this.b = b;
        }
    }


    public KafkaMessageQueue(String topic)
    {
        this(topic, null, null);
    }

    public KafkaMessageQueue(String topic, Class messageClass)
    {
        this(topic, messageClass, null);
    }

    public KafkaMessageQueue(String topic, String groupId)
    {
        this(topic, null, groupId);
    }

    public KafkaMessageQueue(String topic, Class messageClass, String groupId)
    {
        this.groupId = groupId;
        this.topic = topic;
        this.messageClass = messageClass;
        if(groupId == null)
            this.groupId = DEFAULT_GROUP_ID;
        else
            this.groupId = groupId;
    }

    public static void main(String args[])
        throws Exception
    {
        System.out.println("BEGIN");
        String topic = "aichuche-topic";
        String groupId = "aichuche-group";
        KafkaMessageQueue q = new KafkaMessageQueue(topic, Dummy.class, groupId);
        q.put("123chhora_aichuche", "longredhao chh");
        System.out.println("OVER");
    }

    public void put(String entity, String key)
        throws Exception
    {
        if(entity == null)
        {
            return;
        } else
        {
        	  //创建KeyedMessage发送消息，参数1为topic名，参数2为分区名（若为null则随机发到一个分区），参数3为消息
            //getProducer().send(new KeyedMessage(topic, entity));
            long x1= System.currentTimeMillis();
            //eyedMessage<String, String> data2 = new KeyedMessage<String, String>(topic,partitionKey,mesg);  
           getProducer().send(new KeyedMessage(topic, key, entity));
           long x2= System.currentTimeMillis();
			
			System.out.println("==getProducer().send cost(ms)："+(x2-x1));
            return;
        }
    }

    public String take()
        throws Exception
    {
        KafkaStream stream = getConsumerStream();
        ConsumerIterator it = stream.iterator();
        if(it.hasNext())
        {
            System.out.println((new StringBuilder("get from kafka: ")).append(new String((byte[])it.next().message())).toString());
            return new String((byte[])it.next().message());
        } else
        {
            return "";
        }
    }

    public Producer getProducer()
    {
        if(producer == null){
        	producer = new Producer<Object, String>(createProducerConfig(brokerList));
        }
        	
        return producer;
    }

    public KafkaStream getConsumerStream()
    {
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(brokerZkStr));
        Map topicCountMap = new HashMap();
        topicCountMap.put(topic, new Integer(1));
        Map consumerMap = consumer.createMessageStreams(topicCountMap);
        List streams = (List)consumerMap.get(topic);
        return (KafkaStream)streams.get(0);
    }

    public Producer getMapProducer()
    {
        if(mapProducer == null)
            mapProducer = new Producer(createMapProducerConfig(brokerZkStr));
        return mapProducer;
    }

    private ConsumerConfig createConsumerConfig(String zkConnect)
    {
        Properties props = new Properties();
        props.put("zookeeper.connect", zkConnect);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "20000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.enable", "false");
        props.put("auto.offset.reset", "smallest");
        props.put("rebalance.max.retries", "15");
        props.put("rebalance.backoff.ms", "15000");
        return new ConsumerConfig(props);
    }

    private ProducerConfig createProducerConfig(String brokerList)
    {
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", brokerList);
        props.put("request.required.acks", "1");
        return new ProducerConfig(props);
    }

    private ProducerConfig createMapProducerConfig(String brokerList)
    {
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", brokerList);
        props.put("request.required.acks", "1");
        return new ProducerConfig(props);
    }

    public void close()
    {
    }


}
