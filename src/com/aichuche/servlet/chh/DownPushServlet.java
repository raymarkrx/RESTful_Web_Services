package com.aichuche.servlet.chh;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import com.aichuche.util.UtilData;
import com.chh.utils.DateUtils;
import com.chh.utils.DbUtils;
import com.chh.utils.FileUtil;
import com.chh.utils.HttpUtil;
import com.chh.utils.JSONUtils;
import com.chh.utils.PrintUtils;
import com.chh.utils.PropertiesUtils;
import com.chh.utils.encoding.EncodeUtils;

public class DownPushServlet extends HttpServlet {
	
	static Logger log= Logger.getLogger(DownPushServlet.class);
	private static final long serialVersionUID = 3624858948204016394L;
	
	private static final int BUFFER_SIZE = 1024;

	//private static String logPath = ServerConfiguration.getServerHome() + "logs";
	private static String logPath = "logs";
	
	private static Object obj = new Object();
	
	private static String CHARSET_UTF8 = "UTF-8";
	
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	
	private static String yyyyMMdd = "[0-9]{8}";
	
	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long tStart,t1,t2;
		tStart=System.currentTimeMillis();
		t1=System.currentTimeMillis();
		//get方式，开始
		log.debug("before:"+request.getParameter("deviceid"));
		String  deviceId = request.getParameter("deviceid").replace(' ','+'); ;//把空格替换为+
		log.debug("after:"+request.getParameter("deviceid").replace(' ','+'));
		String  datetime = request.getParameter("datetime") ;
		int datetimeUnix=(int)DateUtils.getUnixTimestampFromLocalTimeDate2(datetime);
		int  messagecode = Integer.parseInt(request.getParameter("messagecode") );
		Date receiveDate=new Date();
		
//		String  data = request.getParameter("data") ;

