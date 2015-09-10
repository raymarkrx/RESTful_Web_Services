package com.aichuche.dto;
//package com.bianfeng.bfas.bfrd.dto;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class LogRecord implements Serializable, Unique {
//
//	private static final long serialVersionUID = 8207823027820116332L;
//
//	private String schemaName;
//	
//	private Date startTime;
//	
//	private Date receiveTime;
//	
//	private String key;
//
//	private List<String> data = new ArrayList<String>();
//	
//	private boolean isRepair;
//	
//	private String repairPath;
//	
//	private String productId;
//
//	private transient int failedCount = 0;
//
//	public Date getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(Date startTime) {
//		this.startTime = startTime;
//	}
//
//	public Date getReceiveTime() {
//		return receiveTime;
//	}
//
//	public void setReceiveTime(Date receiveTime) {
//		this.receiveTime = receiveTime;
//	}
//
//	public String getSchemaName() {
//		return schemaName;
//	}
//
//	public void setSchemaName(String schemaName) {
//		this.schemaName = schemaName;
//	}
//
//	public String getKey() {
//		return key;
//	}
//
//	public void setKey(String key) {
//		this.key = key;
//	}
//
//	public List<String> getData() {
//		return data;
//	}
//
//	public void setData(List<String> data) {
//		this.data = data;
//	}
//
//	public boolean isRepair() {
//		return isRepair;
//	}
//
//	public void setRepair(boolean isRepair) {
//		this.isRepair = isRepair;
//	}
//	
//	public String getRepairPath() {
//		return repairPath;
//	}
//
//	public void setRepairPath(String repairPath) {
//		this.repairPath = repairPath;
//	}
//	
//	public String getProductId() {
//		return productId;
//	}
//
//	public void setProductId(String productId) {
//		this.productId = productId;
//	}
//
//	public int increaseFailCount(){
//		return  ++failedCount;
//	}
//	
//	public void append(String value) {
//		data.add(value);
//	}
//	
//	public String getId() {
//		if(data == null || data.size() ==0){
//			return null;
//		}else{
//			return data.get(0);
//		}
//	}
//
//	@Override
//	public String toString() {
//		return "LogRecord [schemaName=" + schemaName + ", data=" + data + "]";
//	}
//
//
//
//}
