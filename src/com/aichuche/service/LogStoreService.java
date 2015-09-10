package com.aichuche.service;
//package com.bianfeng.bfas.bfrd.service;
//
//import java.io.IOException;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import com.bianfeng.bfas.bfrd.dto.EventPackage;
//import com.bianfeng.bfas.bfrd.dto.LogRecord;
//import com.bianfeng.bfas.bfrd.kafka.Message;
//
///**
// * 
// * @author ShaoHongLiang
// * @date 2013-8-12
// */
//public interface LogStoreService extends Runnable{
//
//	void saveEventPackage(EventPackage eventPackage, boolean isRepair, String repairPath) throws Exception;
//
//	void saveLogRecordsMap(LinkedHashMap<String, Object> dataMap, boolean isRepair, String repairPath) throws Exception;
//
//	void saveErrorLogRecords(byte[] bytes, String error, boolean isRepair, String repairPath) throws Exception;
//	
//	void bulkWriteLog(List<Message<LogRecord>> logParams) throws IOException;
//	
//	void writeLog(Message<LogRecord> logParams) throws IOException;
//	
//	void startService();
//	
//	void destory();
//
//	void tryClose();
//	
//}
