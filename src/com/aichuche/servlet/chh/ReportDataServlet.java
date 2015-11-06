package com.aichuche.servlet.chh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
//import java.nio.file.Path;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
//import org.apache.hadoop.fs.Path;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

//import com.bianfeng.base.json.JSONTool;
//import com.bianfeng.base.util.BeanContext;
//import com.bianfeng.bfas.bfrd.service.LogStoreService;
//import com.bianfeng.bfas.bfrd.util.ServerConfiguration;














import com.aichuche.servlet.LogStoreServiceImpl;
import com.aichuche.util.UtilData;
import com.chh.utils.C3P0Utils;
import com.chh.utils.DateUtils;
import com.chh.utils.HttpUtil;
import com.chh.utils.JSONUtils;
import com.chh.utils.PrintUtils;
import com.chh.utils.PropertiesUtils;
import com.chh.utils.encoding.EncodeUtils;
import com.redis.RedisClient;

public class ReportDataServlet extends HttpServlet {

	private static final long serialVersionUID = 3624858948204016394L;

	private static final Logger log = Logger.getLogger("reportData101");

	private static Object obj = new Object();
	private static String groupIdSuffix = "-group";
	private static String delimiter = ";";// 分隔符
	private static String groupId = new String();
	private static String CHARSET_UTF8 = "UTF-8";

	private static String topic = new String();
    private static String brokerList;
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	private static Properties properties = new Properties();
	private static ProducerConfig config = null;  
	private static Producer<String, String> producer = null;  
	private static HashMap<String,String> mapTmp=new HashMap<String,String>();
	private static Connection conn;   
	private static PreparedStatement pst;  

	static {
		topic = PropertiesUtils.getValue("reportdata.topic");
		groupId = topic + groupIdSuffix;
		brokerList = PropertiesUtils.getValue("brokerList");
		
		Properties props = new Properties();
        //props.put("zk.connect", "10.91.228.28:2181,10.91.228.29:2181,10.91.228.30:2181");  
        props.put("serializer.class", "kafka.serializer.StringEncoder");  
        //props.put("metadata.broker.list", "210.51.31.68:9092,210.51.31.67:9092");
        props.put("metadata.broker.list", brokerList);//使用这个参数传入boker和分区的静态信息，如host1:port1,host2:port2, 这个可以是全部boker的一部分
        props.put("request.required.acks", "0");//0表示producer毋须等待leader的确认，1代表需要leader确认写入它的本地log并立即确认，-1代表所有的备份都完成后确认。 仅仅for sync
        config = new ProducerConfig(props); 
        producer = new Producer<String, String>(config);  
	}
	
