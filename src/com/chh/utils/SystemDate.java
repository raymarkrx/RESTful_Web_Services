package com.chh.utils;

//import java.util.GregorianCalendar;
//import java.util.Calendar;
//import java.util.Date;
import java.util.*;
//import java.text.*;

/**
   ȡ��ϵͳ���ڣ�yyyy-mm-dd����ʱ��(hh-mm-ss)�����ڡ�����

   This is another date class, but more convenient that
   <tt>java.util.Date</tt> or <tt>java.util.Calendar</tt>

   @version 1.0 15 Jan 2002
   @author ZhangBiao
*/

public class SystemDate
{
   public static int SUNDAY = 1;
   public static int MONDAY = 2;
   public static int TUESDAY = 3;
   public static int WEDNESDAY = 4;
   public static int THURSDAY = 5;
   public static int FRIDAY = 6;
   public static int SATURDAY = 7;

   /** @serial 1~366*/
   private int year;
   /** @serial 0~11 */
   private int month;
   /** @serial 1~31*/
   private int day;
   private int day_of_week;//1~7��Ӧ��Sun~Sat
   private int day_of_month;//ͬday---1~31
   private int day_of_year;//1~366
   private int week_of_month;//1~6
   private int week_of_year;//1~54

   /** ʱ�� */
   /** hour_of_day 0~23 ��ʮ��Сʱ��*/
   private int hour_of_day;
   /** hour 0~12 ʮ��Сʱ��*/
   private int hour;
   /** minute 0~59*/
   private int minute;
   /** second 0~59 */
   private int second;
   /** millisecond 0~999*/
   private int millisecond;
   private int PM_Flag;//�Ƿ�Ϊ����:0-����;1-����;
// private Locale mylocal;
   private GregorianCalendar todaysDate=null;

//**********************************************************************//
   public SystemDate()
	{
	   //Constructs a default GregorianCalendar using the current time in the default time zone with the default locale.
	  todaysDate = new GregorianCalendar();
/*
	  year=todaysDate.get(Calendar.YEAR);
	  month = todaysDate.get(Calendar.MONTH) + 1;
	  day = todaysDate.get(Calendar.DAY_OF_MONTH);
	  week_of_month=todaysDate.get(Calendar.WEEK_OF_MONTH);//1~6
	  day_of_year=todaysDate.get(Calendar.DAY_OF_YEAR);//1~366
	  */
	}

//**********************************************************************//
/**���õ�ǰ���� zb add at 2002-04-16
*
*/	public void setNow()
	{
	  todaysDate = new GregorianCalendar();
	}//end setNow
  
//**********************************************************************//
/**ȡ�õ�ǰϵͳʱ�����ֵ������ͳ�Ƴ��������ʱ��
*@return int ��ǰʱ���"�������"�ĺ���ֵ
*zb add at 2002-04-17
*/
	public int getCurrentTimeUseMS()
	{
		int now_time=Integer.parseInt(getSystemMinute(10))*60+Integer.parseInt(getSystemSecond(10));//miss
		now_time=now_time*1000+Integer.parseInt(getSystemMilliSecond(10));//mmmm
		return now_time;
	}//end getCurrentTimeUseMS

//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return year 1900~
 */
	public String getSystemYear(int degree)
	{
		String systemdate="";
		 year = todaysDate.get(Calendar.YEAR);
		if (year<100)
			year+=1900;

		if(degree==2)systemdate+=""+Integer.toBinaryString(year);
		else if(degree==8)systemdate+=""+Integer.toOctalString(year);
		else if(degree==16)
			{
			systemdate+="0"+Integer.toHexString(year);
			}
		else systemdate+=""+year;


		return systemdate;
	}//end getSystemYear
//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Month 01~12
 */
	public String getSystemMonth(int degree)
	{
		//if(degree!=2&&degree!=8&&degree!=10&&degree!=16)
		//	return "Your degree"+degree+"��ѡ����ʵĽ���: 2; 8; 10; 16";
		String systemdate="";
		month=todaysDate.get(Calendar.MONTH)+1;//old:0~11
		if (month<degree)	//Now:1~12
		  {
			if(degree==2)systemdate+=""+Integer.toBinaryString(month);
			else if(degree==8)systemdate+=""+Integer.toOctalString(month);
			else if(degree==16)systemdate+="0"+Integer.toHexString(month);
			else systemdate+="0"+month;
		  }
		else
			systemdate+=""+month;

		return systemdate;
	}//end getSystemMonth

//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Day 01~31
 */
	public String getSystemDay(int degree)
	{
		String systemdate="";
		day=todaysDate.get(Calendar.DAY_OF_MONTH);
		if (day<degree)
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(day);
			else if(degree==8)systemdate+=""+Integer.toOctalString(day);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(day);
				}
			else systemdate+="0"+day;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(day);
			else if(degree==8)systemdate+=""+Integer.toOctalString(day);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(day);
				}
			else systemdate+=""+day;
		}

		return systemdate;
	}//end getSystemDay

