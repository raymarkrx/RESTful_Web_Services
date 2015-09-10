 package com.chh.utils;
 
 import org.apache.log4j.FileAppender;
 import org.apache.log4j.Level;
 import org.apache.log4j.Logger;
 import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.varia.LevelRangeFilter;
 
 public class LoggerFactory
 {
   public static Logger getMonitorLogger(String filePath, String dateTime, String type)
   {
	   PropertyConfigurator.configure("classes/my.properties");
     Logger logger = Logger.getLogger("MONITOR");
     try
     {
       filePath = filePath.replace("yyyyMMddHH", dateTime)
         .replace("TYPE", type);
       PatternLayout playout = new PatternLayout();
       playout.setConversionPattern("%m%n");
       FileAppender fAppender = new FileAppender(playout, 
         filePath);
       fAppender.setEncoding("UTF-8");
       fAppender.setAppend(true);
       fAppender.setThreshold(Level.INFO);
       fAppender.setLayout(playout);
       LevelRangeFilter filterInfo = new LevelRangeFilter();
       filterInfo.setLevelMin(Level.INFO);
       filterInfo.setLevelMax(Level.INFO);
       fAppender.addFilter(filterInfo);
       logger.setAdditivity(false);
       logger.addAppender(fAppender);
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     return logger;
   }
 
   public static Logger getSuccessLogger(String filePath, String dateTime, String type)
   {
     Logger logger = Logger.getLogger("POISUCCESS");
     try
     {
       filePath = filePath.replace("yyyyMMddHH", dateTime)
         .replace("TYPE", type);
       PatternLayout playout = new PatternLayout();
       playout.setConversionPattern("%m%n");
       FileAppender fAppender = new FileAppender(playout, 
         filePath);
       fAppender.setEncoding("UTF-8");
       fAppender.setAppend(true);
       fAppender.setThreshold(Level.INFO);
       fAppender.setLayout(playout);
       LevelRangeFilter filterInfo = new LevelRangeFilter();
       filterInfo.setLevelMin(Level.INFO);
       filterInfo.setLevelMax(Level.INFO);
       fAppender.addFilter(filterInfo);
       logger.setAdditivity(false);
       logger.addAppender(fAppender);
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     return logger;
   }
 
   public static Logger getReSuccessLogger(String filePath, String dateTime, String type)
   {
     Logger logger = Logger.getLogger("REPOISUCCESS");
     try
     {
       filePath = filePath.replace("yyyyMMddHH", dateTime)
         .replace("TYPE", type);
       PatternLayout playout = new PatternLayout();
       playout.setConversionPattern("%m%n");
       FileAppender fAppender = new FileAppender(playout, 
         filePath);
       fAppender.setEncoding("UTF-8");
       fAppender.setAppend(true);
       fAppender.setThreshold(Level.INFO);
       fAppender.setLayout(playout);
       LevelRangeFilter filterInfo = new LevelRangeFilter();
       filterInfo.setLevelMin(Level.INFO);
       filterInfo.setLevelMax(Level.INFO);
       fAppender.addFilter(filterInfo);
       logger.setAdditivity(false);
       logger.addAppender(fAppender);
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     return logger;
   }
 
   public static Logger getErrorLogger(String filePath, String dateTime, String type)
   {
     Logger logger = Logger.getLogger("ERRORLOG");
     try
     {
       filePath = filePath.replace("yyyyMMddHH", dateTime)
         .replace("TYPE", type);
       PatternLayout playout = new PatternLayout();
       playout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss,SSS} %c %l: %m%n");
       FileAppender fAppender = new FileAppender(playout, 
         filePath);
       fAppender.setEncoding("UTF-8");
       fAppender.setAppend(true);
       fAppender.setThreshold(Level.ERROR);
       fAppender.setLayout(playout);
       LevelRangeFilter filterInfo = new LevelRangeFilter();
       filterInfo.setLevelMin(Level.ERROR);
       filterInfo.setLevelMax(Level.ERROR);
       fAppender.addFilter(filterInfo);
       logger.setAdditivity(false);
       logger.addAppender(fAppender);
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     return logger;
   }
 
   public static void main(String[] args)
   {
   }
 }
//
///* Location:           D:\saic_days_2015\2poi\LBSPoiJobService.jar
// * Qualified Name:     com.lbs.poi.vcar.utils.LoggerFactory
// * JD-Core Version:    0.6.2