		Map<String, Object> result = new HashMap<String, Object>();
		Connection conn =null; 
        try {
        	 conn =DbUtils.getConn(); 
        	 t2=System.currentTimeMillis();
        	 log.debug("0-cost(ms):"+(t2-t1));
        	 t1=System.currentTimeMillis();
        	 if(conn != null){
        		 
        	   String messageid1,messageid2;
        	   String DataTypeID="201";//201(写死)	201-系统功能
        	   //String DataType="1"; //	1=string  ,2=base64后的rawData
        	   String DataType="2";
        		 //messageid1 ,4 bytes ,send to other service,message是要下发到simplepush的，也是要通过103data返回给我的messageid 
	         	messageid1=String.valueOf(FileUtil.getOneNumber("seq.txt"));
	         	//log.debug("messageid1:"+messageid1);
	         	Date dt=new Date();
	         	SimpleDateFormat sdf=new SimpleDateFormat("yyMMddhh");
	         	//messageid2, 8 bytes,save into my logtable
	         	messageid2=sdf.format(dt)+messageid1;
	         	//log.debug("messageid2:"+messageid2);
	         	
	         	 //Data=’201,datetime,message_id,Message_code’
	         	String data=DataTypeID+","+datetime+","+messageid1+","+messagecode;
	         	
	         	
	         	//data ,messageid1 insert to table
	         	t2=System.currentTimeMillis();
	         	log.debug("1-cost(ms):"+(t2-t1));
	         	t1=System.currentTimeMillis();
	         	
	        	String sql="insert into t_downpush_log(deviceId,dataType,messageId1,messageId2,message_data,message_date,receive_date,status)  "
	        			+ " values(?,?,?,?,?,?,?,?)";
	        	
	         	PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);  
	         	pst.setString(1, deviceId);
	         	pst.setInt(2,Integer.parseInt(DataTypeID));
	         	pst.setString(3,messageid1);
	         	pst.setString(4,messageid2);
	         	pst.setString(5,data);
	         	pst.setTimestamp(6, new java.sql.Timestamp(DateUtils.parseDate(datetime,"yyyyMMddHHmmss").getTime()));
	         	pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
	         	pst.setString(8, "0");
	         	pst.execute();
	         	pst.close();
	         	
	         	t2=System.currentTimeMillis();
	         	log.debug("2-cost(ms):"+(t2-t1));
	         	t1=System.currentTimeMillis();
	         	
//				0:接收成功
//				1:调用成功
//				-1:调用失败
//				2:执行成功
//				-2:执行失败
//	         	String deviceIdEncode=URLEncoder.encode(deviceId,"utf-8");
//	         	log.debug("deviceIdEncode:"+deviceIdEncode);
//		         String url=PropertiesUtils.getValue("urlSimplepush")+"?"
//		        		+ "deviceId="+deviceIdEncode+"&messageId="+messageid1+"&dataType="+DataType+"&data="+data;
//		         log.debug(" revoke URL:"+url);
		         
	         	//把data转为字节流
	         	byte[] result1=null;
	         	
	         	byte[] byte1=EncodeUtils.intToByte1(Integer.parseInt(DataTypeID));
	         	byte[] byte4=EncodeUtils.intToByte4(datetimeUnix);
	         	result1=EncodeUtils.combineTowBytes(byte1, byte4);
	         	
	         	byte4=EncodeUtils.intToByte4(Integer.parseInt(messageid1));
	         	result1=EncodeUtils.combineTowBytes(result1, byte4);
	         	
	         	byte1=EncodeUtils.intToByte1(messagecode);
	         	result1=EncodeUtils.combineTowBytes(result1, byte1);
	         	
	         	String dataRAW=EncodeUtils.base64Encode(result1);
	         	//log.debug("dataRAW:"+dataRAW);  DataTypeID 1个字节 datetime 4个字节  messageid1 4个字节 messagecode 1个字节
	         	
		         String urlPath=PropertiesUtils.getValue("urlSimplepush");
		         Map<String, String> keyValues=new HashMap<String, String>();
		         keyValues.put("deviceId", deviceId);//
		         keyValues.put("messageId", messageid1);
		         keyValues.put("dataType", DataType);
		         keyValues.put("data", dataRAW);
		         //这里是同步调用,不是异步
		         String status="0";
		         String revokeReturnMesg="";
		         try{
		        	 revokeReturnMesg=revokeWebService(urlPath,keyValues);
		        	 log.debug("revoke return:"+revokeReturnMesg);
		        	 status="1";
		         }catch(Exception e){
		        	 e.printStackTrace();
		        	 status="-1";
		         }
		         
		         	t2=System.currentTimeMillis();
		         	log.debug("3-cost(ms):"+(t2-t1));
		         	t1=System.currentTimeMillis();
		         
		         
		         sql="update t_downpush_log set status=?,revoke_return_date=?,revoke_return_message=? where messageId1=? and messageId2=? ";
		         pst = (PreparedStatement) conn.prepareStatement(sql);  
		         	pst.setString(1, status);
		         	pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
		         	pst.setString(3,revokeReturnMesg);
		         	pst.setString(4,messageid1);
		         	pst.setString(5,messageid2);
		         	pst.execute();
		         	pst.close();
		         //System.out.println("UPDATE ok!");
		         	
		           	t2=System.currentTimeMillis();
		         	log.debug("4-cost(ms):"+(t2-t1));
		         	t1=System.currentTimeMillis();
		         
		         	
		         int return_code=(int)JSONUtils.StringToJSONOBject(revokeReturnMesg).get("return_code");
		         String return_message=(String)JSONUtils.StringToJSONOBject(revokeReturnMesg).get("return_message");
		         result.put("return_code", return_code);
				 result.put("return_message",return_message);
				 result.put("messageId1",messageid1);
				 result.put("messageId2",messageid2);
				 
				   	t2=System.currentTimeMillis();
		         	log.debug("5-cost(ms):"+(t2-t1));
		         	t1=System.currentTimeMillis();
		         	
		         //response.getOutputStream().write(temp.getBytes(CHARSET_UTF8));
		         //response.getOutputStream().write((JSONUtils.toJSONString(result).replaceAll("\\[|\\]", "").getBytes(CHARSET_UTF8)));
				 response.getOutputStream().write((JSONUtils.MapToJSONString(result).getBytes(CHARSET_UTF8)));
				 
				 t2=System.currentTimeMillis();
		         log.debug("6-cost(ms):"+(t2-t1));
				 
				 log.debug("total-cost(ms):"+(t2-tStart));
            }else{
            	log.debug("  !!!chh conn is null");
            }
              
        } catch (Exception e) {
        	log.debug(e.getMessage());
            e.printStackTrace();
            synchronized (obj) {
				PrintStream errStream = getErrorStream();
				errStream.println(System.currentTimeMillis());
				//errStream.write(bytes);
				errStream.println();
				e.printStackTrace(errStream);
				errStream.flush();
				errStream.close();
			}
            
    		result.put("return_code", -1);
			result.put("return_message", new String(e.getMessage().getBytes(CHARSET_UTF8)));
			response.getOutputStream().write((JSONUtils.toJSONString(result).replaceAll("\\[|\\]", "").getBytes(CHARSET_UTF8)));
        }finally{
        	try {
        		if(null!=conn){
        			conn.close();
        		}
			} catch (SQLException e) {
				e.printStackTrace();
				 synchronized (obj) {
						PrintStream errStream = getErrorStream();
						errStream.println(System.currentTimeMillis());
						//errStream.write(bytes);
						errStream.println();
						e.printStackTrace(errStream);
						errStream.flush();
						errStream.close();
					}
			}
        }
        
        
	
		//response.getOutputStream().write((JSONUtils.toJSONString(result1).replaceAll("\\[|\\]", "").getBytes(CHARSET_UTF8)));
		
	}
	
	private String  revokeWebService(String strUrl,Map<String,String> keyValues) throws Exception{

		String deviceId=keyValues.get("deviceId");
		String messageId=keyValues.get("messageId");
		String dataType=keyValues.get("dataType");
		String data=keyValues.get("data");
		
		//log.debug("===deviceId:"+deviceId);

		URIBuilder builder = new URIBuilder();
		String host="210.51.31.67";//203.84.197.25 http://210.51.31.67/youyun/simplepush
		//String host="table.finance.yahoo.com";
		builder.setScheme("http").setHost(host)
		.setPath("/youyun/simplepush")
		.setParameter("deviceId",deviceId)//默认执行encoding的操作
		.setParameter("messageId",messageId)
		.setParameter("dataType",dataType)
		.setParameter("data",data);


		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		log.debug("HttpGet.toString: "+httpget.toString());

		//System.out.println(httpget.getURI());
		//System.out.println("executing request " + httpget.getURI());

		long t1=System.currentTimeMillis();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		DefaultHttpClient httpclient = HttpUtil.getHttpClient();
		String responseQuotes = httpclient.execute(httpget, responseHandler);   
		long t2=System.currentTimeMillis();
		log.debug("==revoke cost(ms)："+(t2-t1));
		return responseQuotes;
        
	}
	
	private PrintStream getErrorStream() throws IOException {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		File logFoloder = new File(logPath);
		
		if(!logFoloder.exists()){
			logFoloder.mkdirs();
		}
		
		File file = new File(logFoloder, "/err_json_" + df.format(new Date()));
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		return new PrintStream(new FileOutputStream(file, true), false);
	}
	
}
