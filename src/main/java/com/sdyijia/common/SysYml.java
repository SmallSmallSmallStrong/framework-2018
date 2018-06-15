package com.sdyijia.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@ConfigurationProperties(prefix = "sys", value = "classpath:application.properties")
@Component
public class SysYml {

    @Value("${privateKey}")
    private String privateKey;
    @Value("${appname}")
    private String appname;
    @Value("${copyright}")
    private String copyright;
    @Value("${address}")
    private String address;


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
