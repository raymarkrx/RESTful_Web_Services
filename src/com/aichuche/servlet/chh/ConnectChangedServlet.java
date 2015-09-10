package com.aichuche.servlet.chh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
//import java.nio.file.Path;
import java.text.DateFormat;
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
//import org.apache.hadoop.fs.Path;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;






//import com.bianfeng.base.json.JSONTool;
//import com.bianfeng.base.util.BeanContext;
//import com.bianfeng.bfas.bfrd.service.LogStoreService;
//import com.bianfeng.bfas.bfrd.util.ServerConfiguration;











import com.aichuche.servlet.LogStoreServiceImpl;
import com.aichuche.util.UtilData;
import com.chh.utils.JSONUtils;
import com.chh.utils.PrintUtils;

public class ConnectChangedServlet extends HttpServlet {
	final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 3624858948204016394L;
	
	private static final int BUFFER_SIZE = 1024;

	//private static String logPath = ServerConfiguration.getServerHome() + "logs";
	private static String logPath = "logs";
	
	private static Object obj = new Object();
	private static String topic =new String();
	private static String groupIdSuffix ="-group";
	private static String groupId=new String();
	private static Properties properties = new Properties();
	private static String CHARSET_UTF8 = "UTF-8";
	
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	
	private static String yyyyMMdd = "[0-9]{8}";
	
	static{
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		mapper.configure(Feature.ALLOW_COMMENTS, true);
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
		//允许出现特殊字符和转义符
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;

		//允许出现单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true) ;
		
		try{
  			InputStream in = null;
  		 //判断是否是生产环境的开关,1:加载生产环境的配置，0：加在测试环境的配置
  		 in=ReportDataServlet.class.getResourceAsStream("/config/switch.properties");
  		 properties.load(in);
  		 String isProduction = properties.getProperty("isProduction");
  		 properties.clear();
  		
        
  		 if("1".equals(isProduction)){
  			 System.out.println("!!!this is PRODUCTION ENVIRONMENT  !!!");
  			 in=ReportDataServlet.class.getResourceAsStream("/config/hadoop/server_prod.properties");
          }else{
          	System.out.println("!!!this is test environment!!!");
          	in=ReportDataServlet.class.getResourceAsStream("/config/hadoop/server_test.properties");
          }
  		 
  		   properties.load(in);
	       topic = properties.getProperty("connectChanged.topic");
	       groupId=topic+groupIdSuffix;
	      properties.clear();
	      
	}
	 catch (IOException e)
    {
      e.printStackTrace();
 }

		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*post方式，开始
		InputStream in = request.getInputStream();

		String ce = request.getHeader("Content-Encoding");
		if (ce != null && ce.indexOf("gzip")>=0){
			in = new GZIPInputStream(in);
		}
		
		String requestEncoding = request.getParameter("charset");
		
		if(requestEncoding == null){
			requestEncoding = request.getCharacterEncoding();
		}
		if(requestEncoding == null || !Charset.isSupported(requestEncoding)){
			requestEncoding = CHARSET_UTF8;
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
		byte[] buf = new byte[BUFFER_SIZE];
		int len;
		//out从request中得到字节流，然后封装得到值
		while ((len = in.read(buf)) != -1) {
			// process byte buffer
			out.write(buf, 0, len);
		}
		
		byte[] bytes = out.toByteArray();
		String postMesg=new String(bytes); //最后得到postmesg
		*///post方式，结束
		
		String  deviceId = request.getParameter("deviceId") ;
		String  type = request.getParameter("type") ;
	
		Map<String, Object> result = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		
	
			try {
//				LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>)JSONTool.convertJsonToObject(jsonStr);
				LinkedHashMap<String,String> dataMap = new LinkedHashMap<String,String>();
				String mesg=deviceId+";"+type;
				PrintUtils.print("ConnectChanged mesg："+mesg);
	            dataMap.put("mesg",  mesg);
				
				//LogStoreService logStoreService = BeanContext.getInstance().getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME, LogStoreService.class);
				LogStoreServiceImpl logStoreService = new LogStoreServiceImpl();
				
				if(dataMap != null){
					logStoreService.sendToKafka(dataMap,topic,groupId);
				}
				
				result.put("return_code", 0);
				result.put("return_message","success");
			} catch (Exception e) {
				//out print err status and message
				log.error("can not read EventPackage json format", e);
				
				LogStoreServiceImpl  logStoreService = new LogStoreServiceImpl();
				try {
					//logStoreService.saveErrorLogRecords(bytes, e.getMessage(), isRepair, repairPath);
				} catch (Exception e1) {
					synchronized (obj) {
						PrintStream errStream = getErrorStream();
						errStream.println(System.currentTimeMillis());
						errStream.println();
						e.printStackTrace(errStream);
						errStream.flush();
						errStream.close();
					}
				}
				
				result.put("return_code", -1);
				result.put("return_message", e.getMessage());
			}
		
		   //String t = (new StringBuilder("hello I am kafka Server,I received your Message successully! datetime:")).append(time).toString();
	        //response.getOutputStream().write(t.getBytes(CHARSET_UTF8));
			String temp=JSONUtils.toJSONString(result);
			temp=temp.replace("[", "");
			temp=temp.replace("]", "");
			response.getOutputStream().write(temp.getBytes(CHARSET_UTF8));
		
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