//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Hour 00~23 ��ʮ��Сʱ��
 */
	public String getSystemHour(int degree)
	{
		String systemdate="";

		hour_of_day=todaysDate.get(Calendar.HOUR_OF_DAY);//0~23 ��ʮ��Сʱ��
		//PM_Flag=todaysDate.get(Calendar.AM_PM);
		//if (PM_Flag==1) hour+=12; //ʮ��Сʱ��
		if (hour_of_day<degree)
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(hour_of_day);
			else if(degree==8)systemdate+=""+Integer.toOctalString(hour_of_day);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(hour_of_day);
				}
			else systemdate+="0"+hour_of_day;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(hour_of_day);
			else if(degree==8)systemdate+=""+Integer.toOctalString(hour_of_day);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(hour_of_day);
				}
			else systemdate+=""+hour_of_day;
		}

		return systemdate;
	}//end getSystemHour

//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Minute 00~59
 */
	public String getSystemMinute(int degree)
	{
		String systemdate="";
		minute=todaysDate.get(Calendar.MINUTE);//0~59
		if (minute<degree)
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(minute);
			else if(degree==8)systemdate+=""+Integer.toOctalString(minute);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(minute);
				}
			else systemdate+="0"+minute;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(minute);
			else if(degree==8)systemdate+=""+Integer.toOctalString(minute);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(minute);
				}
			else systemdate+=""+minute;
		}
		return systemdate;
	}//end getSystemMinute
//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Second 00~59
 */
	public String getSystemSecond(int degree)
	{
		String systemdate="";
		second=todaysDate.get(Calendar.SECOND);//0~59
		if (second<degree)
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(second);
			else if(degree==8)systemdate+=""+Integer.toOctalString(second);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(second);
				}
			else systemdate+="0"+second;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(second);
			else if(degree==8)systemdate+=""+Integer.toOctalString(second);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(second);
				}
			else systemdate+=""+second;
		}

		return systemdate;
	}//end getSystemSecond
//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return MilliSecond 000~999
 */
	public String getSystemMilliSecond(int degree)
	{
		String systemdate="";
		millisecond=todaysDate.get(Calendar.MILLISECOND);//0~999
		if (millisecond<degree)
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(millisecond);
			else if(degree==8)systemdate+=""+Integer.toOctalString(millisecond);
			else if(degree==16)
				{
				systemdate+="00"+Integer.toHexString(millisecond);
				}
			else systemdate+="00"+millisecond;
		}
		else if((millisecond>=degree)&&(millisecond<(degree*degree)))
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(millisecond);
			else if(degree==8)systemdate+=""+Integer.toOctalString(millisecond);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(millisecond);
				}
			else systemdate+="0"+millisecond;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(millisecond);
			else if(degree==8)systemdate+=""+Integer.toOctalString(millisecond);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(millisecond);
				}
			else systemdate+=""+millisecond;
		}

		return systemdate;
	}//end getSystemMilliSecond
//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Day_of_Year 001~366
 */
	public String getSystemDay_of_Year(int degree)
	{
		String systemdate="";
		day_of_year=todaysDate.get(Calendar.DAY_OF_YEAR);//001~366
		if (day_of_year<degree)//001~366
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(day_of_year);
			else if(degree==8)systemdate+=""+Integer.toOctalString(day_of_year);
			else if(degree==16)
				{
				systemdate+="00"+Integer.toHexString(day_of_year);
				}
			else systemdate+="00"+day_of_year;
		}
		else if (day_of_year>=degree&&day_of_year<(degree*degree))
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(day_of_year);
			else if(degree==8)systemdate+=""+Integer.toOctalString(day_of_year);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(day_of_year);
				}
			else systemdate+="0"+day_of_year;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(day_of_year);
			else if(degree==8)systemdate+=""+Integer.toOctalString(day_of_year);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(day_of_year);
				}
			else systemdate+=day_of_year;
		}

		return systemdate;
	}//end getSystemDay_of_Year

