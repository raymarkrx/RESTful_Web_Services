package com.aichuche.web;
//package com.bianfeng.bfas.bfrd.web;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.eclipse.jetty.http.HttpURI;
//
//public class Session implements ISession{
//	private HttpServletRequest request;
//	
//	private String ip = null;
//	
//	private boolean isFindRemoteIp = false;
//	
//	public HttpServletRequest getRequest() {
//		return request;
//	}
//
//	public void setRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//
//	public String getRemoteIpAddr() {
//		if(isFindRemoteIp){
//			return ip;
//		}
//		
//		HttpServletRequest request = getRequest();
//		
//		String ip = request.getHeader("x-forwarded-for");
//		
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("x-real-ip");
//		}
//		
//		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		
//		//如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值，
//		//如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100,
//		//用户真实IP为X-Forwarded-For中第一个非unknown的有效IP字符串： 192.168.1.110
////		int multiSplitIndex = ip.indexOf(',');
////		if(multiSplitIndex > -1){
////			ip = ip.substring(0, multiSplitIndex);
////		}
//		
//		this.ip = ip;
//		isFindRemoteIp = true;
//		
//		return ip;
//	}
//	
//	
//	public String getRequestUri(){
//		if(this.request instanceof org.eclipse.jetty.server.Request){
//			HttpURI uri = ((org.eclipse.jetty.server.Request)request).getUri();
//			return uri.toString();
//		}else{
//			return null;
//		}
//	}
//}
