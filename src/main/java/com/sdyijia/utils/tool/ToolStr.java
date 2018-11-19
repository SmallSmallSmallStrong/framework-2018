package com.sdyijia.utils.tool;

import java.util.Collection;
import java.util.Map;

public class ToolStr {

    /**
     * 判断str是不是null，如果是null或者是空格返回true，否则返回false
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断objs数组是不是null，如果是null或者是空格返回true，否则返回false
     *
     * @param objs
     * @return
     */
    public static boolean isEmpty(Object[] objs) {
        if (objs == null || objs.length <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断obj是不是null，如果是null或者是空格返回true，否则返回false
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断map是不是null，如果是null或者是空格返回true，否则返回false
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断Collection容器是不是null，如果是null或者是空格返回true，否则返回false
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null || collection.size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断传入的字符串的第一个字母，如果是大写转为小写，如果不是返回原字符串。
     *
     * @param code
     * @return
     */
    public static String indexCodeLowerCase(String code) {
        char[] chars = new char[1];
        chars[0] = code.charAt(0);
        String str = code;
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            str = code.replaceFirst(temp, temp.toLowerCase());
        }
        return str;
    }

    /**
     * 将字符串表示的整型数据转化为int型。
     *
     * @param s 描述整型数据的字符串
     * @return 返回转换好的int，如果转换时出错则返回0。
     */
    public static int sToI(String s) {
        return sToI(s, 0);
    }

    /**
     * 将字符串表示的整型数据转化为int型。
     *
     * @param s            描述整型数据的字符串
     * @param defaultValue 转换失败时默认返回的值。
     * @return 返回转换好的int，如果转换时出错则返回defaultValue。
     */
    public static int sToI(String s, int defaultValue) {
        int i = defaultValue;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
        }
        return i;
    }

    /**
     * 将字符串表示的整型数据转化为double型。
     *
     * @param s 描述整型数据的字符串
     * @return 返回转换好的double，如果转换时出错则返回0。
     */
    public static double sToD(String s) {
        return sToD(s, 0d);
    }

    /**
     * 将字符串表示的整型数据转化为double型。
     *
     * @param s            描述整型数据的字符串
     * @param defaultValue 转换失败时默认返回的值。
     * @return 返回转换好的double，如果转换时出错则返回defaultValue。
     */
    public static double sToD(String s, double defaultValue) {
        Double d = defaultValue;
        try {
            d = Double.parseDouble(s);
        } catch (Exception e) {
        }
        if (d == null || d.isInfinite()) {
            d = defaultValue;
        }
        return d;
    }

    /**
     * 将字符串表示的整型数据转化为long型。
     *
     * @param s            描述整型数据的字符串
     * @param defaultValue 转换失败时默认返回的值。
     * @return 返回转换好的int，如果转换时出错则返回defaultValue。
     */
    public static long sToL(String s, long defaultValue) {
        long i = defaultValue;
        try {
            i = Long.parseLong(s);
        } catch (Exception e) {
        }
        return i;
    }

    /**
     * 将Long转为String
     *
     * <pre>
     * &#64;param l 如果转换失败，返回""
     * </pre>
     *
     * @return
     */
    public static String L2S(Long l) {
        return L2S(l, "");
    }

    /**
     * 将Long转为String
     *
     * <pre>
     * &#64;param l
     * &#64;param defaultStr--如果转换失败，返回此值
     * </pre>
     *
     * @return
     */
    public static String L2S(Long l, String defaultStr) {
        String str = null;
        try {
            str = l.toString();
        } catch (Exception e) {
            if (defaultStr == null) {
                str = "";
            } else {
                str = defaultStr;
            }
            // e.printStackTrace();
        }
        return str;
    }

    /**
     * 将Integer转为String
     *
     * <pre>
     * &#64;param i 如果转换失败，返回""
     * </pre>
     *
     * @return
     */
    public static String I2S(Integer i) {
        return I2S(i, "");
    }

    /**
     * 将Integer转为String
     *
     * <pre>
     * &#64;param i
     * &#64;param defaultStr--如果转换失败，返回此值
     * </pre>
     *
     * @return
     */
    public static String I2S(Integer i, String defaultStr) {
        String str = null;
        try {
            str = i.toString();
        } catch (Exception e) {
            if (defaultStr == null) {
                str = "";
            } else {
                str = defaultStr;
            }
            // e.printStackTrace();
        }
        return str;
    }

    /**
     * 模糊字符串<BR>
     * 用于sql模糊查询<BR>
     * 将字符串中的空格替换为“%”<BR>
     * 字符串两头添加“%” <BR>
     *
     * @param strSource 需要模糊的字符串
     * @return 模糊后的字符串
     */
    public static String fuzzy(String strSource) {
        String strResult = null;
        if (strSource == null || strSource.trim().length() == 0) {
            strResult = "%";
        } else {
            strResult = "%" + strSource.replace(' ', '%') + "%";
            strResult = strResult.replaceAll("%+", "%");
        }
        return strResult;
    }

    /**
     * html字符转换
     *
     * @param strData
     * @return
     */
    public static String htmlEscape(String strData) {
        if (strData == null) {
            return "";
        }
        strData = replaceString(strData, "&", "&amp;");
        strData = replaceString(strData, "<", "&lt;");
        strData = replaceString(strData, ">", "&gt;");
        strData = replaceString(strData, "'", "&apos;");
        strData = replaceString(strData, "\"", "&quot;");
        return strData;
    }

    /**
     * 替换？
     *
     * @param strData
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceString(String strData, String regex, String replacement) {
        if (strData == null) {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0) {
            while (index >= 0) {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }

    /**
     * 首字母大写
     *
     * @param name 需要处理的字符串
     * @return
     */
    public static String captureNametoUp(String name) {
        char[] cs = name.toCharArray();
        if (cs[0] > 97 && cs[0] < 122)//a-z的Char，必须符合
            cs[0] -= 32;
        return String.valueOf(cs);
    }


    /**
     * 首字母小写
     *
     * @param name 需要处理的字符串
     * @return
     */
    public static String captureNametoLow(String name) {
        char[] cs = name.toCharArray();
        if (cs[0] > 65 && cs[0] < 90)//A-Z的Char，必须符合
            cs[0] += 32;
        return String.valueOf(cs);
    }


}