//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Week_of_Year 01~54
 */
	public String getSystemWeek_of_Year(int degree)
	{
		String systemdate="";
		week_of_year=todaysDate.get(Calendar.WEEK_OF_YEAR);//1~54
		if (week_of_year<degree)//1~54
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(week_of_year);
			else if(degree==8)systemdate+=""+Integer.toOctalString(week_of_year);
			else if(degree==16)
				{
				systemdate+="0"+Integer.toHexString(week_of_year);
				}
			else systemdate+="0"+week_of_year;
		}
		else
		{	if(degree==2)systemdate+=""+Integer.toBinaryString(week_of_year);
			else if(degree==8)systemdate+=""+Integer.toOctalString(week_of_year);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(week_of_year);
				}
			else systemdate+=""+week_of_year;
		}
		return systemdate;
	}//end getSystemWeek_of_Year
//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Week_of_Month 1~6
 */
	public String getSystemWeek_Of_Month(int degree)
	{
		String systemdate="";
		week_of_month=todaysDate.get(Calendar.WEEK_OF_MONTH);//1~6

			if(degree==2)systemdate+=""+Integer.toBinaryString(week_of_month);
			else if(degree==8)systemdate+=""+Integer.toOctalString(week_of_month);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(week_of_month);
				}
			else systemdate+=""+week_of_month;

		return systemdate;
	}//end getSystemWeek_Of_Month
//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return Day_of_Week 1~7
 */
	public String getSystemDay_of_Week(int degree)
	{
		String systemdate="";
		day_of_week=todaysDate.get(Calendar.DAY_OF_WEEK)-1;//old:0~6;
		if(day_of_week==0) day_of_week=7;//now 1-mon;6-sta;7-sun

			if(degree==2)systemdate+=""+Integer.toBinaryString(day_of_week);
			else if(degree==8)systemdate+=""+Integer.toOctalString(day_of_week);
			else if(degree==16)
				{
				systemdate+=""+Integer.toHexString(day_of_week);
				}
			else
		systemdate+=""+day_of_week;

		return systemdate;
	}//end getSystemDay_of_Week

//**********************************************************************//
/**
 * ��ݽ�λ�ƵĲ�ͬ�ѷ���ֵת������Ӧ��λ�Ƶ�ֵ;
 * @param degree ��λ��:10--ʮ����;8--�˽���;2--������;16-ʮ�����
 * @return SystemDateID ϵͳ���ڼ�ʱ���ID,�������Ψһ��
 */
	public String getSystemDateID(int degree)
	{
		String systemdate="";
		systemdate=getSystemYear(degree)+getSystemMonth(degree)+getSystemDay(degree) //if degree=16 8 digit
			+getSystemHour(degree)+getSystemMinute(degree)+getSystemSecond(degree)+getSystemMilliSecond(degree)//if degree=16 9 digit
			//+getSystemWeek_Of_Month(degree)+getSystemDay_of_Year(degree)//if degree=16 4 digit
			+getSystemWeek_of_Year(degree)+getSystemDay_of_Week(degree); //if degree=16 3 digit
		systemdate=systemdate.trim();
		return systemdate; //20 digit
	}//end getSystemDateID
//**********************************************************************//
/**
 *  @return SystemDateMS ϵͳ����ʱ������---"������ʱ�������",��:20020423153803123
*/
		public String getSystemTimeWithMS()
			{
				String systemdate="";
				int degree=10;
				systemdate=getSystemYear(degree)+getSystemMonth(degree)+getSystemDay(degree) //if degree=16 8 digit
						+getSystemHour(degree)+getSystemMinute(degree)+getSystemSecond(degree)+getSystemMilliSecond(degree);//if degree=16 9 digit
				return systemdate;
			}//end getSystemTimeWithMS
//**********************************************************************//
/**
 *  @return SystemDate ����ռλ��ţ���2002-1-21��������>2002-02-21
*/
	public String getSystemDate()
		{
		String systemdate="";
		int degree=10;
		systemdate=getSystemYear(degree)+"-"+getSystemMonth(degree)+"-"+getSystemDay(degree); //10 digit
		//systemdate=systemdate.trim();
		return systemdate; //10 digit
		}//end getSystemDate
//**********************************************************************//

