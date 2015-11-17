package com.aichuche.servlet.chh.model;

public class SimplepushReturnMessageBean {
	private String return_code;
	private String return_message;
	private ReturnData data;
	
	public SimplepushReturnMessageBean(String return_code,String return_message,ReturnData data){
		this.return_code=return_code;
		this.return_message=return_message;
		this.data=data;
	}
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_message() {
		return return_message;
	}
	public void setReturn_message(String return_message) {
		this.return_message = return_message;
	}
	public ReturnData getData() {
		return data;
	}
	public void setData(ReturnData data) {
		this.data = data;
	}
	

}

class ReturnData {
	private String data;
	
	public ReturnData(String data){
		this.data=data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