	public static void main(String[] args) throws Exception {
		String topic="aichuche-topic";
		
		 for(int i=0;i<=100;i=i+1){
			String deviceId  ="test8618616969935";
	    	//String deviceId  ="test8618616969935"+rand.nextInt(99);
	    	String currentDateUnixTimestamp   =String.valueOf(DateUtils.getUnixTimestampFromCurrentDate());//yyyyMMddHHmmss
	    	String currentDate=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.getLocalTimeDateFromUnixTimestamp(currentDateUnixTimestamp));
	        //String currentDate="2015-08-03 15:47:35";
	    	String mesg    =deviceId+";1185;2;101,"+currentDateUnixTimestamp+","+9+",-0.6512,9.3278,-0.0097,-0.0024,-0.0061,-17.1875,-1.8750,30.5625,31.253138,121.354008,2;"+currentDate;
	    	String partitionKey=deviceId;
			
	    	long x3 = System.currentTimeMillis();
	    	
			KeyedMessage<String, String> data2 = new KeyedMessage<String, String>(topic,partitionKey,mesg);  
        	producer.send(data2);
			
			long x4= System.currentTimeMillis();
			
			log.debug("==sendToKafka cost(ms)："+(x4-x3));
		 }
	} 

	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		long beginTime=System.currentTimeMillis();
		
		
		Map<String, Object> result = new HashMap<String, Object>();

		String deviceId = request.getParameter("deviceId");
		String messageId = request.getParameter("messageId");
		String dataType = request.getParameter("dataType");// 1=string
															// 2=base64后的rawData
		String data = null;
		String RAWDATA = null;
		String createTime = request.getParameter("createTime");
		
		int DataTypeID; // 根据这个DataTypeID判断消息的类型，发送到指定的kafka的topic
		
		try {
			if ("1".equals(dataType)) {//没有压缩的情况
				data = request.getParameter("data");
			} else {
				RAWDATA = request.getParameter("data");
				byte[] result1 = EncodeUtils.base64Decode(RAWDATA);//解密为byte数组
				
				//根据data的类型不同，不同方法处理
				DataTypeID = EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1, 0, 1));
				
				Map<String, String> keyValues=new HashMap<String, String>();
		         keyValues.put("deviceId", deviceId);//
		         keyValues.put("messageId", messageId);
		         keyValues.put("dataType", dataType);
		         keyValues.put("data", RAWDATA);
		         keyValues.put("createTime", createTime);
				if(DataTypeID==101){
//					log.debug("get 101 message  deviceId:"+deviceId );
//					log.debug("messageId:"+messageId );
//					log.debug("dataType:"+dataType );
//					log.debug("RAWDATA:"+RAWDATA );
//					log.debug("==RAWDATA byte.length:"+result1.length );
//					log.debug("101 message OVER:" );
					synchronized (obj) {
						 log.debug("字节数组长度："+result1.length);
						//printRAWDATA101(result1);
						long a1=System.currentTimeMillis(); 
						sendRAWDATA101(deviceId,messageId,dataType,createTime,result1);//处理data101的消息
						long a2=System.currentTimeMillis(); 
						log.debug("==sendRAWDATA101 cost(ms)："+(a2-a1));
					}
				}else if(DataTypeID==102){
					
				}else if(DataTypeID==103){
					long a3=System.currentTimeMillis(); 
					log.debug(" \n get 103 message  deviceId:"+deviceId );
					log.debug("messageId:"+messageId );
					log.debug("dataType:"+dataType );
					log.debug("RAWDATA:"+RAWDATA );
					log.debug("==RAWDATA byte.length:"+result1.length );
			         String revokeReturnMesg="";
			         try{
			        	 String url="";
			        	 revokeReturnMesg=revokeWebService103(url,keyValues);
			        	 long a4=System.currentTimeMillis(); 
						 log.debug("==revokeWebService103 cost(ms)："+(a4-a3));
			         }catch(Exception e){
			        	 e.printStackTrace();
			         }
				}
				
				
				result.put("return_code", 0);
				result.put("return_message", "success");
			}
			
		} catch (Exception e) {
			log.info("can not read webService`s Data format", e);
			e.printStackTrace();
			try {
				LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
				String mesg = deviceId + ";" + messageId + ";" + dataType + ";" + data + ";" + createTime;
				PrintUtils.print("NOW ERROR MESG TO KAFKA!! reportData mesg：" + mesg);
				dataMap.put("mesg", mesg);

				// LogStoreService logStoreService =
				// BeanContext.getInstance().getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME,
				// LogStoreService.class);
				LogStoreServiceImpl logStoreService = new LogStoreServiceImpl();

				logStoreService.sendToKafka(dataMap, topic, groupId);

				// 错误数据也正确接收，留给后面的判断
				result.put("return_code", 0);
				result.put("return_message", "success");
			} catch (Exception e1) {// kafka的报错 则抛出
				e1.printStackTrace();
				result.put("return_code", -1);
				result.put("return_message", e.getMessage());
			}
		}
		String temp = JSONUtils.MapToJSONString(result);

		//将验证链路是否畅通的消息insert到msyql,以deviceId=chhTestData101 为区分
		if(deviceId.equals("chhTestData101")){
			String nowTime=DateUtils.getCurrentDateStr2();
			mapTmp.put("createTime", createTime);
			updateTestData101ToMysql(mapTmp);
		}
		
		long endTime=System.currentTimeMillis(); 
		log.debug("==total service  cost(ms)："+(endTime-beginTime));
		
		try {
			response.getOutputStream().write(temp.getBytes(CHARSET_UTF8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	private String formatData(int sour) {
		String temp;
		DecimalFormat df = new DecimalFormat("######0.0000"); // 保留4位小数
		temp = df.format(Double.valueOf(String.valueOf(sour)) / 10000);
		return temp;
	}

	private String formatData6(int sour) {
		String temp;
		DecimalFormat df = new DecimalFormat("######0.000000"); // 保留6位小数
		temp = df.format(Double.valueOf(String.valueOf(sour)) / 1000000);
		return temp;
	}
	

	private String  revokeWebService103(String strUrl,Map<String,String> keyValues) throws Exception{

		String deviceId=keyValues.get("deviceId");
		String messageId=keyValues.get("messageId");
		String dataType=keyValues.get("dataType");
		String RAWDATA=keyValues.get("data");
		String createTime=keyValues.get("createTime");
		
		URIBuilder builder = new URIBuilder();
		String host=PropertiesUtils.getValue("webServiceData103Host");//203.84.197.25 http://210.51.31.67/youyun/simplepush
		//String host="table.finance.yahoo.com";
		builder.setScheme("http").setHost(host).setPort(9088)
		.setPath("/RESTful_Web_Services/reportdata103")
		.setParameter("deviceId",deviceId)//默认执行encoding的操作
		.setParameter("messageId",messageId)
		.setParameter("dataType",dataType)
		.setParameter("data",RAWDATA);

		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		log.debug("revokeWebService103 HttpGet.toString: "+httpget.toString());

		//System.out.println(httpget.getURI());
		//System.out.println("executing request " + httpget.getURI());

		long t1=System.currentTimeMillis();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		DefaultHttpClient httpclient = HttpUtil.getHttpClient();
		String responseQuotes = httpclient.execute(httpget, responseHandler);   
		long t2=System.currentTimeMillis();
		log.debug("==revokeWebService103："+responseQuotes);
		log.debug("\n");
		return responseQuotes;
	}
	
	private void sendRAWDATA101(String deviceId,String messageId,String dataType,String createTime,byte[] result1) throws Exception {
		long x1 = System.currentTimeMillis();
		
		// 定义data101
		int DataTypeID;
		int Date;
		String Ax;
		String Ay;
		String Az;
		String Wx;
		String Wy;
		String Wz;
		String Tx;
		String Ty;
		String Tz;
		String GPSX;
		String GPSY;
		String Speed;
		String fyJiao;//俯仰角 
		String hxJiao;//航向角
		String hgJiao;//横滚角 

		DataTypeID = EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1, 0, 1));
		Date = EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 1, 4));
		Ax = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 5, 4)));
		Ay = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 9, 4)));
		Az = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 13, 4)));
		Wx = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 17, 4)));
		Wy = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 21, 4)));
		Wz = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 25, 4)));
		Tx = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 29, 4)));
		Ty = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 33, 4)));
		Tz = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 37, 4)));
		GPSX = formatData6(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 41, 4)));
		GPSY = formatData6(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 45, 4)));
		Speed = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 49, 4)));
		if(result1.length<=53){//老格式的数据没有后面的3个字段，是53个字节
			fyJiao ="0";
			hgJiao = "0";
			hxJiao ="0";
		}else{//新格式的数据是65个字节，加了3个字段，每个字段4个字节
			fyJiao = String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 53, 4)));
			hgJiao =String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 57, 4)));
			hxJiao =String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 61, 4)));
		}
		
		
		long x2 = System.currentTimeMillis();
		log.debug("==sendRAWDATA101_bytesToInt cost(ms)："+(x2-x1));
		


		StringBuilder sb2 = new StringBuilder();
		sb2.append(DataTypeID + ",");
		sb2.append(Date + ",");
		sb2.append(Ax + ",");
		sb2.append(Ay + ",");
		sb2.append(Az + ",");
		sb2.append(Wx + ",");
		sb2.append(Wy + ",");
		sb2.append(Wz + ",");
		sb2.append(Tx + ",");
		sb2.append(Ty + ",");
		sb2.append(Tz + ",");
		sb2.append(GPSX + ",");
		sb2.append(GPSY + ",");
		sb2.append(Speed + ",");
		sb2.append(fyJiao +",");
		sb2.append(hgJiao + ",");
		sb2.append(hxJiao);// 最后一个不要加 ;

		String data = sb2.toString();

