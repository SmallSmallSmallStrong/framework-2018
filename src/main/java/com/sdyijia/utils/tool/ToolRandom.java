package com.sdyijia.utils.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ToolRandom {

    /**
     * 产生随机字符-英文字母和数字
     * 
     * @param length-长度
     * @return
     */
    public static String randomStr(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        return randomStr(base, length);
    }
    
    /**
     * 产生随机字符-英文字母
     * 
     * @author Yijia
     * @param length-长度
     * @return
     */
    public static String randomEng(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz";
        return randomStr(base, length);
    }

    /**
     * 产生随机字符-英文字母和数字
     * 
     * @param length-长度
     * @return
     */
    public static String randomStr(String base, int length) { // length表示生成字符串的长度
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成随机时间 格式：yyyy-MM-dd
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    /**
     * 生成随机数（取 min~max的随机数），如果都是null则取（1000~9999的随机数）
     * 
     * @param min
     * @param max
     * @return
     */
    public static String randomStrNum(Integer min, Integer max) {
        int startNum = 1000;
        int endNum = 9999;
        if (min != null) {
            startNum = min;
        }
        if (max != null) {
            endNum = max;
        }
        if (startNum > endNum) {
            startNum = 1000;
            endNum = 9999;
        }
        Random random = new Random();
        Integer s = random.nextInt(endNum) % (endNum - startNum + 1) + startNum;
        return s.toString();
    }

}
