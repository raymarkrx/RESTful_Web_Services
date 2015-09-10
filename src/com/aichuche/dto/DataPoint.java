package com.aichuche.dto;
//package com.bianfeng.bfas.bfrd.dto;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import com.bianfeng.base.util.CommonTool;
//import com.bianfeng.bfas.bfrd.util.Base64Codec2;
//
///**
// * data point bean object.
// * @author Peng Peng
// *
// */
//public class DataPoint implements Serializable{
//
//	private static final long serialVersionUID = -4706309307713336184L;
//	
//	private String metric;
//	private long timestamp;
//	private long receiveTimestamp;
//
//	private Number value;
//	private Map<String, String> tags;			//all
//	private Map<String, String> keyTags;		//hbase rowkey
//	private Map<String, String> otherTags;		//meta, hbase value
//	private Map<String, String> hideTags;
//	private String metricType;
//	private int storageType;
//	private String productId;
//	
//	public DataPoint(String metric, long timestamp, Number value) {
//		this(metric, timestamp, value, null);
//	}
//
//	public DataPoint(String metric, long timestamp, Number value,
//			Map<String, String> tags) {
//		super();
//		this.metric = metric;
//		this.timestamp = timestamp;
//		this.value = value;
//		this.tags = tags;
//	}
//	
//	private long getDefaultTimestamp() {
//		return System.currentTimeMillis() / 1000;
//	}
//
//	public String getMetric() {
//		return metric;
//	}
//
//	public void setMetric(String metric) {
//		this.metric = metric;
//	}
//
//	public long getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(long timestamp) {
//		this.timestamp = timestamp;
//	}
//
//	public Number getValue() {
//		return value;
//	}
//
//	public void setValue(Number value) {
//		this.value = value;
//	}
//
//	public Map<String, String> getTags() {
//		return tags;
//	}
//
//	public void setTags(Map<String, String> tags) {
//		this.tags = tags;
//	}
//	
//	public void addTag(String key, String value) {
//		if(this.tags == null) {
//			this.tags = new LinkedHashMap<String, String>();
//		}
//		
//		this.tags.put(key, value);
//	}
//	
//	public Map<String, String> getKeyTags() {
//		return keyTags;
//	}
//
//	public void setKeyTags(Map<String, String> keyTags) {
//		this.keyTags = keyTags;
//	}
//	
//	public void addKeyTag(String key, String value) {
//		addTag(key, value);
//		
//		if(this.keyTags == null) {
//			this.keyTags = new LinkedHashMap<String, String>();
//		}
//		
//		this.keyTags.put(key, value);
//	}
//	
//	public Object[] getKeyTagValues() {
//		if(this.keyTags == null){
//			return new Object[]{};
//		}else{
//			return this.keyTags.values().toArray();
//		}
//	}
//	
//	public Map<String, String> getOtherTags() {
//		return otherTags;
//	}
//
//	public void setOtherTags(Map<String, String> otherTags) {
//		this.otherTags = otherTags;
//	}
//
//	public void addOtherTag(String key, String value) {
//		addTag(key, value);
//		
//		if(this.otherTags == null) {
//			this.otherTags = new LinkedHashMap<String, String>();
//		}
//		
//		this.otherTags.put(key, value);
//	}
//	
//	public Map<String, String> getHideTags() {
//		return hideTags;
//	}
//	
//	public void addHideTag(String key, String value) {
//		addHideTag(key, value, true);
//	}
//	
//	public void addHideTag(String key, String value, boolean isOpentsdbTag) {
//		if(isOpentsdbTag){
//			addTag(key, value);
//		}
//		
//		if(this.hideTags == null) {
//			this.hideTags = new LinkedHashMap<String, String>();
//		}
//		
//		this.hideTags.put(key, value);
//	}
//
//	public String getMetricType() {
//		return metricType;
//	}
//
//	public void setMetricType(String metricType) {
//		this.metricType = metricType;
//	}
//	
//	public int getStorageType() {
//		return storageType;
//	}
//
//	public void setStorageType(int storageType) {
//		this.storageType = storageType;
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
//	public long getReceiveTimestamp() {
//		return receiveTimestamp;
//	}
//
//	public void setReceiveTimestamp(long receiveTimestamp) {
//		this.receiveTimestamp = receiveTimestamp;
//	}
//
//	public DataPoint(String metric, Number value) {
//		this(metric, 0, value);
//	}
//
//	public boolean isValid() {
//		return !CommonTool.isEmpty(metric);
//	}
//
//	@Override
//	public String toString() {
//		return "DataPoint [metric=" + metric + ", timestamp=" + timestamp
//				+ ", value=" + value + ", tags=" + tags + "]";
//	}
//	
//	public String toDPString() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(V_PUT);
//		sb.append(V_SPACE);
//		sb.append(metric);
//		sb.append(V_SPACE);
//		if(timestamp == 0) {
//			timestamp = getDefaultTimestamp();
//		}
//		sb.append(timestamp / 60 * 60);
//		sb.append(V_SPACE);
//		sb.append(value);
//		sb.append(V_SPACE);
//		
//		if(!CommonTool.isEmpty(tags)) {
//			for(String key : tags.keySet()) {
//				if(!CommonTool.isEmpty(tags.get(key))){
//					sb.append(Base64Codec2.encode(key));
//					sb.append(V_TAG_EQ);
//					sb.append(Base64Codec2.encode(tags.get(key)));
//					sb.append(V_SPACE);
//				}
//			}
//			
//			sb.delete(sb.length() - LEN_SPACE, sb.length());
//		}
//		
//		return sb.toString();
//	}
//
//	public Map<String, Object> getElasticSource() {
//		Map<String, Object> sourceMap = new HashMap<String, Object>();
//		sourceMap.put("value", value);
//		sourceMap.put("timestamp", timestamp);
//
//		if(!CommonTool.isEmpty(tags)) {
//			for(String key : tags.keySet()) {
//				//bmid not store in es
//				if(!key.startsWith("bmid") && !CommonTool.isEmpty(tags.get(key))){
//					sourceMap.put(key, tags.get(key));
//				}
//			}
//		}
//		return sourceMap;
//	}
//	
//	private static final String V_PUT 		= "put";
//	private static final String V_SPACE 	= " ";
//	private static final int LEN_SPACE 		= V_SPACE.length();
//	private static final String V_TAG_EQ 	= "=";
//	
//}
