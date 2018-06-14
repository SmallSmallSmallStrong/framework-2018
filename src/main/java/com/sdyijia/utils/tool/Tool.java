package com.sdyijia.utils.tool;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

public class Tool {

    public static Object getValueByField(Object obj, String field) {
        String[] fns = field.split("\\.");
        Method getMothod = null;
        for (String fn : fns) {
            if (obj == null)
                return null;
            Class<? extends Object> clazz = obj.getClass();
            String temp = Character.toUpperCase(fn.charAt(0)) + fn.substring(1);
            try {
                getMothod = clazz.getMethod("get" + temp);
                obj = getMothod.invoke(obj);
            } catch (NoSuchMethodException e) {
                try {
                    getMothod = clazz.getMethod("is" + temp);
                    obj = getMothod.invoke(obj);
                } catch (Exception e1) {
                    obj = null;
                }
            } catch (Exception e) {
            }
        }

        return obj;
    }

    /**
     * 获取xml文件的内容
     *
     * @param xmlFileName
     * @return
     */
    public static Element getRootElement(String xmlFileName, final String UPLOADDIR) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        String dir = UPLOADDIR;

        try {
            File file = null;
            try {
                file = new File(dir + File.separator + xmlFileName);
            } catch (Exception e) {
                e.printStackTrace();
                file = new File(dir + xmlFileName);
            }
            doc = reader.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null) {
            return null;
        } else {
            return doc.getRootElement();
        }

    }

    /**
     * 用Spring的方式获取工程所在的具体硬盘位置（例如 E:\install\apache-tomcat-7.0.42\webapps）
     *
     * @param pojN = "/base"
     * @return
     */
    public static String projectExsitsPath(String pojN) {
        /* 获取工程实际路径和工程名 */
        String dirPath = System.getProperty("webAppRoot");
        if (pojN == null || pojN.trim().length() <= 0) {
            return dirPath;
        } else {
            pojN = pojN.substring(1, pojN.length());
            dirPath = dirPath.substring(0, dirPath.lastIndexOf(pojN));
            return dirPath;
        }
    }

    /**
     * 用Spring的方式获取工程所在的具体硬盘位置（例如 E:\install\apache-tomcat-7.0.42\webapps）
     *
     * @return
     */
    public static String projectExsitsPath() {
//        return projectExsitsPath(SysiniConfig.getSysini().getAppName());
        return "null";
    }

    /**
     * 初始化properties文件
     *
     * @param propertiesPath-文件路径
     * @param module-反射对象
     */
    public static void initProperties(String propertiesPath, Object module) {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(propertiesPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<?> enumeration = properties.propertyNames();// 得到配置文件的名字

        if (enumeration.hasMoreElements()) {

            Class<? extends Object> clazz = module.getClass();
            for (Method method : clazz.getMethods()) {
                if (method.getName().indexOf("set") == 0) {
                    String temp = method.getName().substring(3);
                    String key = ToolStr.indexCodeLowerCase(temp);
                    String value = properties.getProperty(key);
                    Method setMothod = null;
                    try {
                        setMothod = clazz.getMethod("set" + temp, String.class);
                        setMothod.invoke(module, value);
                    } catch (Exception e) {
                    }
                }
            }
        }

    }

    /**
     * 解析html获取document的属性值（返回一个值）
     */
    public static String parseHtml(String html, String attribute) {
        String value = "";
        org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(html);
        org.jsoup.nodes.Element body = document.body();
        org.jsoup.select.Elements imgs = body.getElementsByTag("img");
        if (imgs == null || imgs.isEmpty()) {
            return null;
        }
        for (org.jsoup.nodes.Element element : imgs) {
            value = element.attr(attribute);
        }
        return value;
    }
}