//**********************************************************************//
/**
 *  @return java.sql.Date SystemDate
*/
		public java.sql.Date getSystemSQLDate()
			{
					java.util.Date util_date=new java.util.Date();
					long timevalue=util_date.getTime();
					java.sql.Date systemdate=new java.sql.Date(timevalue);
					return systemdate;
			}//end getSystemSQLDate()

//
	public GregorianCalendar getSystemCalendar()
	{
		return todaysDate;
	}//end getSystemCalendar

//**********************************************************************//
/**
 *  @return  SystemTime ����ռλ��ţ���7:23:1��������>07:23:01
*/
	public String getSystemTime()
	{
		String systemtime="";
		int degree=10;
		systemtime=getSystemHour(degree)+":"+getSystemMinute(degree)+":"+getSystemSecond(degree); //8 digit
		//systemdate=systemdate.trim();
		return systemtime; //8 digit
	}//end getSystemTime
//**********************************************************************//
/**
 *  @return  SystemDateTime ����ռλ��ţ���2002-1-21 7:23:1��������>2002-02-21 07:23:01
*/
	public String getSystemDateTime()
	{
		String systemdatetime="";
		systemdatetime=getSystemDate()+" "+getSystemTime();
		return systemdatetime; //19 digit
	}//end getSystemDateTime

//**********************************************************************//
	/**
	  A string representation of the day

	  @return a string representation of the day
	*/

	public String DatetoString()
	   {  return "" + year + "-" + month + "-" + day + "";
	   }

	public String TimetoString()
	   {  return "" + hour_of_day + ":" + minute + ":" + second + "";
	   }
//************************************************************************//
/** adjust �������ڼ�ʱ��
 *  @param  nyear ��
 *  @param  nmonth ��
 *  @param  nday ��
 *  @param  nhour Сʱ(24Сʱ��)
 *  @param  nminute ��
 *  @param  nsecond ��
 *  @param  nmillisecond ����
 *  @return  ���������ڼ�ʱ��
*/
	public String adjust(int nyear,int nmonth,int nday,int nhour,int nminute,int nsecond,int nmillisecond)
	{
				todaysDate.add(Calendar.YEAR, nyear);
		todaysDate.add(Calendar.MONTH, nmonth);
		todaysDate.add(Calendar.DATE, nday);
		todaysDate.add(Calendar.HOUR_OF_DAY, nhour);
		todaysDate.add(Calendar.MINUTE, nminute);
		todaysDate.add(Calendar.SECOND, nsecond);
		todaysDate.add(Calendar.MILLISECOND, nmillisecond);
		return getSystemDateTime();
	}//end adjust
//*************************************************************************//
/** adjustDate ��������
 *  @param  nyear ��
 *  @param  nmonth ��
 *  @param  nday ��
 *  @return  ����������
*/
	public String adjustDate(int nyear,int nmonth,int nday)
	{
		todaysDate.add(Calendar.YEAR, nyear);
		todaysDate.add(Calendar.MONTH, nmonth);
		todaysDate.add(Calendar.DATE, nday);
		return getSystemDate();
	}//end adjustDate

//************************************************************************//
/** adjustTime ����ʱ��
 *  @param  nhour Сʱ(24Сʱ��)
 *  @param  nminute ��
 *  @param  nsecond ��
 *  @param  nmillisecond ����
 *  @return ������ʱ��
*/
	public String adjustTime(int nhour,int nminute,int nsecond,int nmillisecond)
	{
		todaysDate.add(Calendar.HOUR_OF_DAY, nhour);
		todaysDate.add(Calendar.MINUTE, nminute);
		todaysDate.add(Calendar.SECOND, nsecond);
		todaysDate.add(Calendar.MILLISECOND, nmillisecond);
		return getSystemTime();
	}//end adjustTime
//**********************************************************************//
/**
 * ��������
 * @param  nyear ��
 * @param  nmonth ��
 * @param  nday ��
 * @return ���ú������
 */
	public String setDate(int nyear,int nmonth,int nday)
	{
		todaysDate=new GregorianCalendar(nyear,nmonth,nday);
		return getSystemDate();
	}//end setDate
//**********************************************************************//
/**
 * ����ʱ��
 * @param  nhour Сʱ(24Сʱ��)
 * @param  nminute ��
 * @param  nsecond ��
 * @return ���ú��ʱ��
 */
	public String setTime(int nhour,int nminute,int nsecond)
	{
		todaysDate=new GregorianCalendar(nhour,nminute,nsecond);
		return getSystemTime();
	}//end setTime
