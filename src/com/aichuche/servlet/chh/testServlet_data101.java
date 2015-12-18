package com.aichuche.servlet.chh;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chh.utils.DateUtils;
import com.chh.utils.HttpUtil;
import com.chh.utils.PrintUtils;
import com.chh.utils.encoding.EncodeUtils;

 
public class testServlet_data101 {
	public static String DEFAULT_TEST="chhTestData101";//判断是否测试的消息，不要改动
	public static void main(String[] args) throws Exception {
		testServlet_data101 testIt=new testServlet_data101();
		testIt.testWebService();
	}
	
	public void  testWebService() throws Exception {
        List<String> a=new ArrayList<String>();
        a.add("0,0");//加速度和速度
        a.add("0,0");
        a.add("1,30");//s3
        a.add("2,30");//s4
        a.add("3,30");//s5
        
        Random rand = new Random();
        for(int i=0;i<1;i=i+1){
        	int j=i%a.size();
        	String b=a.get(j);
        	String[] c=b.split(",");
        	String speed=c[0];
        	String ax=c[1];
        	//PrintUtils.print(j+","+ax+","+speed);
        	
        	String currentDateUnixTimestamp   =String.valueOf(DateUtils.getUnixTimestampFromCurrentDate());//yyyyMMddHHmmss
        	String currentDate=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.getLocalTimeDateFromUnixTimestamp(currentDateUnixTimestamp));
        	
        	//发送的消息由5个字段构成（deviceId，messageId，dataType，data（由明细字段生成），createTime）
        	//String deviceId     =i%2==0?"+86test_1":"+86test_2";
        	//String deviceId  ="test8618616969935"+rand.nextInt(99);
        	
        	//String deviceId  ="352273017386360";//deviceId字段 352273017386360
        	String deviceId  =DEFAULT_TEST;
 			String messageId="0";//递增的序列
 			String dataType="2";//表示二进制的加密后的数据
 			String data;//这个字段由下面的字段构成
        	// 定义data的明细字段
    		int DataTypeID=101;
    		int Date=Integer.valueOf(currentDateUnixTimestamp);
    		String Ax=ax;
    		String Ay="-0.6512";//4位小数
    		String Az="9.3278";
    		String Wx="-0.0097";
    		String Wy="-0.0024";
    		String Wz="-0.0061";
    		String Tx="-17.1875";
    		String Ty="-1.8750";
    		String Tz="30.5625";
    		String GPSX="31.253138";//6位小数
    		String GPSY="121.354008";//6位小数
    		String Speed=speed;
    		data=DataTypeID+","+Date+","+Ax+","+Ay+","+Az+","+Wx+","+Wy+","+Wz+","+Tx+","+Ty+","+Tz+","+GPSX+","+GPSY+","+Speed;
    		//数据生成时间
 			String createTime=currentDate;//数据生成时间
 			
 			//要发送的data101数据
        	String message101    =deviceId+";"+messageId+";"+dataType+";"+data+";"+createTime;
        	PrintUtils.print("send to webService  :"+message101);
        	
        	//把data转为字节流，只有DataTypeID是一个byte，其他的都是4个byte
         	byte[] result=null;
         	
         	byte[] byte1=EncodeUtils.intToByte1(DataTypeID);
         	byte[] byte4_Date=EncodeUtils.intToByte4(Date);
         	result=EncodeUtils.combineTowBytes(byte1, byte4_Date);
         	byte[] byte4_Ax=EncodeUtils.intToByte4((int)Double.parseDouble(Ax)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Ax);
         	byte[] byte4_Ay=EncodeUtils.intToByte4((int)Double.parseDouble(Ay)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Ay);
         	byte[] byte4_Az=EncodeUtils.intToByte4((int)Double.parseDouble(Az)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Az);
         	byte[] byte4_Wx=EncodeUtils.intToByte4((int)Double.parseDouble(Wx)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Wx);
         	byte[] byte4_Wy=EncodeUtils.intToByte4((int)Double.parseDouble(Wy)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Wy);
         	byte[] byte4_Wz=EncodeUtils.intToByte4((int)Double.parseDouble(Wz)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Wz);
         	byte[] byte4_Tx=EncodeUtils.intToByte4((int)Double.parseDouble(Tx)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Tx);
         	byte[] byte4_Ty=EncodeUtils.intToByte4((int)Double.parseDouble(Ty)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Ty);
         	byte[] byte4_Tz=EncodeUtils.intToByte4((int)Double.parseDouble(Tz)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Tz);
         	byte[] byte4_GPSX=EncodeUtils.intToByte4((int)Double.parseDouble(GPSX)*1000000);
         	result=EncodeUtils.combineTowBytes(result, byte4_GPSX);
         	byte[] byte4_GPSY=EncodeUtils.intToByte4((int)Double.parseDouble(GPSY)*1000000);
         	result=EncodeUtils.combineTowBytes(result, byte4_GPSY);
         	byte[] byte4_Speed=EncodeUtils.intToByte4((int)Double.parseDouble(Speed)*10000);
         	result=EncodeUtils.combineTowBytes(result, byte4_Speed);
         	
            //对字节流base64加密，生成加密字符串
         	String dataRAW=EncodeUtils.base64Encode(result);
         	
         	Map<String, String> keyValues=new HashMap<String, String>();
	         keyValues.put("deviceId", deviceId);//
	         keyValues.put("messageId", messageId);
	         keyValues.put("dataType", dataType);
	         keyValues.put("data", dataRAW);
	         keyValues.put("createTime", createTime);
	         
	         //调用webService
	         revokeServletData101(keyValues);
        	
        	Thread.sleep(1000*1);
        } 
        
        //System.out.println("=====================OVER================");
        
	}
	
	  public  String  revokeServletData101(Map<String, String> keyValues) throws Exception{
		  
			String deviceId=keyValues.get("deviceId");
			String messageId=keyValues.get("messageId");
			String dataType=keyValues.get("dataType");
			String dataRAW=keyValues.get("data");
			String createTime=keyValues.get("createTime");
			

			URIBuilder builder = new URIBuilder();//210.51.31.67
			String host="180.97.232.56";//http://180.97.232.56:9088/RESTful_Web_Services/reportdata?deviceId=deviceId&messageId=messageId&dataType=1&data=data&createTime=20150626142430 
			//String host="127.0.0.1";
			builder.setScheme("http").setHost(host).setPort(9088)
			.setPath("/RESTful_Web_Services/reportdata")
			.setParameter("deviceId",deviceId)//默认执行encoding的操作
			.setParameter("messageId",messageId)
			.setParameter("dataType",dataType)
			.setParameter("data",dataRAW)
			.setParameter("createTime",createTime);


			URI uri = builder.build();
			HttpGet httpget = new HttpGet(uri);
			//PrintUtils.print("HttpGet.toString: "+httpget.toString());

			//System.out.println(httpget.getURI());
			//System.out.println("executing request " + httpget.getURI());

			long t1=System.currentTimeMillis();
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			DefaultHttpClient httpclient = HttpUtil.getHttpClient();
			String responseQuotes = httpclient.execute(httpget, responseHandler);   
			long t2=System.currentTimeMillis();
			PrintUtils.print("==revokeWebServlet101 http://180.97.232.56/RESTful_Web_Services/reportdata  cost(ms): "+(t2-t1)+" ms,return :"+responseQuotes);
			return responseQuotes;
	        
		}
	
	
	
	
 
}
