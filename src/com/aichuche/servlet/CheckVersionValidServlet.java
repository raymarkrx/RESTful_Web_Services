package com.aichuche.servlet;
//package com.bianfeng.bfas.bfrd.web.servlet;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.bianfeng.bfas.bfrd.dto.CheckVersionValid;
//import com.bianfeng.bfas.bfrd.msgpack.MsgPackUtil;
//import com.bianfeng.bfas.bfrd.util.ClientHolderPool;
//import com.bianfeng.bfas.bfrd.util.ClientHolderWarpper;
//import com.bianfeng.bfas.bfrd.util.UtilData;
//
///**
// * 
// * @author ShaoHongLiang
// * @date 2013-10-17
// */
//public class CheckVersionValidServlet extends HttpServlet {
//	final Log log = LogFactory.getLog(getClass());
//	
//	private static String INVALID_VERS_QUERY_KEY = "avv1";
//	private static String INVALID_VERS_RESULT_KEY = "invalidvers";
//	private static String INVALID_VERS_SPLIT_CHAR = ";";
//
////	@SuppressWarnings("unused")
////	private static String ANDROID = "1";
////	private static String IOS = "2";
//
//	private static final long serialVersionUID = 132976923753880061L;
//	
//	private static ClientHolderPool chPool = ClientHolderPool.getInstance();
//	
//	private static HashMap<String, byte[]> resultCache = new HashMap<String, byte[]>();
//
//	private static ClientHolderWarpper ch = chPool.getConnection();
//	
//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//
//		int result = 0;
//		String err = null;
//		
//		if(req.getParameter("clearCache") != null){
//			resultCache.clear();
//			resp.getOutputStream().write("cache cleared!".getBytes());	
//			return;
//		}
//		
//		//get parameter
//		List<String> nullParamList = new ArrayList<String>();
//		
//		String appId = req.getParameter("appId");
//		if(appId == null){
//			nullParamList.add("appId");
//		}
//		
//		String groupId = req.getParameter("groupId");
//		if(groupId == null){
//			nullParamList.add("groupId");
//		}
//		
//		String sdkType = req.getParameter("sdkType");
//		if(sdkType == null){
//			nullParamList.add("sdkType");
//		}
//		
//		String appVersion = req.getParameter("appVersion");
//		if(appVersion == null){
//			nullParamList.add("appVersion");
//		}
//		
//		String resultFormat = req.getParameter("resultFormat");
//		if(resultFormat == null){
//			resultFormat = UtilData.Request.Type.JSON;
//		}
//		byte[] resBytes = null;
//		
//		String cacheKey = appId + "_" + groupId + "_" + sdkType + "_" + appVersion + "_" + resultFormat;
//		if(resultCache.containsKey(cacheKey)){
//			resBytes = resultCache.get(cacheKey);
//		}else{
//			boolean containsAppId = false;
//			if(nullParamList.size() > 0){
//				err = nullParamList.toString() + " not allow null";
//			}else{
//				
//				//bfdc is connected
//				if(ch != null){
//					try {
//						List<String> keys = new ArrayList<String>();
//						keys.add(INVALID_VERS_RESULT_KEY);
//						Map<String, Object> data = ch.getDbRequester().queryCacheData(INVALID_VERS_QUERY_KEY, keys, appId, groupId, sdkType);
//						if(data == null){
//							//
//							log.info("when request bfdc by params:appId["+ appId +"], groupId[" + groupId + "], sdkType[" + sdkType + "], null result ");
//						}else{
//							Object invalidVersions = data.get(INVALID_VERS_RESULT_KEY);
//							
//							if(invalidVersions != null){
//								String[] invalidVerArr = ((String)invalidVersions).split(INVALID_VERS_SPLIT_CHAR, -1);
//								for(String invalidVersion : invalidVerArr){
//									if(invalidVersion.equals(appVersion)){
//										containsAppId = true;
//										break;
//									}
//								}
//							}
//						}
//					} finally {
//						chPool.release(ch);
//					}
//					
//				}else{
//					log.info("can't connect to bfdc...");
//				}
//			}
//			
//			if(containsAppId){
//				log.info("skip invalid app version :appId["+ appId +"], groupId[" + groupId + "], sdkType[" + sdkType + "], appVersion[" + appVersion + "]");
//				result = 0;
//			}else{
//				result = 1;
//			}
//			
//			CheckVersionValid valid = new CheckVersionValid(result, err);
//			if(UtilData.Request.Type.MSG_PACK.equals(resultFormat)){
//				//msgpack
//				resBytes = MsgPackUtil.write(valid);
//			}else if(UtilData.Request.Type.JSON.equals(resultFormat)){
//				String val = valid.toJsonString();
//				resBytes = val.getBytes();
//			}
//			
//			resultCache.put(cacheKey, resBytes);
//		}
//		
//		
//		resp.getOutputStream().write(resBytes);	
//	}
//	
//}