//**********************************************************************//
/**
 * �������ڼ�ʱ��
 * @param  nyear ��
 * @param  nmonth ��
 * @param  nday ��
 * @param  nhour Сʱ(24Сʱ��)
 * @param  nminute ��
 * @param  nsecond ��
 * @return �������ڼ�ʱ��
 */
	public String set(int nyear,int nmonth,int nday,int nhour,int nminute,int nsecond)
	{
		todaysDate=new GregorianCalendar(nyear,nmonth,nday,nhour,nminute,nsecond);
		return getSystemDateTime();
	}//end set
//**********************************************************************//

//*****************************************************************
//**********************************************************************//
/**
 * Description:�������ĸ�ʽ��������Ϣ���磺������
 * @version 1.0.2002.0921
 * @author �ű�
 */
	public String getSystemWeekDay_CN()
	{
		String strReturnWeekDay="",strWeekDay="";

		strWeekDay=getSystemDay_of_Week(10);
		strWeekDay=convertWeekNumberToCNString(strWeekDay);
		strReturnWeekDay="����"+strWeekDay;
		return strReturnWeekDay;
	}//end getSystemWeekDay_CN

//*****************************************************************
//**********************************************************************//
/**
 * Description:�������ĸ�ʽ��������Ϣ���磺2002��9��21��
 * @param strDateFormat ���ڸ�ʽ
 * @version 1.0.2002.0921
 * @author �ű�
 */
	public String getSystemDate(String strDateFormat)
	{
		String strReturnSystemDate="";
		String strYear="",strMonth="",strDay="";
		int degree=10;
		
		strYear=getSystemYear(degree);
		strMonth=getSystemMonth(degree);
		strDay=getSystemDay(degree);

		strDateFormat=strDateFormat.trim();
		if (strDateFormat.equalsIgnoreCase("yyyy��mm��dd��"))
		{
			strReturnSystemDate=strYear+"��"+strMonth+"��"+strDay+"��";
		}else if (strDateFormat.equalsIgnoreCase("yyyy��m��d��"))
		{
			int intMonth=Integer.parseInt(strMonth);
			int intDay=Integer.parseInt(strDay);
			strReturnSystemDate=strYear+"��"+String.valueOf(intMonth)+"��"+String.valueOf(intDay)+"��";
		}else if (strDateFormat.equalsIgnoreCase("yyyy-mm-dd"))
		{
			strReturnSystemDate=getSystemDate();
		}else{
			strReturnSystemDate=getSystemDate();
		}
		
		return strReturnSystemDate;
	}//end getSystemDate

//*****************************************************************
//*****************************************************************
/**
 * Description:�������ַ��е�����ת���ɺ���
 * @param strNumber
 * @return strCNString
 * @version 1.0.2002.0921
 * @author �ű�
 */
public String convertWeekNumberToCNString(String strNumber)
	{
		String strCNString="";
		strNumber=strNumber.trim();
		if(strNumber.equalsIgnoreCase("1")) strCNString="һ";
		if(strNumber.equalsIgnoreCase("2")) strCNString="��";
		if(strNumber.equalsIgnoreCase("3")) strCNString="��";
		if(strNumber.equalsIgnoreCase("4")) strCNString="��";
		if(strNumber.equalsIgnoreCase("5")) strCNString="��";
		if(strNumber.equalsIgnoreCase("6")) strCNString="��";
		if(strNumber.equalsIgnoreCase("7")) strCNString="��";
		return strCNString;
	}//end convertWeekNumberToCNString

//**********************************************************************//
/**
 * ȡ��ǰ��ݵ�ǰ}λ����2003->20
 * gdx add at 2003.06.23
 */
	public String getLeft2DigitsOfSystemYear()
	{
		String strLeft2DigitsOfSystemYear="";
		strLeft2DigitsOfSystemYear=getSystemYear(10);
		strLeft2DigitsOfSystemYear=strLeft2DigitsOfSystemYear.substring(0,2);
		return strLeft2DigitsOfSystemYear;
	}
	
	
	//��4���Ե�main����,gdx add at 2003.06.23
	public static void main(String[] args){
		//SystemDate sDate=new SystemDate();
		//log.debug(sDate.getLeft2DigitsOfSystemYear());
	}		

}// end Class