package com.scushare.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ��ȡ�ͱȽ����ڵĹ�����
 *
 */
public class DateUtil {
	/**
	 * 
	 * @return ���ص�ǰʱ�䣨yyyy-MM-dd HH:mm:ss��
	 */
	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		String nowDate = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		return nowDate;
	}
	
	/**
	 * ��ȡ����ʱ�����ķ�����
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int minutesBetweenDate(String startTime, String endTime) {
	     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	     Calendar cal = Calendar.getInstance();  
	     long time1 = 0;
	     long time2 = 0;
	      
	     try{
	        cal.setTime(sdf.parse(startTime));  
	        time1 = cal.getTimeInMillis();  
	        cal.setTime(sdf.parse(endTime)); 
	        time2 = cal.getTimeInMillis(); 
	     }catch(Exception e){
	       e.printStackTrace();
	     }
	     long between_days=(time2-time1)/(1000*60); 
	        
	     return Integer.parseInt(String.valueOf(between_days));   
	}
	
	/**
	 * ��ȡ��ǰʱ���Timestamp���ͣ��������ݿ�ʱ�������ʱ���붪ʧ
	 * @return ����Timestamp����ʱ��
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(new Date().getTime() + 8 * 3600 * 1000);
	}
	
	/**
	 * ��date����תΪString���ͣ�yyyy-MM-dd��
	 * @param date
	 * @return
	 */
	public static String getStringByDate(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}