package com.aichuche.servlet.chh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.chh.utils.DbUtils;
import com.chh.utils.JSONUtils;
import com.chh.utils.PrintUtils;
import com.chh.utils.encoding.EncodeUtils;

public class ReportData103Servlet extends HttpServlet {

	private static final long serialVersionUID = 3624858948204016394L;
	
	private static final int BUFFER_SIZE = 1024;

	//private static String logPath = ServerConfiguration.getServerHome() + "logs";
	static Logger log= Logger.getLogger(ReportData103Servlet.class);
	
	private static Object obj = new Object();
	private static String topic =new String();
	private static String groupIdSuffix ="-group";
	private static String delimiter =";";//分隔符
	private static String groupId=new String();
	private static Properties properties = new Properties();
	private static String CHARSET_UTF8 = "UTF-8";
	
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	
	private static String yyyyMMdd = "[0-9]{8}";
	
	static{
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)			 {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String  deviceId = request.getParameter("deviceId") ;
		String  messageId = request.getParameter("messageId") ;
		String  dataType = request.getParameter("dataType") ;//1=string   	2=base64后的rawData
		String data=null;
		String RAWDATA=null;
		String  createTime = request.getParameter("createTime") ;
		
		log.debug("ReportData103Servlet deviceId:"+deviceId);
		
		//定义data的
		int DataTypeID;
		int Date       ;
		int Message_ID ;
		int Return_code ;
		
		
		try {
			if ("1".equals(dataType)) {
				data = request.getParameter("data");
				DataTypeID = Integer.parseInt(data.split(",")[0]);
				Date = Integer.parseInt(data.split(",")[1]);
				Message_ID = Integer.parseInt(data.split(",")[2]);
				Return_code = Integer.parseInt(data.split(",")[3]);
			} else {
				RAWDATA = request.getParameter("data");
				byte[] result1 = EncodeUtils.base64Decode(RAWDATA);
				log.debug(" 上报103消息（103消息返回下发命令的执行结果） RAWDATA:"+RAWDATA+",转为byte[] 后的lenght:"+result1.length);

				DataTypeID = EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1, 0, 1));
				Date = EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 1, 4));
				Message_ID = EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1, 5, 4));
				Return_code = EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1, 9, 1));

				StringBuilder sb2 = new StringBuilder();
				sb2.append(DataTypeID + ",");
				sb2.append(Date + ",");
				sb2.append(Message_ID + ",");
				sb2.append(Return_code); // 最后一个不要加 ;

				data = sb2.toString();
				log.debug("解析后的 data:"+data+" ,分为：DataTypeID"+DataTypeID+" ,Date:"+Date+" ,Message_ID:"+Message_ID+" ,Return_code:"+Return_code);

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < result1.length && i < 53; i++) {
					sb.append("[" + i + "]:" + EncodeUtils.byteToInt(result1[i]));
					sb.append("\n");
				}
				// PrintUtils.print("每个byte打印后的值：\n"+sb.toString());
			}
			
//			0:接收成功
//			1:调用成功
//			-1:调用失败
//			2:执行成功
//			-2:执行失败

			  
			String status="-2";
			if(Return_code==0){
				status="2";
			}
			log.debug("Sql:"+"update t_downpush_log set status="+status+",execute_return_date=now where messageId1="+Message_ID+" and deviceID="+deviceId);
			 String sql="update t_downpush_log set status=?,execute_return_date=? where messageId1=? and deviceID=? ";
			 Connection conn =null; 
			 conn =DbUtils.getConn(); 
			 PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);  
	         	pst.setString(1, status);
	         	pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
	         	pst.setString(3,String.valueOf(Message_ID));
	         	pst.setString(4,deviceId);
	         	int r=pst.executeUpdate();
	         	pst.close();
				
	         	log.debug("更新103消息对应的下发命令的status( 2:执行成功, -2:执行失败 ) . update rows: "+r+", status:"+status);
	         	
				result.put("return_code", 0);
				result.put("return_message","success");
		
		} catch (Exception e) {
			e.printStackTrace();
			result.put("return_code", -1);
			result.put("return_message", "failed "+e.getMessage());
		}
			String temp=JSONUtils.toJSONString(result);
			temp=temp.replace("[", "");
			temp=temp.replace("]", "");
			
			PrintUtils.print("====ReportData103Servlet OVER=======");
			try {
				response.getOutputStream().write(temp.getBytes(CHARSET_UTF8));
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	
}
