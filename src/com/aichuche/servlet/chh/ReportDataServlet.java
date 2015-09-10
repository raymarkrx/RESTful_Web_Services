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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private static String topic = new String();
	private static String groupIdSuffix = "-group";
	private static String delimiter = ";";// 分隔符
	private static String groupId = new String();
	private static String CHARSET_UTF8 = "UTF-8";

	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	private static String yyyyMMdd = "[0-9]{8}";

	static {
		topic = PropertiesUtils.getValue("reportdata.topic");
		PrintUtils.print("topic:"+topic);
		groupId = topic + groupIdSuffix;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
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
			if ("1".equals(dataType)) {
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
					//log.debug("get 101 message  deviceId:"+deviceId );
//					log.debug("messageId:"+messageId );
//					log.debug("dataType:"+dataType );
//					log.debug("RAWDATA:"+RAWDATA );
//					log.debug("==RAWDATA byte.length:"+result1.length );
//					log.debug("101 message OVER:" );
					synchronized (obj) {
						//printRAWDATA101(result1);
						sendRAWDATA101(deviceId,messageId,dataType,createTime,result1);//处理data101的消息
					}
				}else if(DataTypeID==102){
					
				}else if(DataTypeID==103){
					
					log.debug(" \n get 103 message  deviceId:"+deviceId );
					log.debug("messageId:"+messageId );
					log.debug("dataType:"+dataType );
					log.debug("RAWDATA:"+RAWDATA );
					log.debug("==RAWDATA byte.length:"+result1.length );
			         String revokeReturnMesg="";
			         try{
			        	 String url="";//暂时写死了
			        	 revokeReturnMesg=revokeWebService103(url,keyValues);
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

		try {
			response.getOutputStream().write(temp.getBytes(CHARSET_UTF8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintUtils.print("====ReportDataServlet OVER=======");

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
		String host="210.51.31.67";//203.84.197.25 http://210.51.31.67/youyun/simplepush
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
		sb2.append(Speed); // 最后一个不要加 ;

		String data = sb2.toString();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result1.length && i < 53; i++) {
			sb.append("[" + i + "]:" + EncodeUtils.byteToInt(result1[i]));
			sb.append("\n");
		}
		// log.debug("每个byte打印后的值：\n"+sb.toString());
		long time = System.currentTimeMillis();
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String mesg = deviceId + ";" + messageId + ";" + dataType + ";" + data + ";" + createTime;
		log.debug("reportData101 mesg:" + mesg);
		dataMap.put("partitionKey", deviceId);
		dataMap.put("mesg", mesg);
		
		LogStoreServiceImpl logStoreService = new LogStoreServiceImpl();
		logStoreService.sendToKafka(dataMap, topic, groupId);
		
		//测试消息是否有序
		Map<String, Object> rtnMap =RedisClient.testOrder(deviceId, "ReportDataServlet",String.valueOf(Date));
		log.debug("ReportDataServlet_deviceId:"+deviceId+"最新倒序的20个unixtimestamp:"+rtnMap.get(deviceId));
		
		
	}



	private void printRAWDATA101(byte[] result1) throws IOException {

		// log.debug("解码后,第0个字节："+String.valueOf(EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1,0,1))));
		// log.debug("解码后,第1-4个字节："+String.valueOf(EncodeUtils.
		// bytesToInt4(EncodeUtils.splitBytesArray(result1,1,4))));
		// log.debug("解码后,第5-8个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,5,4))));
		// log.debug("解码后,第9-12个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,9,4))));
		// log.debug("解码后,第13-16个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,13,4))));
		// log.debug("解码后,第17-20个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,17,4))));
		// log.debug("解码后,第21-24个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,21,4))));
		// log.debug("解码后,第25-28个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,25,4))));
		// log.debug("解码后,第29-32个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,29,4))));
		// log.debug("解码后,第33-36个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,33,4))));
		// log.debug("解码后,第37-40个字节："+String.valueOf(
		// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,37,4))));
		//log.debug("解码后,第41-44个字节(GPSX)："+ String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 41, 4))));
		//log.debug("解码后,第45-48个字节(GPSY)："+ String.valueOf(EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 45, 4))));
				// log.debug("解码后,第49-52个字节："+String.valueOf(
				// EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,49,4))));

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

		// log.debug("解码后,第0个字节："+ DataTypeID );
		// log.debug("解码后,第1-4个字节："+ Date );
		// log.debug("解码后,第5-8个字节："+ Ax );
		// log.debug("解码后,第9-12个字节："+ Ay );
		// log.debug("解码后,第13-16个字节："+Az );
		// log.debug("解码后,第17-20个字节："+Wx );
		// log.debug("解码后,第21-24个字节："+Wy );
		// log.debug("解码后,第25-28个字节："+Wz );
		// log.debug("解码后,第29-32个字节："+Tx );
		// log.debug("解码后,第33-36个字节："+Ty );
		// log.debug("解码后,第37-40个字节："+Tz );
		//log.debug("格式化后,第41-44个字节(GPSX)：" + GPSX);
		//log.debug("格式化后,第45-48个字节(GPSY)：" + GPSY);
		// log.debug("解码后,第49-52个字节："+ Speed );

	}

}