//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < result1.length && i < 53; i++) {
//			sb.append("[" + i + "]:" + EncodeUtils.byteToInt(result1[i]));
//			sb.append("\n");
//		}
//		log.debug("每个byte打印后的值：\n"+sb.toString());
		
		String mesg = deviceId + ";" + messageId + ";" + dataType + ";" + data + ";" + createTime;
		String partitionKey=deviceId;
		log.debug("sendRAWDATA101_reportData101 mesg:" + mesg); 
		
		//将验证链路是否畅通的消息insert到msyql,以deviceId=chhTestData101 为区分
		if(deviceId.equals("chhTestData101")){
			long x6= System.currentTimeMillis();
			mapTmp.put("mesg_date", createTime);
			mapTmp.put("message", mesg);
			putTestData101ToMysql(mapTmp);
			long x7= System.currentTimeMillis();
			log.debug("=== insert tm_monitor_data101  cost(ms)："+(x7-x6));
		}
		
//		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
//		dataMap.put("partitionKey", deviceId);
//		dataMap.put("mesg", mesg);
//		LogStoreServiceImpl logStoreService = new LogStoreServiceImpl();
//		logStoreService.sendToKafka(dataMap, topic, groupId);
		
		long x3 = System.currentTimeMillis();
		
		KeyedMessage<String, String> data2 = new KeyedMessage<String, String>(topic,partitionKey,mesg);  
    	producer.send(data2);
		
		long x4= System.currentTimeMillis();
		
		log.debug("==sendRAWDATA101_sendToKafka cost(ms) ："+(x4-x3));
		
		//测试消息是否有序
		//Map<String, Object> rtnMap =RedisClient.testOrder(deviceId, "ReportDataServlet",String.valueOf(Date));
		//log.debug("ReportDataServlet_deviceId:"+deviceId+"最新倒序的20个unixtimestamp:"+rtnMap.get(deviceId));
		//long x5= System.currentTimeMillis();
		//log.debug("==RedisClient.testOrder  cost(ms)："+(x5-x4));
		
	}
	
	public void putTestData101ToMysql(HashMap<String,String> mapTmp ){
         conn =C3P0Utils.getConnection(); 
         pst = null;
        try {
        	 if(conn != null){
        	 String sql="insert into tm_monitor_data101(mesg_date,webService_receive_date,message)  values(?,?,?)";
        	 String mesg_date=mapTmp.get("mesg_date");
        	 String message=mapTmp.get("message");
        	 
         	pst = (PreparedStatement) conn.prepareStatement(sql);  
         	pst.setTimestamp(1,new Timestamp(DateUtils.getMillisecondsFromLocalTimeDate(mesg_date)));
         	pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
         	pst.setString(3, message);
         	pst.execute();
         	
         	//log.debug("  insert  tm_monitor_data101 ,OK");
            }else{
            	log.debug("   conn is null");
            }
        } catch (Exception e) { 
        	log.debug(e.getMessage());
            e.printStackTrace();
        }finally{
        	try {
        		if(pst!=null)pst.close();
        		if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public void updateTestData101ToMysql(HashMap<String,String> mapTmp ){
		long x6= System.currentTimeMillis();
		
        conn =C3P0Utils.getConnection(); 
        pst = null;
       try {
       	 if(conn != null){
       	 String sql="update  tm_monitor_data101 set webService_leave_date=? where mesg_date=?";
    	 String mesg_date=mapTmp.get("createTime");
       	 
        	pst = (PreparedStatement) conn.prepareStatement(sql);  
        	pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
        	pst.setTimestamp(2,new Timestamp(DateUtils.getMillisecondsFromLocalTimeDate(mesg_date)));
        	pst.execute();
        	
        	long x7= System.currentTimeMillis();
        	log.debug("==update  tm_monitor_data101`s webService_leave_date  cost(ms)："+(x7-x6));
           }else{
           	log.debug("   conn is null");
           }
       } catch (Exception e) { 
       	log.debug(e.getMessage());
           e.printStackTrace();
       }finally{
       	try {
       		if(pst!=null)pst.close();
       		if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
       }
	}



	private void printRAWDATA101(byte[] result1) throws IOException {

//		log.debug("解码后,第0个字节："+String.valueOf(EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1,0,1))));
//		log.debug("解码后,第1-4个字节："+String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,1,4))));
//		log.debug("解码后,第5-8个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,5,4))));
//		log.debug("解码后,第9-12个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,9,4))));
//		log.debug("解码后,第13-16个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,13,4))));
//		log.debug("解码后,第17-20个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,17,4))));
//		log.debug("解码后,第21-24个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,21,4))));
//		log.debug("解码后,第25-28个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,25,4))));
//		log.debug("解码后,第29-32个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,29,4))));
//		log.debug("解码后,第33-36个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,33,4))));
//		log.debug("解码后,第37-40个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,37,4))));
//		log.debug("解码后,第41-44个字节(GPSX)："+ String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 41, 4))));
//		log.debug("解码后,第45-48个字节(GPSY)："+ String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 45, 4))));
//		log.debug("解码后,第49-52个字节："+String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,49,4))));

		// 定义data的
		int DataTypeID;
		int Date;
		String Ax;
		String Ay;
		String Az;
		String Wx;
		String Wy;
		String Wz;
		String Tx;
		String Ty;
		String Tz;
		String GPSX;
		String GPSY;
		String Speed;
		String hgJiao;//横滚角 2个字节
		String fyJiao;//俯仰角 2个字节
		String hxJiao;//航向角 2个字节

		DataTypeID = EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1, 0, 1));
		Date = EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 1, 4));
		Ax = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 5, 4)));
		Ay = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 9, 4)));
		Az = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 13, 4)));
		Wx = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 17, 4)));
		Wy = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 21, 4)));
		Wz = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 25, 4)));
		Tx = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 29, 4)));
		Ty = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 33, 4)));
		Tz = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 37, 4)));
		GPSX = formatData6(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 41, 4)));
		GPSY = formatData6(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 45, 4)));
		Speed = formatData(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 49, 4)));
		if(result1.length<=53){//老格式的数据没有后面的3个字段，是53个字节
			hgJiao = "0";
			fyJiao ="0";
			hxJiao ="0";
		}else{//新格式的数据是65个字节，加了3个字段，每个字段4个字节
			hgJiao = String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 53, 4)));
			fyJiao =String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 57, 4)));
			hxJiao =String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 61, 4)));
		}

		
		 log.debug("解码后,第0个字节："+ DataTypeID );
		 log.debug("解码后,第1-4个字节："+ Date );
		 log.debug("解码后,第5-8个字节："+ Ax );
		 log.debug("解码后,第9-12个字节："+ Ay );
		 log.debug("解码后,第13-16个字节："+Az );
		 log.debug("解码后,第17-20个字节："+Wx );
		 log.debug("解码后,第21-24个字节："+Wy );
		 log.debug("解码后,第25-28个字节："+Wz );
		 log.debug("解码后,第29-32个字节："+Tx );
		 log.debug("解码后,第33-36个字节："+Ty );
		 log.debug("解码后,第37-40个字节："+Tz );
		log.debug("解码格式化后,第41-44个字节(GPSX)：" + GPSX);
		log.debug("解码格式化后,第45-48个字节(GPSY)：" + GPSY);
		 log.debug("解码后,第49-52个字节："+ Speed );
		log.debug("解码后,第53-56个字节(hgJiao)：" + hgJiao);
		log.debug("解码后,第57-60个字节(fyJiao)：" + fyJiao);
		 log.debug("解码后,第61-64个字节(hxJiao)："+ hxJiao );

	}

}
