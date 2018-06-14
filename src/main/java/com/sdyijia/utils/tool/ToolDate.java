package com.sdyijia.utils.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToolDate {

    public final static String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_YEAR = "yyyy";
    public final static String FORMAT_MONTH = "yyyy-MM";
    public final static String FORMAT_DAY = "yyyy-MM-dd";

    private static Calendar cal = Calendar.getInstance();

    /**
     * 传入 date 然后得使用 Calendar 得到日期的月份
     * 
     * @return
     */
    public static int subMonth(Date date, String format) {
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 传入 date 然后得使用 Calendar 得到日期的月份
     * 
     * @return
     */
    public static int subMonth(String date, String format) {
        return subMonth(parseTime(date, format), format);
    }

    /**
     * 传入 date 然后得使用 Calendar 得到日期的年份
     * 
     * @return
     */
    public static int subYear(Date date, String format) {
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 传入 date 然后得使用 Calendar 得到日期的月份
     * 
     * @return
     */
    public static int subYear(String date, String format) {
        return subYear(parseTime(date, format), format);
    }

    /**
     * 根据today获取当月的第一天和最后一天
     * 
     * @param today
     * @return
     */
    public static Date[] monthDate(Date today) {
        Date[] s = new Date[2];
        cal.setTime(today);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        s[0] = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        s[1] = cal.getTime();
        return s;
    }

    /**
     * 根据today获取当月的第一天和最后一天
     * 
     * @param today
     *            格式“YYYY-MM-dd”
     * @return
     */
    public static String[] monthFirstDateOrLastDate(String today) {
        String[] datesstr = new String[2];
        Date date = parseTime(today, "yyyy-MM-dd");
        Date[] dates = monthDate(date);
        for (int j = 0; j < dates.length; j++) {
            datesstr[j] = formatTime(dates[j], "yyyy-MM-dd HH:mm:ss", "2001-01-01 00:00:00");
        }
        return datesstr;
    }

    /**
     * 根据today获取当年的第一天和最后一天
     * 
     * @param today
     * @return
     */
    public static Date[] yearDate(Date today) {
        Date[] s = new Date[2];
        cal.setTime(today);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        s[0] = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.SECOND, -1);
        s[1] = cal.getTime();
        return s;
    }

    /**
     * 根据today获取当年的第一天和最后一天
     * 
     * @param today
     *            格式“YYYY-MM-dd”
     * @return
     */
    public static String[] yearFirstDayOrLastDay(String today) {
        String[] datesstr = new String[2];
        Date date = parseTime(today, "yyyy-MM-dd");
        Date[] dates = yearDate(date);
//        System.out.println(dates[0]);
//        System.out.println(dates[1]);
        for (int j = 0; j < dates.length; j++) {
            datesstr[j] = formatTime(dates[j], "yyyy-MM-dd HH:mm:ss", "2001-01-01 00:00:00");
        }
        return datesstr;
    }

    /**
     * 根据today获取当月的第一天和最后一天
     * 
     * @param today
     *            格式“YYYY-MM-dd”
     * @return
     */
    public static Date[] monthFirstDayOrLastDay(String today) {
        Date date = parseTime(today, "yyyy-MM-dd");
        Date[] s = new Date[2];
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        s[0] = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        s[1] = cal.getTime();
        return s;
    }

    /**
     * 根据date获取这周的第一天和最后一天
     * 
     * @param today
     *            格式“YYYY-MM-dd”
     * @return
     */
    public static Date[] weekFirstDayOrLastDay(Date date) {
        Date[] s = new Date[2];
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        s[0] = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        s[1] = cal.getTime();
        return s;
    }

    /**
     * 根据today获取前一天
     * 
     * @param today
     * @return
     */
    public static Date leastDate(Date today) {
        cal.setTime(today);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 根据today获取上月的第一天和最后一天
     * 
     * @param today
     * @return
     */
    public static Date[] leastMonth(Date today) {
        Date[] s = new Date[2];
        cal.setTime(today);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        s[0] = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        s[1] = cal.getTime();
        return s;
    }

    /**
     * 对时间time添加addYear年
     * 
     * @param time
     * @param addYear
     * @return
     */
    public static Date addYear(Date time, int addYear) {
        cal.setTime(time);
        cal.add(Calendar.YEAR, addYear);
        return cal.getTime();
    }

    /**
     * 对时间time添加addMonth月
     * 
     * @param time
     * @param addMonth
     * @return
     */
    public static Date addMonth(Date time, int addMonth) {
        cal.setTime(time);
        cal.add(Calendar.MONTH, addMonth);
        return cal.getTime();
    }

    /**
     * 对时间time添加addDay天
     * 
     * @param time
     * @param addDay
     * @return
     */
    public static Date addDay(Date time, int addDay) {
        cal.setTime(time);
        cal.add(Calendar.DAY_OF_YEAR, addDay);
        return cal.getTime();
    }

    /**
     * 对时间time添加addHour小时
     * 
     * @param time
     * @param addHour
     * @return
     */
    public static Date addHour(Date time, int addHour) {
        cal.setTime(time);
        cal.add(Calendar.HOUR_OF_DAY, addHour);
        return cal.getTime();
    }

    /**
     * 对时间time添加addMinute分钟
     * 
     * @param time
     * @param addMinute
     * @return
     */
    public static Date addMinute(Date time, int addMinute) {
        cal.setTime(time);
        cal.add(Calendar.MINUTE, addMinute);
        return cal.getTime();
    }

    /**
     * 对时间time添加addSecond秒
     * 
     * @param time
     * @param addSecond
     * @return
     */
    public static Date addSecond(Date time, int addSecond) {
        cal.setTime(time);
        cal.add(Calendar.SECOND, addSecond);
        return cal.getTime();
    }

    /**
     * 将日期（date）转换为给定模式（format）的字符串。<BR>
     * 如果给定日期为null返回默认字符串（defaultV）。<BR>
     * 给定模式为null或为空字符串时使用“yyyy年MM月dd日 HH:mm:ss”模式。<BR>
     * 
     * @param date
     *            需要转换的日期型对象。为java.utils.Date类或其子类的对象。
     * @param format
     *            转换的模式，默认为yyyy年MM月dd日 HH:mm:ss
     * @param defaultV
     *            默认字符串，给定日期为null返回。
     * @return 返回格式化好的字符串，如果给定日期为null返回默认字符串。
     */
    public static String formatTime(Date date, String format, String defaultV) {
        if (date == null)
            return defaultV;
        if (format == null || format.trim().equals(""))
            format = "yyyy年MM月dd日 HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateS = sdf.format(date);
        return dateS;
    }

    /**
     * 将毫秒数表示的日期（time）转换为给定模式（format）的字符串。<BR>
     * 如果给定日期为0返回默认字符串（defaultV）。<BR>
     * 给定模式为null或为空字符串时使用“yyyy年MM月dd日 HH:mm:ss”模式。<BR>
     *
     * @param time
     *            需要转换的日期（毫秒数）。
     * @param format
     *            转换的模式，默认为yyyy年MM月dd日 HH:mm:ss
     * @param defaultV
     *            默认字符串，给定日期毫秒数为0返回。
     * @return 返回格式化好的字符串，如果给定日期为null返回默认字符串。
     */
    public static String formatTime(long time, String format, String defaultV) {
        if (time == 0L)
            return defaultV;
        Date date = new Date(time);
        return formatTime(date, format, defaultV);
    }

    /**
     * 将字符串表示的日期转换为日期的毫秒数。<BR>
     * 字符串必须按"yyyy-MM-dd HH:mm:ss"模式。<BR>
     * 转换失败时返回0。<BR>
     *
     * @param timeS
     *            表示日期的字符串
     * @return 返回日期的毫秒数。转换出错时返回0。
     */
    public static long parseTime(String timeS) {
        return parseTime(timeS, "yyyy-MM-dd HH:mm:ss").getTime();
    }

    public static Date parsedate(String timeS) {
        return parseTime(timeS, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parsedate2(String timeS) {
        return parseTime(timeS, "yyyy-MM-dd");
    }

    /**
     * 将字符串表示的日期根据给定模式（format）转换为日期的毫秒数。<BR>
     * 转换失败时返回 null 。<BR>
     *
     * @param timeS
     *            表示日期的字符串
     * @param format
     *            表示转换的模式
     * @return 返回日期的毫秒数。转换出错时返回 null。
     */
    public static Date parseTime(String timeS, String format) {
        if (format == null || format.trim().equals(""))
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(timeS);
        } catch (Exception ex) {
            // ex.printStackTrace();
            date = null;
        }
        return date;
    }

    /**
     * 获取指定日期的开始和结束时间 时间默认格式：yyyy-MM-dd
     * 
     * @return
     */
    public static Date[] nowDate0_24(String date, String formatStr) {
        if (formatStr == null || formatStr.trim().length() <= 0) {
            formatStr = "yyyy-MM-dd";
        }
        if (formatStr.equals("yyyy-MM-dd")) {
            String startDay = date + " 00:00:00";
            String endDay = date + " 23:59:59";
            return new Date[] { parseTime(startDay, "yyyy-MM-dd HH:mm:ss"), parseTime(endDay, "yyyy-MM-dd HH:mm:ss") };
        } else {
            Date now = parseTime(date, formatStr);
            return new Date[] { parseTime(formatTime(now, "yyyy-MM-dd", "") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"),
                    parseTime(formatTime(now, "yyyy-MM-dd", "") + " 23:59:59", "yyyy-MM-dd HH:mm:ss") };
        }
    }

    /**
     * 获取指定日期的开始和结束时间 时间默认格式：yyyy-MM-dd
     * 
     * @return
     */
    public static Date[] nowDate0_24(String date) {
        return nowDate0_24(date, "");
    }

    /**
     * 获取指定日期的开始和结束时间 时间默认格式：yyyy-MM-dd
     * 
     * @return
     */
    public static Date[] nowDate0_24(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sdff = sdf.format(date);
        return nowDate0_24(sdff, "yyyy-MM-dd");
    }

    /**
     * 根据东八区的本地string格式的日期 得到 GMT 的类型 获得指定日期的开始时间点和结束时间点
     * 
     * @param date
     *            日期格式 yyyy-MM-dd
     * @return
     */
    public static Date[] getGMTDate0_24(String date) {
        String startDay = date + " 00:00:00";
        String endDay = date + " 23:59:59";
        return new Date[] { addHour(parseTime(startDay, "yyyy-MM-dd HH:mm:ss"), -8),
                addHour(parseTime(endDay, "yyyy-MM-dd HH:mm:ss"), -8) };
    }

    /**
     * 获取指定日期的开始和结束时间 时间默认格式：yyyy-MM-dd
     * 
     * @return
     */
    public static Date[] getGMTDate0_24(String date, String formatStr) {
        if (formatStr == null || formatStr.trim().length() <= 0) {
            formatStr = "yyyy-MM-dd";
        }
        if (formatStr.equals("yyyy-MM-dd")) {
            String startDay = date + " 00:00:00";
            String endDay = date + " 23:59:59";
            return new Date[] { addHour(parseTime(startDay, "yyyy-MM-dd HH:mm:ss"), +8),
                    addHour(parseTime(endDay, "yyyy-MM-dd HH:mm:ss"), +8) };
        } else {
            Date now = parseTime(date, formatStr);
            return new Date[] {
                    addHour(parseTime(formatTime(now, "yyyy-MM-dd", "") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"), +8),
                    addHour(parseTime(formatTime(now, "yyyy-MM-dd", "") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"), +8) };
        }
    }

    /**
     * 获取今天的开始和结束时间
     * 
     * @return
     */
    public static Date[] nowDate0_24() {
        Date now = new Date();
        String date = formatTime(now, "yyyy-MM-dd", "");
        return nowDate0_24(date);
    }
}
