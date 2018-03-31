package com.books.model;

import java.sql.*;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date; 

public class Tools {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//数据库相关
	public static String IP = "localhost:3307";
	public static String DATABASE = "bookmanagersystem";
	public static String URL = "jdbc:mysql://" + IP + "/" + DATABASE + "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true";
	public static String DBUSERNAME = "root";
	public static String DBPASSWORD = "123456";
	
	//数据库连接
	public static Connection getConnection() {
		Connection ct = null;
		try {
        	Class.forName("com.mysql.jdbc.Driver");
        	ct = DriverManager.getConnection(URL, DBUSERNAME, DBPASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}
	
	//关闭数据库
	public static void CloseDB(Connection ct, PreparedStatement ps, ResultSet rs) 
			throws SQLException{

        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
		if (ct != null) {
            ct.close();
        }
	}
	
	//关闭数据库
	public static void CloseDB(Connection ct, Statement st, ResultSet rs) 
			throws SQLException{

        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
		if (ct != null) {
            ct.close();
        }
	}
	
	//日期相关
	/** 
     * Java Calender类获得指定日期加几天 
     *  
     * @param specifiedDay 
     * @param d  day 
     * @return 
     */  
    public static String getSpecifiedDayAfter(String specified,int d) {  
        Calendar c = Calendar.getInstance();  
        Date date = null;  
        try {  
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specified);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        c.setTime(date);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day + d);  
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
        return dayAfter;  
    } 
    
    /** 
     * 比较两个日期的大小 
     * @param DATE1 
     * @param DATE2 
     * @return 
     */  
     public static int compare_date(String DATE1, String DATE2) {  
        int a = 0;  
        try {  
            Date dt1 = sdf.parse(DATE1);  
            Date dt2 = sdf.parse(DATE2);  
            if (dt1.getTime() > dt2.getTime()) {  
                //System.out.println("dt1在dt2后");  
                a = 1;  
            } else if (dt1.getTime() < dt2.getTime()) {  
                //System.out.println("dt1在dt2前");  
                a = 2;  
            }else{  
                //System.out.println("两个时间相等");  
                a = 0;  
            }  
        } catch (Exception exception) {  
            exception.printStackTrace();  
        }  
        return a;  
     }
     
     /**
      * 返回当前系统日期
      * @return String类型字符串
      */
     public static String getDateNow(){
    	 String dateNow = "";
    	 Calendar now = Calendar.getInstance();
    	 int year = now.get(Calendar.YEAR);
    	 int month = now.get(Calendar.MONTH) + 1;
    	 int day = now.get(Calendar.DAY_OF_MONTH);
    	 dateNow = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
    	 return dateNow;
     }
     
     //变量转码
	 public static String getCovertCode(String code) {
		 String result="";
		 try {
			 result=new String(code.getBytes("ISO-8859-1"),"utf-8");
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return result;
	 }
}
