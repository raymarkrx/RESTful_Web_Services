package com.chh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.io.Reader;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.net.ssl.SSLException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.ExecutionContext;
 
 public class HttpUtil
 {
   public static String executeGet(String url)
     throws Exception
   {
     GetMethod method = new GetMethod(url);
     String poi = null;
     try {
       HttpClient client = new HttpClient();
       System.out.println("### URL: " + url + " ###");
       int statusCode = client.executeMethod(method);
       System.out.println("### Status: " + statusCode + " ###");
       if (statusCode == 200)
         poi = convertStreamToString(method.getResponseBodyAsStream());
       else
         System.err.println("Response Code: " + statusCode);
     }
     catch (HttpException e) {
       System.err.println("Fatal protocol violation: " + e.getMessage());
     } catch (IOException e) {
       System.err.println("Fatal transport error: " + e.getMessage());
     } finally {
       method.releaseConnection();
     }
     return poi;
   }
   
   //得到自动重试的httpclient
   public static DefaultHttpClient getHttpClient(){
	   
		DefaultHttpClient httpclient = new DefaultHttpClient();
		Integer socketTimeout = 300*1000;
		Integer connectionTimeout = 300*1000;
		final int retryTime = 5;//重试次数
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
		httpclient.getParams().setParameter(CoreConnectionPNames.TCP_NODELAY, false);
		httpclient.getParams().setParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 1024 * 1024);
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler()
		{
			@Override
			public boolean retryRequest(IOException exception, int executionCount, org.apache.http.protocol.HttpContext context)
			{
				if (executionCount >= retryTime)
				{
					// Do not retry if over max retry count
					return false;
				}
				if (exception instanceof InterruptedIOException)
				{
					// Timeout
					return false;
				}
				if (exception instanceof UnknownHostException)
				{
					// Unknown host
					return false;
				}
				if (exception instanceof ConnectException)
				{
					// Connection refused
					return false;
				}
				if (exception instanceof SSLException)
				{
					// SSL handshake exception
					return false;
				}
				HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent)
				{
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}

		};
		httpclient.setHttpRequestRetryHandler(myRetryHandler);
	   
		return httpclient;
   }
   
 
   public static String executeGet(String url, String host, String port, String userName, String pwd) throws Exception
   {
     GetMethod method = new GetMethod(url);
     String poi = null;
     try {
       HttpClient client = new HttpClient();
 
       setHttpProxy(client, host, port, userName, pwd);
       System.out.println("### URL: " + url + " ###");
       int statusCode = client.executeMethod(method);
       System.out.println("### Status: " + statusCode + " ###");
       if (statusCode == 200)
         poi = convertStreamToString(method.getResponseBodyAsStream());
       else
         System.err.println("Response Code: " + statusCode);
     }
     catch (HttpException e) {
       System.err.println("Fatal protocol violation: " + e.getMessage());
     } catch (IOException e) {
       System.err.println("Fatal transport error: " + e.getMessage());
     } finally {
       method.releaseConnection();
     }
     return poi;
   }
 
   private static String convertStreamToString(InputStream ins)
     throws Exception
   {
     String strRet = "";
     Reader buf = null;
     InputStreamReader reader = null;
     try
     {
       StringBuffer sbuf = new StringBuffer();
       reader = new InputStreamReader(ins, "UTF-8");
       buf = new BufferedReader(reader);
       int ch;
       while ((ch = buf.read()) > -1)
       {
         //int ch;
         sbuf.append((char)ch);
       }
       strRet = sbuf.toString();
       sbuf.delete(0, sbuf.length());
     } catch (Exception e) {
       e.printStackTrace();
       throw e;
     } finally {
       try {
         if (buf != null) {
           buf.close();
         }
         if (reader != null)
           reader.close();
       }
       catch (IOException localIOException) {
       }
     }
     return strRet;
   }
 
   public static String sendRestfulRequest(String url, String param)
     throws Exception
   {
     String result = "";
     HttpURLConnection conn = null;
     BufferedReader in = null;
     try {
       String urlName = url + "?" + param;
       URL realUrl = new URL(urlName);
 
       conn = (HttpURLConnection)realUrl.openConnection();
 
       conn.setRequestProperty("accept", "*/*");
       conn.setRequestProperty("connection", "Keep-Alive");
       conn.setConnectTimeout(30000);
       conn.setReadTimeout(30000);
 
       conn.connect();
       result = convertStreamToString(conn.getInputStream());
     } catch (Exception e) {
       e.printStackTrace();
       throw e;
     } finally {
       try {
         if (in != null) {
           in.close();
         }
         if (conn != null)
           conn.disconnect();
       }
       catch (IOException ex) {
         ex.printStackTrace();
       }
     }
     return result;
   }
 
   public static String sendRestfulRequest(String url, String param, String host, String port, String nonProxyHosts, String userName, String password)
     throws Exception
   {
     String result = "";
     HttpURLConnection conn = null;
     BufferedReader in = null;
     try
     {
       setHttpProxy(host, port, nonProxyHosts, userName, password);
 
       String urlName = url + "?" + param;
       URL realUrl = new URL(urlName);
 
       conn = (HttpURLConnection)realUrl.openConnection();
 
       conn.setRequestProperty("accept", "*/*");
       conn.setRequestProperty("connection", "Keep-Alive");
       conn.setConnectTimeout(30000);
       conn.setReadTimeout(30000);
 
       conn.connect();
       result = convertStreamToString(conn.getInputStream());
     } catch (Exception e) {
       e.printStackTrace();
       throw e;
     } finally {
       try {
         if (in != null) {
           in.close();
         }
         if (conn != null)
           conn.disconnect();
       }
       catch (IOException ex) {
         ex.printStackTrace();
       }
     }
     return result;
   }
 
   private static void setHttpProxy(String proxyHost, String proxyPort, String nonProxyHosts, String userName, String password)
   {
     Properties prop = System.getProperties();
 
     prop.setProperty("http.proxyHost", proxyHost);
 
     prop.setProperty("http.proxyPort", proxyPort);
 
     prop.setProperty("http.nonProxyHosts", nonProxyHosts);
 
     Authenticator.setDefault(new MyAuthenticator(userName, password));
   }
 
   private static void setHttpProxy(HttpClient httpClient, String host, String port, String userName, String pwd)
   {
     httpClient.getHostConfiguration()
       .setProxy(host, Integer.parseInt(port));
     httpClient.getParams().setAuthenticationPreemptive(true);
 
     httpClient.getState().setProxyCredentials(AuthScope.ANY, 
       new UsernamePasswordCredentials(userName, pwd));
   }
 
   public static void main(String[] args) {
     try {
       String url = "http://api.map.baidu.com/geocoder/v2/?ak=tvneSluSZWnSdhTeRdW63Nv7&output=json&coordtype=wgs84ll&pois=1&location=31.409676,121.254782";
       String str = executeGet(url);
       System.out.println(str);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   static class MyAuthenticator extends Authenticator
   {
     private String user = "";
     private String password = "";
 
     public MyAuthenticator(String user, String password) {
       this.user = user;
       this.password = password;
     }
 
     protected PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(this.user, this.password.toCharArray());
     }
   }
 }