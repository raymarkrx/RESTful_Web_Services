package com.aichuche.servlet.chh;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chh.utils.HttpUtil;
import com.chh.utils.PrintUtils;

/**
 *  
 * 只是写的一个示例，filePath,和FileName根据需要进行调整。
 */
public class testRevokeServlet {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	testRevokeServlet.revokeWebService103();
    }
    
    public static String  revokeWebService103() throws Exception{

		URIBuilder builder = new URIBuilder();
		String host="127.0.0.1";//203.84.197.25 http://210.51.31.67/youyun/simplepush
		//String host="table.finance.yahoo.com";
		builder.setScheme("http").setHost(host).setPort(8080)
		.setPath("/RESTful_Web_Services/reportdata103")
		.setParameter("deviceId","+8618516535783")//默认执行encoding的操作
		.setParameter("messageId","53")
		.setParameter("dataType","2")
		.setParameter("data","Z2+XtVU1AA==")
		.setParameter("createTime","20150626142430");


		URI uri = builder.build();
		HttpGet httpget = new HttpGet(uri);
		PrintUtils.print("HttpGet.toString: "+httpget.toString());

		//System.out.println(httpget.getURI());
		//System.out.println("executing request " + httpget.getURI());

		long t1=System.currentTimeMillis();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		DefaultHttpClient httpclient = HttpUtil.getHttpClient();
		String responseQuotes = httpclient.execute(httpget, responseHandler);   
		long t2=System.currentTimeMillis();
		PrintUtils.print("==revokeWebService103："+responseQuotes);
		return responseQuotes;
        
	}

}