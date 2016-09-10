/**
 * @Project Name:WeChat
 * @File Name:DateUtil.java
 * @Package Name:com.wuxianedu.util
 * @Date:2016年7月8日下午8:49:25
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @ClassName:DateUtil 
 * @Function: TODO ADD FUNCTION. 
 * @Date:     2016年7月8日 下午8:49:25 
 * @author   yifeng.Zhang
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class DateUtil {

	public static final SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat df4 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    public static final SimpleDateFormat df5 = new SimpleDateFormat("Gyyyy年MM月dd日");
    public static final SimpleDateFormat df6 = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat df7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat SDF = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat df8 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    //2016-01-07T00:07:18+08:00
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static final DateFormat DF = new SimpleDateFormat("MM月dd日");

	public static String dateToString(DateFormat df, Date date) {
		return df.format(date);
	}
	
	/**
	 * 将长整形时间转换为字符串日期
	 * @param longDate
	 * @param str
	 * @return
	 */
	public static String parseLongToString(long longDate, DateFormat str) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(longDate);
		return str.format(calendar.getTime());
	}
	
    /**
     * 将Date转为字符串日期
     * @param df
     * @param date
     * @return
     */
    public static String getStringDate(DateFormat df,Date date){
        return df.format(date);
    }

    /**
	 * 解析 字符串为日期
	 * @param format
	 * @param time
	 * @return
	 */
	public static Date parseStringDate(DateFormat format,String time) {
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
    /**
     * 将字符串日期转为Date
     * @param df
     * @param date
     * @return
     */
    public static Date getDate(DateFormat df,String date){
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取系统的当前时间
     * @return  long型系统时间
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 转换日期
     * @param timesamp
     * @return
     */
    public static String getDay(Date timesamp) {
        if(timesamp == null){
            return "未";
        }
        String result = "未";
        SimpleDateFormat sdf = new SimpleDateFormat("DD");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = timesamp;
//        int temp = Integer.parseInt(sdf.format(today))
//                - Integer.parseInt(sdf.format(otherDay));
        int temp = (int) ((today.getTime() - timesamp.getTime())/1000/60/60/24);
        int month = temp/30;
        int year = temp/365;
        if (temp > 30 && temp < 365){
            result = month+"月前" + getStringDate(SDF, timesamp);
        }else if (temp > 365){
            result = year+"年前" + getStringDate(SDF, timesamp);
        }else{
            switch (temp) {
                case 0:
                    result = "今天 " + getStringDate(SDF, timesamp);
                    break;
                case 1:
                    result = "昨天 "+ getStringDate(SDF, timesamp);
                    break;
                case 2:
                    result = "前天 "+ getStringDate(SDF, timesamp);
                    break;
                default:
                    result = temp + "天前 "+ getStringDate(SDF, timesamp);
                    break;
            }
        }
        return result;
    }

}

