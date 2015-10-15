package com.chh.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class PropertiesUtils 
{
	  private static Properties properties = new Properties();
	    private static Map<String,String> keyValues=new HashMap<String,String>();
	    private static String isProduction=new String();
	    private static String isDebug=new String();
	    private static InputStream in = null;
		
		 static {
		    	 try {
		    		 //判断是否是生产环境的开关,1:加载生产环境的配置，0：加在测试环境的配置
		    		 in=PropertiesUtils.class.getResourceAsStream("/config/switch.properties");
		    		 properties.load(in);
		    		 isProduction = properties.getProperty("isProduction");
		    		 //判断是否是debug模式
		    		 isDebug = properties.getProperty("isDebug");
		    		 properties.clear();
		    		 
		    		 if("1".equals(isProduction)){
						 PrintUtils.print("!!!this is PRODUCTION ENVIRONMENT  !!!");
						 in=PropertiesUtils.class.getResourceAsStream("/config/hadoop/server_prod.properties");
			        }else{
			        	PrintUtils.print("!!!this is test environment!!!");
			        	in=PropertiesUtils.class.getResourceAsStream("/config/hadoop/server_prod.properties");
			        }
				 
			      properties.load(in);
			      String brokerList = properties.getProperty("metadata.broker.list");
			      String  topic = properties.getProperty("reportdata.topic");
			      String  stormZkPath = properties.getProperty("brokerStr");
			      String  id = properties.getProperty("id");
			      String kafkaBrokerZkPath = properties.getProperty("kafkaBrokerZkPath");
			      String FsUrl = properties.getProperty("hdfs.fsUrl");
			      String  webServiceData103Host = properties.getProperty("webServiceData103Host");
			      String urlSimplepush=properties.getProperty("url.simplepush");
			      String  redisHost = properties.getProperty("redis.host");
			      String redisPort=properties.getProperty("redis.port");
			      
			      keyValues.put("isProduction", isProduction);
			      keyValues.put("isDebug", isDebug);
			      keyValues.put("brokerList", brokerList);
			      keyValues.put("reportdata.topic", topic);
			      keyValues.put("stormZkPath", stormZkPath);
			      keyValues.put("id", id);
			      keyValues.put("kafkaBrokerZkPath", kafkaBrokerZkPath);
			      keyValues.put("FsUrl", FsUrl);
			      keyValues.put("webServiceData103Host", webServiceData103Host);
			      keyValues.put("urlSimplepush", urlSimplepush);
			      keyValues.put("redisHost", redisHost);
			      keyValues.put("redisPort", redisPort);
			      
			      
			      
			      
			      properties.clear();
		    		 
		    	 }
				 catch (Exception e)
			     {
			       e.printStackTrace();
			     }finally{
			    	 try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			     }
		    }
		 
  public static void main(String[] args)
  {
	  PropertiesUtils p=new PropertiesUtils();
	  System.out.println("value======"+PropertiesUtils.getValue("reportdata.topic"));
  }

  public  static String getValue(String a)
  {
		  return keyValues.get(a);
  }
}