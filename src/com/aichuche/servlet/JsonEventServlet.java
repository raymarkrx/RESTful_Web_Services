package com.aichuche.servlet;

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


import com.aichuche.util.UtilData;

public class JsonEventServlet extends HttpServlet {
	final Log log = LogFactory.getLog(getClass());

	private static final long serialVersionUID = 3624858948204016394L;
	
	private static final int BUFFER_SIZE = 1024;

	//private static String logPath = ServerConfiguration.getServerHome() + "logs";
	private static String logPath = "logs";
	
	private static Object obj = new Object();
	
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
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream in = request.getInputStream();

		String ce = request.getHeader("Content-Encoding");
		if (ce != null && ce.indexOf("gzip")>=0){
			in = new GZIPInputStream(in);
		}
		
//		Session session = new Session();
//		session.setRequest(request);
		
		//set request to session context
		//SessionContext.setSession(session);
		
		//get request charset encoding
		String requestEncoding = request.getParameter("charset");
		
		if(requestEncoding == null){
			requestEncoding = request.getCharacterEncoding();
		}
		if(requestEncoding == null || !Charset.isSupported(requestEncoding)){
			requestEncoding = CHARSET_UTF8;
		}
		
		boolean isRepair = request.getParameter("repair") != null;
		String repairPath = null;
		String err = null;
		
//		if(isRepair){
//			String day = request.getParameter("day");
//			String version = request.getParameter("version");
//
//			List<String> errParamList = new ArrayList<String>();
//			if(day == null){
//				errParamList.add("param day not null");
//			}else{
//				Pattern pattern = Pattern.compile(yyyyMMdd);
//				Matcher matcher = pattern.matcher(day);
//				if(!matcher.matches()){
//					errParamList.add("param day must be format yyyyMMdd");
//				}
//			}
//			
//			if(version == null){
//				errParamList.add("param version not null");
//			}else if(!NumberUtils.isNumber(version)){
//				errParamList.add("param version must be integer");
//			}
//			
//			if(errParamList.size() > 0){
//				err = errParamList.toString();
//			}else{
//				//repairPath = day + Path.SEPARATOR + "{product_id}" + Path.SEPARATOR + version;
//			}
//		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(); 

		byte[] buf = new byte[BUFFER_SIZE];
		int len;
		//out从request中得到字节流，然后封装得到值
		while ((len = in.read(buf)) != -1) {
			// process byte buffer
			out.write(buf, 0, len);
		}
		
		byte[] bytes = out.toByteArray();
		
		Map<String, Object> result = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		
	
			try {
				//jsonStr 得到的值：
				String jsonStr = new String(bytes, requestEncoding);
//				LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>)JSONTool.convertJsonToObject(jsonStr);
				LinkedHashMap dataMap = new LinkedHashMap();
				String value = (new StringBuilder("Hi,I come from other system ,send to you(kafka),chh,datetime :")).append(time+",Mesg   ").append(jsonStr+";"+time).toString();
	            dataMap.put("1", value);
				
				//LogStoreService logStoreService = BeanContext.getInstance().getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME, LogStoreService.class);
				LogStoreServiceImpl logStoreService = new LogStoreServiceImpl();
				
				if(dataMap != null){
					logStoreService.saveLogRecordsMap(dataMap, isRepair, repairPath);
				}
				
				result.put("result", 0);
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
						errStream.write(bytes);
						errStream.println();
						e.printStackTrace(errStream);
						errStream.flush();
						errStream.close();
					}
				}
				
				result.put("result", -1);
				result.put("msg", e.getMessage());
			}
		
		   String t = (new StringBuilder("hello I am kafka Server,I received your Message successully! datetime:")).append(time).toString();
	        response.getOutputStream().write(t.getBytes(CHARSET_UTF8));
		//response.getOutputStream().write(JSONTool.convertObjectToJson(result).getBytes(CHARSET_UTF8));
		
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
