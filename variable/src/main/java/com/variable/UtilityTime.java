package com.variable;

import android.app.Activity;
import android.content.Context;

import com.log.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by star on 2017/11/1.
 */
public class UtilityTime {
    private static final String TAG = UtilityTime.class.getSimpleName();
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String DATE_AND_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final String TIME_TODAY = "yyyy/MM/dd HH:mm:ss";
    private static final long DATE_TIME = 1000L;

    private volatile static UtilityTime u;
    public UtilityTime(){}

    public static UtilityTime getNewInstance() {
        if(u == null) {
            synchronized (UtilityTime.class) {
                if(u == null) {
                    u = new UtilityTime();
                }
            }
        }
        return u;
    }
    
    /**
     * 轉換long to date (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param timestamp
     * @return
     */
    public String convertLongToDate(Activity activity, String timestamp){
        return convertLongToDate(activity, null, timestamp);
    }

    /**
     * 轉換long to date (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param newFormat 帶null會跑預設format
     * @param timestamp
     * @return
     */
    public String convertLongToDate(Activity activity, String newFormat, String timestamp){
        if(timestamp==null){
            return "";
        }
        long dateTime = 0;
        try {
            dateTime = Long.valueOf(timestamp.contains(".")?timestamp.split("\\.")[0]:timestamp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertLongToDate(activity, newFormat, dateTime);
    }

    /**
     * 轉換long to date (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param timestamp
     * @return
     */
    public String convertLongToDate(Activity activity, long timestamp){
        return convertLongToDate(activity, null, timestamp);
    }

    /**
     * 轉換long to date (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param newFormat =null: 預設format, !=null: 會強制跑指定格式
     * @param timestamp
     * @return
     */
    public String convertLongToDate(Activity activity, String newFormat, long timestamp){
        //本地時區
        Calendar nowCal = Calendar.getInstance();
        TimeZone localZone = nowCal.getTimeZone();
        boolean isToday = false;
        if(newFormat==null){
            String dateFormat = DATE_FORMAT;
            if(new SimpleDateFormat(dateFormat).format(new Date().getTime())
                    .equals(new SimpleDateFormat(dateFormat).format(timestamp*DATE_TIME))){
                isToday = true;
            }
        }
        String defaultFormat = DATE_AND_TIME_FORMAT;
        if(isToday){
            defaultFormat = TIME_TODAY;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(null==newFormat?defaultFormat:newFormat);
        //設定SDF的時區為本地
        sdf.setTimeZone(localZone);
        Logger.d(TAG+"=time zone name="+sdf.getTimeZone().getDisplayName()+"=time zone id="+sdf.getTimeZone().getID());
        return (isToday?TIME_TODAY+" ":"") + sdf.format(new Date(timestamp*DATE_TIME));
    }

    /**
     * 轉換 date to long (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param date
     * @return
     */
    public String convertDateToTimestamp(Activity activity, String date){
        return convertDateToTimestamp(activity, null, date);
    }

    /**
     * 轉換 date to long (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param newFormat 帶null會跑預設format
     * @param date
     * @return
     */
    public String convertDateToTimestamp(Context activity, String newFormat, String date){
        DateFormat formatter = new SimpleDateFormat(null==newFormat?DATE_AND_TIME_FORMAT:newFormat);
        Date finalDate = null;
        try {
            finalDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(finalDate==null){
            return "";
        }else{
            return Long.toString(finalDate.getTime()/DATE_TIME);
        }
    }

    /**
     * 轉換 date to long (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param date
     * @return
     */
    public long convertDateToLongTimestamp(Activity activity, String date){
        return convertDateToLongTimestamp(activity, null, date);
    }

    /**
     * 轉換 date to long (DEFAULT: yyyy/MM/dd HH:mm:ss)
     * @param activity
     * @param newFormat 帶null會跑預設format
     * @param date
     * @return
     */
    public long convertDateToLongTimestamp(Activity activity, String newFormat, String date){
        DateFormat formatter = new SimpleDateFormat(null==newFormat?DATE_AND_TIME_FORMAT:newFormat);
        Date finalDate = null;
        try {
            finalDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(finalDate==null){
            return 0;
        }else{
            return finalDate.getTime()/DATE_TIME;
        }
    }

    /**
     * 取得UTC標準時間
     * @return
     */
    public String getTimeStamp() {
        Long tsLong = System.currentTimeMillis()/DATE_TIME;
        String ts = tsLong.toString();
        Logger.d("getTimeStamp="+ts);
        return ts;
    }

    /**
     * 取得今天日期
     * 預設Format: yyyy/MM/dd
     * @param context
     * @return
     */
    public String getCurrentDate(Context context){
        return getCurrentDate(context, null);
    }

    /**
     * 取得今天日期的timestamp
     * @param context
     * @param newFormat
     * @return
     */
    public String getCurrentDate(Context context, String newFormat){
        Calendar calendar = Calendar.getInstance();
        String convertFormat = null==newFormat?DATE_FORMAT:newFormat;
        DateFormat formatter = new SimpleDateFormat(convertFormat);
        String formattedDate = formatter.format(calendar.getTime());
        return convertDateToTimestamp(context, convertFormat, formattedDate);
    }

    /**
     * 檢查是否超過24小時=1440分鐘
     * @param timestamp 檢查的時間
     * @return
     */
    private boolean checkOver24H(long timestamp) {
        long cutime = System.currentTimeMillis();
        long qutime = timestamp;
        long dftime = (cutime - qutime)/DATE_TIME/60 ;
        Logger.d("dftime=" + dftime);
        boolean result = false;
        if(dftime>=1440){
            result = true;
        }
        return result;
    }

    /**
     * 檢查是否超過H小時
     * @param overHour 超過的小時
     * @param timestamp 檢查的時間
     * @return
     */
    private boolean checkOverHour(int overHour, long timestamp) {
        long cutime = System.currentTimeMillis();
        long qutime = timestamp;
        long dftime = (cutime - qutime)/DATE_TIME/60 ;
        Logger.d("dftime=" + dftime);
        boolean result = false;
        if(dftime>=overHour*60){
            result = true;
        }
        return result;
    }
}
