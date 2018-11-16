package com.sdyijia.modules.sys.controller;

import com.sdyijia.modules.sys.bean.Message;
import com.sdyijia.modules.sys.repository.UserRepository;
import com.sdyijia.modules.sys.repository.MessageRepository;
import com.sdyijia.modules.sys.service.MessageService;
import com.sdyijia.utils.email.Emailini;
import com.sdyijia.utils.email.EmailiniConfig;
import com.sdyijia.utils.email.SendEmailUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;


@Controller
public class MessageController extends SysController{
    //前缀--文件夹名--如果有多层文件夹就写多层
    private final String PREFIX = "/sys/message/";
    private final String LIST = PREFIX + "msgList";
    private final String REMOVE = PREFIX + "msgRemove";
    private final String STA = PREFIX + "sta";
    private final String SEND = PREFIX + "send";
    private final String SENDTO = PREFIX + "sendto";

    private void addURl(Model m) {
       m.addAttribute("LIST", LIST);
       m.addAttribute("REMOVE", REMOVE);
        m.addAttribute("STA", STA);
        m.addAttribute("SEND", SEND);
        m.addAttribute("SENDTO", SENDTO);
    }


    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageService messageService;


    private static String myEmailAccount = "1173283077@qq.com";
    private static String myEmailPsw = "ovqxlwbdbwucjica";
    //QQSMTP服务器地址为:smtp.qq.com
    private static String myEmailSMTPHost = "smtp.qq.com";

/*---------------------
    作者：leadhow
    来源：CSDN
    原文：https://blog.csdn.net/weixin_35843891/article/details/79102161
    版权声明：本文为博主原创文章，转载请附上博文链接！*/



    /**
     * 消息列表;
     *
     * @return
     */
    @RequestMapping(LIST)
    @GetMapping(LIST)
    public String msgList(Model m){
        addURl(m);
        List<Message> msg=messageRepository.findAll();
        m.addAttribute("msglist",msg);

       return "sys/message/list";
    }

    /**
     * 消息删除;
     *
     * @return
     */
    @RequestMapping(REMOVE)
    @GetMapping(REMOVE)
     public String msgRemove(Long id) {
        messageRepository.deleteById(id);
        return "redirect:msgList";
    }
    /**
     * 消息已读;
     *
     * @return
     */
    @RequestMapping(STA)
    @GetMapping(STA)
    public String sta(Long id) {
        Message msg=messageRepository.getOne(id);
        msg.setState("1");
        messageRepository.save(msg);
        return "redirect:msgList";
    }

    /**
     * 发送消息页面;
     *
     * @return
     */
    @RequestMapping(SEND)
    @GetMapping(SEND)
    public String send(Model m) {
        addURl(m);
        //使用用户管理员（具有该权限的）直接添加用户
        return PREFIX + "sendAdd";
    }

    /**
     * 发送消息;
     *
     * @return
     */
    @RequestMapping(SENDTO)
    @PostMapping(SENDTO)
    public String sendto(Model m,String email,String name,String content) {
        addURl(m);
        try {
            sendEmail(email, name, content); //发送邮件
            Message msg=new Message();
            msg.setState("0");
            msg.setBelong("1");
            msg.setSend(myEmailAccount);
            msg.setLz(email);
            msg.setName(name);
            msg.setContent(content);
            messageService.save(msg);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:msgList";
    }
    public static void sendEmail(String receiveMailAccount, String receiveMailTitle, String receiveMailContent) throws Exception {
       /* Emailini emailini = EmailiniConfig.getEmailini();*/
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties(); // 参数配置
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的SMTP服务器地址
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

        // session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
        // 如果需要ssl就写上这五行
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        MimeMessage message;
        // 3. 创建一封邮件
        message = createMimeMessage(session, myEmailAccount, receiveMailAccount, receiveMailTitle,
                receiveMailContent);

        // 也可以保持到本地查看
        // message.writeTo(file_out_put_stream);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        // 这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(myEmailAccount, myEmailPsw);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients()
        // 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封复杂邮件（文本+图片+附件）
     */
    private static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,
                                                 String receiveMailTitle, String receiveMailContent) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(receiveMailTitle, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(receiveMailContent, "text/html;charset=UTF-8");

        // 12. 设置发件时间
        message.setSentDate(new Date());

        // 13. 保存上面的所有设置
        message.saveChanges();

        return message;
    }

}
