// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LogStoreServiceImpl.java

package com.aichuche.servlet;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aichuche.servlet.KafkaMessageQueue.Dummy;

// Referenced classes of package com.bianfeng.bfas.bfrd.web.servlet:
//            KafkaMessageQueue

public class LogStoreServiceImpl
    implements Runnable
{

    public LogStoreServiceImpl()
    {
        ONE = Integer.valueOf(1);
        UNIQUEID = "uniqueId";
        startupFlag = true;
    }

    public void startService()
    {
    	
    }
    
    public static void main(String[] args) throws Exception{
    	String topic="aichuche-topic";
    	String groupId=topic+"-group";
    	LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
    	String mesg="867255020571164;test;101,1438927510,0.2777,-0.5075,10.2088,-0.0234,-0.0031,0.0170,-39.5400,13.2000,-58.4400,0.000000,0.000000,0.0000;2015-08-07 14:05:10";
    	dataMap.put("mesg", mesg);
		LogStoreServiceImpl logStoreService = new LogStoreServiceImpl();
		logStoreService.sendToKafka(dataMap, topic, groupId);
		System.out.println("OVER");
    }

    public void saveLogRecordsMap(LinkedHashMap dataMap, boolean isRepair, String repairPath)
        throws Exception
    {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String topic = "aichuche-topic";
        String groupId = "aichuche-group";
        KafkaMessageQueue logCacheQueue = new KafkaMessageQueue(topic, Dummy.class, groupId);
        String value = (String)dataMap.get("1");
        System.out.println("get from servlet(send to kafka):"+value);
        logCacheQueue.put(value, value);
    }
    
    public void sendToKafka(LinkedHashMap dataMap,String topic,String groupId)
            throws Exception
        {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            KafkaMessageQueue logCacheQueue = new KafkaMessageQueue(topic, Dummy.class, groupId);
        
            String partitionKey=(String)dataMap.get("partitionKey");;//这个key跟kafka的分区有关系，指定发送到哪个partition
            String mesg = (String)dataMap.get("mesg");
            logCacheQueue.put(mesg, partitionKey);
        }

    public void run()
    {
    	
    }

    private static final Log logWarpperLog = LogFactory.getLog("logWarpper.debug");
    private static final Log log = LogFactory.getLog(LogStoreServiceImpl.class);
    private boolean startupFlag;
    private Integer ONE;
    private String UNIQUEID;

}
