package com.aichuche.dto;

import java.io.Serializable;

public class RequestWarpper implements Serializable{

	private static final long serialVersionUID = 2084377959117459459L;

	private String type;
	
	private String remoteIp;
	
	private long receiveTimestamp;
	
	private Object value;
	
	private String uuid;
	
	private byte[] bytes;
	
	private String requestUri;
	
	private String msg;
	
	private boolean isRepair;
	
	private String repairPath;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public long getReceiveTimestamp() {
		return receiveTimestamp;
	}

	public void setReceiveTimestamp(long receiveTimestamp) {
		this.receiveTimestamp = receiveTimestamp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isRepair() {
		return isRepair;
	}

	public void setRepair(boolean isRepair) {
		this.isRepair = isRepair;
	}

	public String getRepairPath() {
		return repairPath;
	}

	public void setRepairPath(String repairPath) {
		this.repairPath = repairPath;
	}
}
