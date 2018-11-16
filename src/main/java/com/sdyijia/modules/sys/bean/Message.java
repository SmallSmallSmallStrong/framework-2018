package com.sdyijia.modules.sys.bean;

import com.sdyijia.modules.base.bean.Base;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Message extends Base {

    private String name;
    private String content;

    private String source;
    private String state; //0未读 1已读

    private String belong; //0收件 1发件

    private String send;

    private String lz;

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }


}
