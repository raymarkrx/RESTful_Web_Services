package com.chh.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils
{
	
	private static Properties properties = new Properties();
	private static String jdbc=new String();
	private static String url=new String();
	private static String username=new String();
	private static String passwd=new String();
    private static String isProduction=new String();
	private static InputStream in = null;
	
	 static {
	     try {
    		 //判断是否是生产环境的开关,1:加载生产环境的配置，0：加在测试环境的配置
    		 in=DbUtils.class.getResourceAsStream("/config/switch.properties");
    		
    		 properties.load(in);
    		 isProduction = properties.getProperty("isProduction");
    		 properties.clear();
    		 
    	        if("1".equals(isProduction)){
    	        	  in=DbUtils.class.getResourceAsStream("/config/db/jdbc_prod.properties");
    	          }else{
    	        	  in=DbUtils.class.getResourceAsStream("/config/db/jdbc_test.properties");
    	          }
  		 
	       properties.load(in);
	        jdbc = properties.getProperty("db.driverClassName");
	        url = properties.getProperty("db.url");
	        username = properties.getProperty("db.username");
	        passwd = properties.getProperty("db.password");
	        properties.clone();
	     }
	     catch (IOException e)
	     {
	       e.printStackTrace();
	     }finally{
	    	 try {
	    		 if (in!=null){
					in.close();
	    		 }
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
	 }
    
  public static void main(String[] args)
  {
	  DbUtils.getConn();
	  System.out.println("sql======");
  }
  
  public static Connection getConn()
  {
    Connection con = null;
    try {
      Class.forName(jdbc);
      con = DriverManager.getConnection(url, username, passwd);
      if (con == null)
      {
        System.out.println(" con is null");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return con;
  }
}