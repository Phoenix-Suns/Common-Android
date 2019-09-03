package com.nghiatl.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nghia-PC on 8/3/2015.
 */
public class TimeUtil {

    public static final String DATE_TIME_FORMAT_COMMON = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_SYNC_CODE = "yyMMddHHmmssSSS";

    /** lấy ngày giờ hiện tại **/
    public static Calendar getNow(){
        return Calendar.getInstance();
    }

    /**
     * Đặt lại giá trị dựa trên Calendar cũ
     * @param calendar
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static Calendar setValue(Calendar calendar, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar;
    }

    /**
     * Đặt lại giá trị dựa trên Calendar cũ
     */
    public static Calendar setValue(Calendar calendar, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    /**
     * Convert a millisecond duration to a string format
     *
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static HashMap calculateTime(long millis) {
        HashMap result = new HashMap();

        if(millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);


        result.put(Calendar.DAY_OF_YEAR, (int) days);
        result.put(Calendar.HOUR_OF_DAY, (int) hours);
        result.put(Calendar.MINUTE, (int) minutes);
        result.put(Calendar.SECOND, (int) seconds);

        return result;
    }

    /**
     * get current datetime
     * */
    public static String getNowDateTime(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * Chuyển Millisecond thành Giờ:Phút:giây
     * @param millis
     * @return
     */
    public static String toHourMinuteSecond(long millis) {
        /*int h = (int) (millis / 3600000);
        int m = (int) (millis - h * 3600000) / 60000;
        int s = (int) ((millis - (long)(h * 3600000) - (long)(m * 60000))) / 1000;*/

        long seconds = millis / 1000 % 60;
        long minute = (millis / (1000 * 60)) % 60;   // Max 60 minute Per Hour
        long hour = (millis / (1000 * 60 * 60));
        // long hour = (millis / (1000 * 60 * 60)) % 24; // max 24 Hour Per Day
        return String.format("%02d:%02d:%02d", hour, minute, seconds);
    }

    public static long minusTime(Calendar fromTime, Calendar toTime) {
        return Math.abs(fromTime.getTimeInMillis() - toTime.getTimeInMillis());
    }
}
