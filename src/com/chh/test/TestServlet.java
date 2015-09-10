package com.chh.test;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  
 * 只是写的一个示例，filePath,和FileName根据需要进行调整。
 */
public class TestServlet {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //String str="http://127.0.0.1:8080/RESTful_Web_Services/json?id=1&user=2&type=3";
        //String str="http://127.0.0.1:8080/RESTful_Web_Services/json?id=1&user=2&type=3";
        String str="http://127.0.0.1:8080/RESTful_Web_Services/reportdata?deviceId=xxx&messageId=x&dataType=zzz&data=ccc&createTime=xxx";
//        String filePath="/home/chh/getKafka2.txt";
//        String fileName="getKafka2.txt";
        try {
            URL url=new URL(str);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");//post方式
            //connection.addRequestProperty("FileName", fileName);
            connection.setRequestProperty("content-type", "text/html");
            BufferedOutputStream  out=new BufferedOutputStream(connection.getOutputStream());
            
//            //读取文件上传到服务器
//            File file=new File(filePath);
//            FileInputStream fileInputStream=new FileInputStream(file);
//            byte[]bytes=new byte[1024];
//            int numReadByte=0;
//            while((numReadByte=fileInputStream.read(bytes,0,1024))>0)
//            {
//            }
            //kafka 测试消息
            String kafkaMes="chh_aichuche_kafka";
            out.write(kafkaMes.getBytes(), 0, kafkaMes.getBytes().length);

            out.flush();
            out.close();
            
            //fileInputStream.close();

           // 一旦发送成功，用以下方法就可以得到服务器的回应：  
            String sCurrentLine;  
            String sTotalString;  
            sCurrentLine = "";  
            sTotalString = "";
            InputStream l_urlStream= connection.getInputStream();  
            // 传说中的三层包装阿！  
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(  l_urlStream));  
            while ((sCurrentLine = l_reader.readLine()) != null) {  
                sTotalString += sCurrentLine + "\r\n";  
      
            }  
            System.out.println(sTotalString);  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}