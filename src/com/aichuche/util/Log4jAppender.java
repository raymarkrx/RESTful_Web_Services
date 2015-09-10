package com.aichuche.util;
//package com.bianfeng.bfas.bfrd.util;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.bianfeng.bfas.bfae.handler.preprocess.PreProcessLog;
//
///**
// * 
// * @author ShaoHongLiang
// * @date 2013-10-8
// */
//public class Log4jAppender extends com.bianfeng.bfas.bfdc.client.ClientHolder.Log implements PreProcessLog {
//
//	
//	private static final Log log = LogFactory.getLog(Log4jAppender.class);
//
//	@Override
//	public void log(int step, String message) {
//		if(log.isDebugEnabled()){
//			log.debug("PreProcess step " + step +" : " + message);
//		}
//	}
//	
//	@Override
//	public void error(String message, Exception e) {
//		if(log.isDebugEnabled()){
//			log.debug("bfdc error " + message +" : " + e);
//		}
//	}
//}
