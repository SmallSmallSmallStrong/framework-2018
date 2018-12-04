package com.sdyijia.myclass;

import org.jboss.logging.Logger;

public class PersonDanli {
    private Logger logger = Logger.getLogger(PersonDanli.class);

    public <T> T getPersonUseMyClassLoader() {
        MyClassLoader loader = new MyClassLoader();
        Class<?> aClass = loader.findClass(Person.class.getName()) ;
            logger.info(String.format("Person.class.getName() ：%s", Person.class.getName() ));
        try {
            Object obj = aClass.newInstance();

            logger.info(String.format("obj.equals(Person.class)：%s", obj.getClass().equals(Person.class)));
// 输出false
            logger.info(String.format("obj instanceof Person：%s", obj instanceof Person));
// 输出false
            logger.info(String.format("obj 的 classLoader：%s", obj.getClass().getClassLoader()));
// 输出com.zhqy.classLoad.MyClassLoader
            logger.info(String.format("Person 的 classLoader：%s", Person.class.getClassLoader()));
// 输出sun.misc.Launcher$AppClassLoader


            return (T) obj;
        } catch (Exception e) {
            logger.error(String.format("错误：%s", e));
        }
        return null;


    }

    public static void main(String[] args) {
        PersonDanli personDanli = new PersonDanli();
        Person person = personDanli.getPersonUseMyClassLoader();

    }

}
