package com.asurplus.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期时间处理工具类
 *
 * @author xlp
 *
 **/
public class DateUtils {

    /**
     * 获取当前时间，yyyy-MM-dd HH:mm:ss
     */
    public static String getYmdHms() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 时间格式化，yyyy-MM-dd HH:mm:ss
     */
    public static String getYmdHms(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 时间格式化，yyyy-MM-dd HH:mm:ss
     */
    public static Date getYmdHms(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间格式化，yyyy年MM月dd日 HH时mm分ss秒
     */
    public static String getYmdHmsZh() {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return df.format(new Date());
    }

    /**
     * 时间格式化，yyyyMMdd
     */
    public static String getYyyymmddhhmmss() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    /**
     * 时间格式化，yyyyMMdd
     */
    public static String getYyyymmddhhmmss(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(date);
    }

    /**
     * 时间格式化，yyyyMMdd
     */
    public static String getYyyymmdd() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }

    /**
     * 时间格式化，yyyyMMdd
     */
    public static String getYyyymmdd(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }

    /**
     * 获取当前日期，yyyy-MM-dd
     */
    public static String getYmd() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取当前日期，yyyy-MM-dd
     */
    public static String getYmd(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    /**
     * 日期格式化，yyyy-MM-dd
     */
    public static Date getYmd(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当月日期，yyyy-MM
     */
    public static String getYm() {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(new Date());
    }

    /**
     * 获取当月日期，yyyy-MM
     */
    public static String getYm(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(date);
    }

    /**
     * 获取当年日期，yyyy
     */
    public static String getY() {
        DateFormat df = new SimpleDateFormat("yyyy");
        return df.format(new Date());
    }

    /**
     * 获取当年日期，yyyy
     */
    public static String getY(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy");
        return df.format(date);
    }

    /**
     * 获取本月的天数
     */
    public static int getThisMonthDayNum() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        return aCalendar.getActualMaximum(Calendar.DATE);

    }

    /**
     * 获取本月第一天，yyyy-MM-dd
     */
    public static String getThisMonthFirstDayYmd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取本月最后一天，yyyy-MM-dd
     */
    public static String getThisMonthLastDayYmd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.set(Calendar.DAY_OF_MONTH, calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar2.getTime());
    }

    /**
     * 获取某个月的第一天，yyyy-MM-dd
     */
    public static String getMonthFirstDayYmd(String month) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf1.parse(month));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某个月的最后一天，yyyy-MM-dd
     */
    public static String getMonthLastDayYmd(String month) {
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf1.parse(month));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取该日期往后推多少天的日期，yyyy-MM-dd
     * <p>
     * days > 0 ,往后，2020-10-12 --> 2020-10-14
     * days < 0, 往前，2020-10-12 --> 2020-10-10
     */
    public static String getDayBeforeDayYmd(Date date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取该日期往后推多少天的日期，yyyy-MM-dd
     * <p>
     * days > 0 ,往后，2020-10-12 --> 2020-10-14
     * days < 0, 往前，2020-10-12 --> 2020-10-10
     */
    public static String getDayBeforeDayYmd(String date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(sdf.parse(date));
            // 把日期往后增加一天.整数往后推,负数往前移动
            calendar.add(calendar.DATE, days);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本周周一，yyyy-MM-dd
     */
    public static String getThisWeekMonday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        return imptimeBegin;
    }

    /**
     * 获取本周周日，yyyy-MM-dd
     */
    public static String getThisWeekSundayYmd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.add(Calendar.DATE, 6);
        return sdf.format(cal.getTime());
    }

    /**
     * 补全时间，开始时间
     */
    public static String completionTimeStart(String date) {
        return date + " 00:00:00";
    }

    /**
     * 补全时间，结束时间
     */
    public static String completionTimeEnd(String date) {
        return date + " 23:59:59";
    }

    /**
     * 获取两个时间的时间差
     */
    public static String getDateTimeDiffer(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = Math.abs(endDate.getTime() - nowDate.getTime());
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        return day + "天" + hour + "时" + min + "分";
    }

    /**
     * 根据生日算年龄
     */
    private static int getAgeByBirthDay(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);
            // 如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

    /**
     * 获取n年后今天的日期 yyyy-MM-dd
     */
    public static String getAfterYearDateYmd(int num) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        // 设置起时间
        cal.setTime(date);
        // 增加一年
        cal.add(Calendar.YEAR, num);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    /**
     * 获取下个月的今天 y-m-d
     */
    public static String getNextMonthDayYmd(String date, int i) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date resDate = getYmd(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resDate);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);
        resDate = calendar.getTime();
        return dateFormat.format(resDate);
    }

    /**
     * 获取上个月的今天 y-m-d
     */
    public static String getLastMonthDayYmd(String date, int i) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date resDate = getYmd(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(resDate);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - i);
        resDate = calendar.getTime();
        return dateFormat.format(resDate);
    }

    /**
     * 获取月份的上个月
     */
    public static String getLastMonth(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(month);
            calendar.setTime(date);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            date = calendar.getTime();
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得两个时间差多少小时
     */
    public static long getDateDiffHour(Date startDate, Date nowDate) {
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate.getTime() - startDate.getTime();
        // 相差多少小时
        return diff / nh;
    }

    public static void main(String[] args) {
        System.out.println(getYmdHmsZh());
    }
}
