package com.run.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 将时间格式化字符串转为毫秒值
     *
     * @param fromat（String）
     * @param dateStr（String）
     * @return
     */
    public static Long dateFormatTurnTime(String fromat, String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(fromat);
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 将时间格式化字符串转为毫秒值（常用格式）
     *
     * @param dateStr（String）
     * @return
     */
    public static Long acquiescenceDateFormatTurnTime(String dateStr) {
        return dateFormatTurnTime("yyyy-MM-dd HH:mm:ss", dateStr);
    }


    /**
     * 将日期格式为指定字符串格式并返回
     *
     * @param fromat
     * @param time
     * @return
     */
    public static String dateFormatTurnString(String fromat, Object time) {
        SimpleDateFormat formatter = new SimpleDateFormat(fromat);
        return formatter.format(time);
    }

    /**
     * 将日期格式为指定字符串格式并返回（常用的格式）
     *
     * @param time
     * @return
     */
    public static String acquiescenceDateFormatTurnString(Object time) {
        return dateFormatTurnString("yyyy-MM-dd HH:mm:ss", time);
    }
}
