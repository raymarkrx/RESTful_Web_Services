package com.chh.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
    public static final String[] months = { "一月", "二月", "三月", "四月", "五月", "六月",
     "七月", "八月", "九月", "十月", "十一月", "十二月", };

    public static final String[] quarters = { "一季度", "二季度", "三季度", "四季度" };

    public DateUtils() {
    }

    /**
     * 获取日期字符串。
     * 
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     * 
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getDate() {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

 return formatter.format(new Date());
    }

    /**
     * 获取当前年度字符串。
     * 
     * <pre>
     *  日期字符串格式： yyyy
     *  其中：
     *      yyyy   表示4位年。
     * </pre>
     * 
     * @return String "yyyy"格式的当前年度字符串。
     */
    public static String getNowYear() {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

 return formatter.format(new Date());
    }

    /**
     * 获取当前月度字符串。
     * 
     * <pre>
     *  日期字符串格式： MM
     *  其中：
     *      MM   表示4位年。
     * </pre>
     * 
     * @return String "yyyy"格式的当前月度字符串。
     */
    public static String getNowMonth() {
 SimpleDateFormat formatter = new SimpleDateFormat("MM");

 return formatter.format(new Date());
    }

    /**
     * 获取当前月度字符串。
     * 
     * <pre>
     *  日期字符串格式： dd
     *  其中：
     *      dd   表示4位年。
     * </pre>
     * 
     * @return String "yyyy"格式的当前月度字符串。
     */
    public static String getNowDay() {
 SimpleDateFormat formatter = new SimpleDateFormat("dd");

 return formatter.format(new Date());
    }

    /**
     * 获取日期字符串。
     * 
     * <pre>
     *  日期字符串格式： yyyyMMdd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     * 
     * @param date
     *                需要转化的日期。
     * @return String "yyyyMMdd"格式的日期字符串。
     */
    public static String getDate(Date date) {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

 return formatter.format(date);
    }


    /**
     * 获取日期字符串。
     * 
     * <pre>
     *  日期字符串格式： yyyy-MM-dd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     * 
     * @return String "yyyy-MM-dd"格式的日期字符串。
     */
    public static String getHyphenDate() {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

 return formatter.format(new Date());
    }

    /**
     * 获取日期字符串。
     * 
     * <pre>
     *  日期字符串格式： yyyy-MM-dd
     *  其中：
     *      yyyy   表示4位年。
     *      MM     表示2位月。
     *      dd     表示2位日。
     * </pre>
     * 
     * @param date
     *                需要转化的日期。
     * @return String "yyyy-MM-dd"格式的日期字符串。
     */
    public static String getHyphenDate(Date date) {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

 return formatter.format(date);
    }

    /**
     * 将"yyyyMMdd"格式的日期字符串转换为日期对象。
     * 
     * @param source
     *                日期字符串。
     * @return Date 日期对象。
     */
    public static Date parsePlainDate(String source) {
 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

 return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将“yyyy-MM-dd”格式的日期字符串转换为日期对象。
     * 
     * @param source
     *                日期字符串。
     * @return Date 日期对象。
     */
    public static Date parseHyphenDate(String source) {
 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

 return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将指定格式的日期字符串转换为日期对象。
     * 
     * @param source
     *                日期字符串。
     * @param pattern
     *                模式。
     * @return Date 日期对象。
     */
    public static Date parseDate(String source, String pattern) {
 SimpleDateFormat sdf = new SimpleDateFormat(pattern);

 return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将“yyyy-MM-dd”格式的日期字符串转换为“yyyyMMdd”格式的日期字符串。
     * 
     * @param source
     *                日期字符串。
     * @return String "yyyymmdd"格式的日期字符串。
     */
    public static String toPlainDate(String source) {
 Date date = parseHyphenDate(source);
 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

 return formatter.format(date);
    }
    

    /** 
     * 将UNIX时间戳转为本地时间
     * @param UTCTime 
     * @return 
     */  
    public static String getLocalTimeStrFromUnixTimestamp(String UnixTimestamp){
    	  Long timestamp = Long.parseLong(UnixTimestamp)*1000; 
    	  String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date(timestamp));    
    	  return date; 
    }
    
    /** 
     * 将UNIX时间戳转为本地时间
     * @param UTCTime 
     * @return 
     */  
    public static Date  getLocalTimeDateFromUnixTimestamp(String UnixTimestamp){  
    	  Long timestamp = Long.parseLong(UnixTimestamp)*1000;    
    	  return new java.util.Date(timestamp);
    } 
    
    /** 
     * 将本地时间转为UNIX时间戳
     * @param UTCTime 
     * @return 
     * @throws ParseException 
     */  
    public static long  getUnixTimestampFromLocalTimeDate(String dateStr) throws ParseException{  
    	long epoch = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr).getTime();
    	return epoch/1000;
    } 
    
    public static long  getUnixTimestampFromLocalTimeDate2(String dateStr) {  
    	long epoch=1L;
		try {
			epoch = new java.text.SimpleDateFormat("yyyyMMddHHmmss").parse(dateStr).getTime();
			epoch=epoch/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return epoch;
    } 
    

    /**
     * 将“yyyyMMdd”格式的日期字符串转换为“yyyy-MM-dd”格式的日期字符串。
     * 
     * @param source
     *                日期字符串。
     * @return String "yyyy-MM-dd"格式的日期字符串。
     */
    public static String toHyphenDate(String source) {
 Date date = parsePlainDate(source);
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

 return formatter.format(date);
    }

    /**
     * 获取时间戳，将日期对象转换为时间戳类型。
     * 
     * @param date
     *                日期对象
     * @return Timestamp 时间戳
     */
    public static Timestamp getTimestamp(Date date) {
 return new Timestamp(date.getTime());
    }

    /**
     * 获取时间戳，将当前日期转换为时间戳类型。
     * 
     * @return Timestamp 时间戳
     */
    public static Timestamp getTimestamp() {
 return new Timestamp(new Date().getTime());
    }

    /**
     * 将“yyyyMMdd”格式的日期字符串转换为Timestamp类型的对象。
     * 
     * @param source
     *                日期字符串
     * @return Timestamp 时间戳
     */
    public static Timestamp parseTimestamp(String source) {
 Date date = parsePlainDate(source);

 return getTimestamp(date);
    }

    /**
     * 获得年度周期 <br>
     * Example:<br>
     * XJPDateUtil.getPeriodYear("20040229" , -1);<br>
     * XJPDateUtil.getPeriodYear("20040228" , -1);<br>
     * XJPDateUtil.getPeriodYear("20020230" , 2);<br>
     * 
     * @param source
     *                时间串
     * @param yearPeriod
     *                年度周期 -1代表本时间的上一年度,以次类推。
     * @return String 时间。
     */
    public static String getPeriodYear(String source, int yearPeriod) {
 int p = Integer.parseInt(source.substring(0, 4)) + yearPeriod;
 String newYear = String.valueOf(p)
  + source.substring(4, source.length());
 Date date = parsePlainDate(newYear);
 String s = getDate(date);
 int ny = Integer.parseInt(s);
 int sy = Integer.parseInt(newYear);

 while (ny > sy) {
     sy--;
     ny = Integer.parseInt(getDate(parsePlainDate(String.valueOf(sy))));
 }

 return String.valueOf(sy);
    }

    /**
     * 获取当前日期和时间
     * 
     * @return String
     */
    public static String getCurrentDateStr() {
 Date date = new Date();
 String str = null;
 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
 str = df.format(date);
 return str;
    }

    /**
     * 日期相加
     *
     * @param day
     *             天数
     * @return 返回相加后的日期
     */
    public static String addDate(int day) {
 java.util.Calendar c = java.util.Calendar.getInstance();

 c.setTimeInMillis(System.currentTimeMillis() + ((long) day) * 24 * 3600
  * 1000);
 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
 return df.format(c.getTime());
    }

    /**
     * 返回毫秒
     * 
     * @param date
     *             日期
     * @return 返回毫秒
     */
    public static long getMillis(java.util.Date date) {
 java.util.Calendar c = java.util.Calendar.getInstance();
 c.setTime(date);
 return c.getTimeInMillis();
    }
    /**
     * 获取当前日期和时间
     * @param format 日期格式 例：yyyy-MM-dd hh:mm
     * @return String
     */
    public static String getNowDate(String format) {
 Date date = new Date();
 String str = null;
 SimpleDateFormat df = new SimpleDateFormat(format);
 str = df.format(date);
 return str;
    }
    /**
     * 将strmon的日期减小一个月
     * @param mon
     * @return
     */
    public static String getReduceMonDate(String strmon) {
 if (strmon != null && !strmon.equals("")) {
     Date mon = parseHyphenDate(strmon);
     mon.setMonth(mon.getMonth() - 1);
     return getHyphenDate(mon);
 } else {
     return "";
 }
    }

  public static String getTimeStr(){
      String str="";
      SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
  str = df.format(new Date());
  return str;
  }
}