package com.sdyijia.utils.email;

public class Emailini {
    // 服务器邮箱账号
    private String emailAccount;
    // 服务器邮箱密码
    private String emailPassword;
    // 服务器SMTP地址
    private String emailSMTPHost;
    // 使用的协议
    private String emailTransportProtocol;
    // 请求认证
    private String emailAuth;

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getEmailSMTPHost() {
        return emailSMTPHost;
    }

    public void setEmailSMTPHost(String emailSMTPHost) {
        this.emailSMTPHost = emailSMTPHost;
    }

    public String getEmailTransportProtocol() {
        return emailTransportProtocol;
    }

    public void setEmailTransportProtocol(String emailTransportProtocol) {
        this.emailTransportProtocol = emailTransportProtocol;
    }

    public String getEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(String emailAuth) {
        this.emailAuth = emailAuth;
    }

}
