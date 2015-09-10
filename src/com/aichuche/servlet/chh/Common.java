package com.aichuche.servlet.chh;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.chh.utils.DateUtils;
import com.chh.utils.PrintUtils;
import com.chh.utils.encoding.EncodeUtils;

import net.sf.json.*;

public class Common {

	static Logger log= Logger.getLogger(Common.class);
    public void log(){
       log.debug("Debug info.");
       log.info("Info info");
       log.warn("Warn info");
       log.error("Error info");
       log.fatal("Fatal info");
       System.out.println("end");
    }

    // 创建JSONObject对象
    private static JSONObject createJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "huangwuyi");
        jsonObject.put("sex", "男");
//        jsonObject.put("QQ", "413425430");
//        jsonObject.put("Min.score", new Integer(99));
//        jsonObject.put("nickname", "梦中心境");
        return jsonObject;
    }

    public static void main(String[] args) throws Exception {
    	
    	System.out.println(DateUtils.getUnixTimestampFromLocalTimeDate2("20150727102903"));
    	System.out.println((int)DateUtils.getUnixTimestampFromLocalTimeDate2("20150727102903"));
    	
    	System.out.println("===="+Integer.MAX_VALUE);
    	String DataTypeID="103";
    	int datetimeUnix= 1437964143;
    	String messageid1="194";
    	int messagecode=7;
    	
    	byte[] result1=null;
     	
     	byte[] byte1=EncodeUtils.intToByte1(Integer.parseInt(DataTypeID));
     	byte[] byte4=EncodeUtils.intToByte4(datetimeUnix);
     	result1=EncodeUtils.combineTowBytes(byte1, byte4);
     	
     	byte1=EncodeUtils.intToByte4(Integer.parseInt(messageid1));
     	result1=EncodeUtils.combineTowBytes(result1, byte1);
     	
     	byte1=EncodeUtils.intToByte4(messagecode);
     	result1=EncodeUtils.combineTowBytes(result1, byte1);
     	String dataRAW=EncodeUtils.base64Encode(result1);
     	//dataRAW="Z8s+t1UAAAAAAQAAAA==";
     	System.out.println(dataRAW);
     	result1=EncodeUtils.base64Decode(dataRAW);
     	
     	System.out.println("解码后,第0个字节："+String.valueOf(EncodeUtils.bytesToInt1(EncodeUtils.splitBytesArray(result1,0,1))));	
     	System.out.println("解码后,第1-4个字节："+String.valueOf(EncodeUtils. bytesToInt4(EncodeUtils.splitBytesArray(result1,1,4))));
     	System.out.println("解码后,第5-8个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,5,4))));
     	System.out.println("解码后,第9-10个字节："+String.valueOf( EncodeUtils.bytesToInt4(EncodeUtils.splitBytesArray(result1,9,4))));

     	
     	
    	
//        JSONObject jsonObject = Common.createJSONObject();//静待方法，直接通过类名+方法调用
//        // 输出jsonobject对象
//        System.out.println("jsonObject：" + jsonObject);
//
//        // 判读输出对象的类型
//        boolean isArray = jsonObject.isArray();
//        boolean isEmpty = jsonObject.isEmpty();
//        boolean isNullObject = jsonObject.isNullObject();
//        System.out.println("是否为数组:" + isArray + "， 是否为空:" + isEmpty
//                + "， isNullObject:" + isNullObject);
//
//        // 添加属性，在jsonObject后面追加元素。
//        jsonObject.element("address", "福建省厦门市");
//        System.out.println("添加属性后的对象：" + jsonObject);
//
//        // 返回一个JSONArray对象
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(0, "this is a jsonArray value");
//        jsonArray.add(1, "another jsonArray value");
//        jsonObject.element("jsonArray", jsonArray);
//        //在jsonObject后面住家一个jsonArray
//        System.out.println(jsonObject);
//       
//        JSONArray array = jsonObject.getJSONArray("jsonArray");
//        System.out.println("返回一个JSONArray对象：" + array);

        //        // 添加JSONArray后的值
//        // {"username":"huangwuyi","sex":"男","QQ":"413425430","Min.score":99,"nickname":"梦中心境","address":"福建省厦门市","jsonArray":["this is a jsonArray value","another jsonArray value"]}
//        System.out.println("结果=" + jsonObject);
//
//        // 根据key返回一个字符串
//        String username = jsonObject.getString("username");
//        System.out.println("username==>" + username);
//
//        // 把字符转换为 JSONObject
//        String temp = jsonObject.toString();
//        JSONObject object = JSONObject.fromObject(temp);
//        // 转换后根据Key返回值
//        System.out.println("qq=" + object.get("QQ"));

    }

    

}
