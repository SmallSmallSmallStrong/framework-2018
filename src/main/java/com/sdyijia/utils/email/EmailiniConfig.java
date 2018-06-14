package com.sdyijia.utils.email;

import java.util.logging.Logger;

//TODO 需要设置响应的属性
public class EmailiniConfig {
    private static Logger logger = Logger.getLogger("EmailiniConfig");

    private static Emailini emailini;

    private EmailiniConfig() {

    }

    public static Emailini getEmailini() {

        if (emailini == null) {
            synchronized (EmailiniConfig.class) {
                if (emailini == null) {
                    initEmailini();
                }
            }
        }

        return emailini;
    }

    private static Emailini initEmailini() {

        emailini = new Emailini();
        readConfig(emailini);

        return emailini;
    }

    @SuppressWarnings("unchecked")
    private static void readConfig(Emailini emailini) {
//        /* 获取本文件实际路径 */
//        String dirPath = SysiniConfig.class.getResource("/").getPath();
//        // 获取xml文件的内容
//        Element root = Tool.getRootElement("emailini.xml", dirPath);
//        if(root == null){
//            logger.error("请先上传文件");
//            return;
//        }
//        // 获取第一个element
//        List<Element> elementList = root.elements();
//        // 遍历第一个element
//        for(Element ge : elementList){
//            String emailAccount = ge.attributeValue("emailAccount");
//            String emailPassword = ge.attributeValue("emailPassword");
//            String emailSMTPHost = ge.attributeValue("emailSMTPHost");
//            String emailTransportProtocol = ge.attributeValue("emailTransportProtocol");
//            String emailAuth = ge.attributeValue("emailAuth");
//
//            emailini.setEmailAccount(emailAccount);
//            emailini.setEmailPassword(emailPassword);
//            emailini.setEmailSMTPHost(emailSMTPHost);
//            emailini.setEmailTransportProtocol(emailTransportProtocol);
//            emailini.setEmailAuth(emailAuth);
//
//        }

    }
}
