package com.aichuche.servlet;
//package com.bianfeng.bfas.bfrd.web.servlet;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.zip.GZIPInputStream;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.math.NumberUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.hadoop.fs.Path;
//
//import com.bianfeng.base.json.JSONTool;
//import com.bianfeng.base.util.BeanContext;
//import com.bianfeng.bfas.bfrd.dto.EventPackage;
//import com.bianfeng.bfas.bfrd.msgpack.MsgPackUtil;
//import com.bianfeng.bfas.bfrd.service.LogStoreService;
//import com.bianfeng.bfas.bfrd.util.ServerConfiguration;
//import com.bianfeng.bfas.bfrd.util.UtilData;
//import com.bianfeng.bfas.bfrd.web.Session;
//import com.bianfeng.bfas.bfrd.web.SessionContext;
//
///**
// * process msgpack unpack
// * 
// * @author ShaoHongLiang
// * @date 2013-8-9
// */
//public class MsgpackEventServlet extends HttpServlet {
//
//	final Log log = LogFactory.getLog(getClass());
//	
//	private static final long serialVersionUID = 1810002370032874234L;
//	
//	private static final int BUFFER_SIZE = 1024;
//	
//	private static String logPath = ServerConfiguration.getServerHome() + "logs";
//	
//	private static Object obj = new Object();
//	
//	private static String CHARSET_UTF8 = "UTF-8";
//	
//	private static String yyyyMMdd = "[0-9]{8}";
//	
//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		
//		InputStream in = request.getInputStream();
//
//		String ce = request.getHeader("Content-Encoding");
//		if (ce != null && ce.indexOf("gzip")>=0){
//			in = new GZIPInputStream(in);
//		}
//		
//		Session session = new Session();
//		session.setRequest(request);
//		
//		//set request to session context
//		SessionContext.setSession(session);
//
//		boolean isRepair = request.getParameter("repair") != null;
//		String repairPath = null;
//		String err = null;
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
//				repairPath = day + Path.SEPARATOR + "{product_id}" + Path.SEPARATOR + version;
//			}
//		}
//		
//		ByteArrayOutputStream out = new ByteArrayOutputStream(); 
//
//		byte[] buf = new byte[BUFFER_SIZE];
//		int len;
//
//		while ((len = in.read(buf)) != -1) {
//			// process byte buffer
//			out.write(buf, 0, len);
//		}
//		
//		byte[] bytes = out.toByteArray();
//		
//		//unpack data
//		EventPackage eventPackage;
//
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		if(err != null){
//			result.put("result", -1);
//			result.put("msg", err);
//		}else if(bytes.length == 0){
//			result.put("result", -1);
//			result.put("msg", "not found http post body");
//		}else{
//			try {
//				eventPackage = MsgPackUtil.read(bytes, EventPackage.class);
//				
//				if(eventPackage != null){
//
//					LogStoreService logStoreService = BeanContext.getInstance().getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME, LogStoreService.class);
//					
//					logStoreService.saveEventPackage(eventPackage, isRepair, repairPath);
//					
//				}
//				
//				result.put("result", 0);
//				/*
//				 * skip store success stream
//				 * 
//				 * synchronized (obj) {
//					OutputStream errStream = getSuccessStream();
//					errStream.write(bytes);
//					errStream.write(UtilData.Value.UNIX_LINEBREAK.getBytes());
//					errStream.close();
//				}*/
//				
//			} catch (Exception e) {
//				log.error("can not read EventPackage msgpack format", e);
//
//				LogStoreService logStoreService = BeanContext.getInstance().getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME, LogStoreService.class);
//				try {
//					logStoreService.saveErrorLogRecords(bytes, e.getMessage(), isRepair, repairPath);
//					
//					/*synchronized (obj) {
//						File logFoloder = new File(logPath);
//						
//						if(!logFoloder.exists()){
//							logFoloder.mkdirs();
//						}
//						
//						File file = new File(logFoloder, "/err_msgpack_" + UUID.randomUUID());
//						
//						if(!file.exists()){
//							file.createNewFile();
//						}
//						
//						PrintStream errStream = new PrintStream(new FileOutputStream(file, true), false);
//						errStream.write("HEADER:\n".getBytes());
//						Enumeration Enumeration = request.getHeaderNames();
//						while(Enumeration.hasMoreElements()){
//							String headName = Enumeration.nextElement().toString();
//							errStream.write(headName.getBytes());
//							errStream.write(" = ".getBytes());
//							errStream.write(request.getHeader(headName).getBytes());
//							errStream.println();
//						}
//						errStream.write("\nBODY:\n".getBytes());
//						errStream.write(bytes);
//						errStream.println();
//						errStream.write("\nEXCEPTION:\n".getBytes());
//						e.printStackTrace(errStream);
//						errStream.flush();
//						errStream.close();
//					}*/
//				} catch (Exception e1) {
//					synchronized (obj) {
//						PrintStream errStream = getErrorStream();
//						errStream.println(System.currentTimeMillis());
//						errStream.write(bytes);
//						errStream.println();
//						e.printStackTrace(errStream);
//						errStream.flush();
//						errStream.close();
//					}
//				}
//				
//				result.put("result", -1);
//				result.put("msg", e.getMessage());
//			}
//		}
//
//		response.getOutputStream().write(JSONTool.convertObjectToJson(result).getBytes(CHARSET_UTF8));
//	}
//
//	/**
//	 * get err file stream 
//	 * file name is like msgpack20010303
//	 * file context is bytes length and bytes context
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	private PrintStream getErrorStream() throws IOException {
//		DateFormat df = new SimpleDateFormat("yyyyMMdd");
//		File logFoloder = new File(logPath);
//		
//		if(!logFoloder.exists()){
//			logFoloder.mkdirs();
//		}
//		
//		File file = new File(logFoloder, "/err_msgpack_" + df.format(new Date()));
//		
//		if(!file.exists()){
//			file.createNewFile();
//		}
//		
//		return new PrintStream(new FileOutputStream(file, true), false);
//	}
//	
//
//	/**
//	 * get succsss file stream 
//	 * file name is like msgpack20010303
//	 * file context is bytes length and bytes context
//	 * 
//	 * @return
//	 * @throws IOException
//	 */
//	@SuppressWarnings("unused")
//	private PrintStream getSuccessStream() throws IOException {
//		DateFormat df = new SimpleDateFormat("yyyyMMdd");
//		File logFoloder = new File(logPath);
//		
//		if(!logFoloder.exists()){
//			logFoloder.mkdirs();
//		}
//		
//		File file = new File(logFoloder, "/success_msgpack_" + df.format(new Date()));
//		
//		if(!file.exists()){
//			file.createNewFile();
//		}
//		
//		return new PrintStream(new FileOutputStream(file, true), false);
//	}
//}
