package com.aichuche.servlet;
//package com.bianfeng.bfas.bfrd.web.servlet;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.codehaus.jackson.JsonParser.Feature;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.eclipse.jetty.websocket.WebSocket;
//import org.eclipse.jetty.websocket.WebSocketServlet;
//
//import com.bianfeng.base.json.JSONTool;
//import com.bianfeng.base.util.BeanContext;
//import com.bianfeng.bfas.bfrd.service.LogStoreService;
//import com.bianfeng.bfas.bfrd.util.ServerConfiguration;
//import com.bianfeng.bfas.bfrd.util.UtilData;
//import com.bianfeng.bfas.bfrd.web.SessionContext;
//import com.bianfeng.bfas.bfrd.web.WSSession;
//
//public class WSJsonEventServlet extends WebSocketServlet {
//	final Log log = LogFactory.getLog(getClass());
//
//	private static final long serialVersionUID = -7289719281366784056L;
//		
//	private static String logPath = ServerConfiguration.getServerHome() + "logs";
//
//	private static Object obj = new Object();
//	
//	private final Set<JSONWebSocket> _members = new CopyOnWriteArraySet<JSONWebSocket>();
//	
//	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share
//																// globally
//	static {
//		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
//		mapper.configure(Feature.ALLOW_COMMENTS, true);
//		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//
//		// 允许出现特殊字符和转义符
//		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//
//		// 允许出现单引号
//		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
//	}
//
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		getServletContext().getNamedDispatcher("default").forward(request,
//				response);
//	}
//
//	@Override
//	public WebSocket doWebSocketConnect(HttpServletRequest request, String arg1) {
//		return new JSONWebSocket(request);
//	}
//
//	class JSONWebSocket implements WebSocket.OnTextMessage {
//		private Connection _connection;
//		
//		private HttpServletRequest request;
//		
//		public JSONWebSocket(HttpServletRequest request) {
//			this.request = request;
//		}
//
//		public void onClose(int closeCode, String message) {
//			_members.remove(this);
//		}
//
//		public void sendMessage(String data) throws IOException {
//			_connection.sendMessage(data);
//		}
//
//		@SuppressWarnings("unchecked")
//		public void onMessage(String jsonStr) {
//			String uid = null;
//			Map<String, Object> result = new HashMap<String, Object>();
//			try {
//				WSSession session = new WSSession();
//				session.setRequest(request);
//				
//				SessionContext.setSession(session);
//				
//				LogStoreService logStoreService = BeanContext.getInstance()
//						.getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME,
//								LogStoreService.class);
//				LinkedHashMap<String, Object> dataMap = (LinkedHashMap<String, Object>) mapper
//						.readValue(jsonStr, Map.class);
//
//				if (dataMap != null) {
//					logStoreService.saveLogRecordsMap(dataMap, false, null);
//				}
//				uid = (String)dataMap.get("uid");
//				
//				result.put("result", 0);
//				result.put("uid", uid);
//			} catch (Exception e) {
//				// out print err status and message
//				log.error("can not read EventPackage json format", e);
//
//				LogStoreService logStoreService = BeanContext.getInstance()
//						.getBean(UtilData.Service.LOG_STORE_SERVICE_BEAN_NAME,
//								LogStoreService.class);
//				try {
//					logStoreService.saveErrorLogRecords(jsonStr.getBytes(),
//							e.getMessage(), false, null);
//				} catch (Exception e1) {
//					synchronized (obj) {
//						try {
//							PrintStream errStream = getErrorStream();
//							errStream.println(System.currentTimeMillis());
//							errStream.write(jsonStr.getBytes());
//							errStream.println();
//							e.printStackTrace(errStream);
//							errStream.flush();
//							errStream.close();
//						} catch (IOException e2) {
//							log.error("can't log error", e);
//						}
//					}
//				}
//
//				result.put("result", -1);
//				result.put("msg", e.getMessage());
//				result.put("uid", uid);
//			} finally {
//				try {
//					_connection.sendMessage(JSONTool.convertObjectToJson(result));
//				} catch (IOException e) {
//					log.error("can't send message", e);
//				}
//			}
//		}
//
//		public boolean isOpen() {
//			return _connection.isOpen();
//		}
//
//		public void onOpen(Connection connection) {
//			_members.add(this);
//			_connection = connection;
//		}
//	}
//	
//	private PrintStream getErrorStream() throws IOException {
//		DateFormat df = new SimpleDateFormat("yyyyMMdd");
//		File logFoloder = new File(logPath);
//		
//		if(!logFoloder.exists()){
//			logFoloder.mkdirs();
//		}
//		
//		File file = new File(logFoloder, "/err_wsjson_" + df.format(new Date()));
//		
//		if(!file.exists()){
//			file.createNewFile();
//		}
//		
//		return new PrintStream(new FileOutputStream(file, true), false);
//	}
//}
