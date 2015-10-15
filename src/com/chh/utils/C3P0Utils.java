package com.chh.utils;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {   
    private static C3P0Utils dbcputils=null;   
    private static ComboPooledDataSource cpds=null;   
	private static Properties properties = new Properties();
	private static String jdbc=new String();
	private static String url=new String();
	private static String username=new String();
	private static String passwd=new String();
    private static String isProduction=new String();
	private static InputStream in = null;
	
   static{
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
	   
	   
        if(cpds==null){   
            cpds=new ComboPooledDataSource();   
        }   
        	cpds.setUser(username);   
        	cpds.setPassword(passwd);   
        	cpds.setJdbcUrl(url);   
            cpds.setDriverClass(jdbc);   
            
            cpds.setInitialPoolSize(20);   
            cpds.setMaxIdleTime(20);   
            cpds.setMaxPoolSize(40);   
            cpds.setMinPoolSize(20);   
            
        } catch (Exception e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }   
    }   
    public synchronized static C3P0Utils getInstance(){   
        if(dbcputils==null)   
            dbcputils=new C3P0Utils();   
        return dbcputils;   
    }   
    public static Connection getConnection(){
        Connection con=null;   
        try {   
            con=cpds.getConnection();   
        } catch (SQLException e) {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }
        return con;   
    }   
       
    public static void main(String[] args) throws SQLException {  
 Connection con=null;   
        long begin=System.currentTimeMillis();   
        for(int i=0;i<10;i++){   
            con=C3P0Utils.getConnection();   
            con.close();
        }      
        long end=System.currentTimeMillis();   
        System.out.println("耗时为:"+(end-begin)+"ms");   
    }   
}  	
 