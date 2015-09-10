package com.aichuche.dto;
//package com.bianfeng.bfas.bfrd.dto;
//
//import java.io.Serializable;
//import java.util.List;
//
//public class LogStorage implements Serializable{
//
//	private static final long serialVersionUID = 8229385747543281301L;
//	
//	private List<LogRecord> offlineLogs;
//	
//	private List<DataPoint> realtimeLogs;
//
//	public List<LogRecord> getOfflineLogs() {
//		return offlineLogs;
//	}
//
//	public void setOfflineLogs(List<LogRecord> offlineLogs) {
//		this.offlineLogs = offlineLogs;
//	}
//
//	public List<DataPoint> getRealtimeLogs() {
//		return realtimeLogs;
//	}
//
//	public void setRealtimeLogs(List<DataPoint> realtimeLogs) {
//		this.realtimeLogs = realtimeLogs;
//	}
//
//	public void add(LogStorage otherLogStorage) {
//		if(otherLogStorage != null){
//			List<LogRecord> otherOfflineLogs = otherLogStorage.getOfflineLogs();
//			if(this.offlineLogs == null){
//				this.offlineLogs = otherOfflineLogs;
//			}else{
//				this.offlineLogs.addAll(otherOfflineLogs);
//			}
//			
//			List<DataPoint> otherRealtimeLogs = otherLogStorage.getRealtimeLogs();
//			if(this.realtimeLogs == null){
//				this.realtimeLogs = otherRealtimeLogs;
//			}else{
//				this.realtimeLogs.addAll(otherRealtimeLogs);
//			}
//		}
//		
//	}
//
//
//}
