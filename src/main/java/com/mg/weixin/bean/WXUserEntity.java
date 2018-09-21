package com.mg.weixin.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 14:52
 * @Description: 封装 微信用户
 */
@Entity
@Table(name="T_WXUser")
public class WXUserEntity {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator ="system-uuid")
    private String id;
    @Column(unique = true,columnDefinition="varchar(255) comment '用户名称（一个OpenID）'")
    private String userName;
    @Column(nullable = true,columnDefinition="datetime comment '用户订阅日期'")
    private Date subscribeTime;//订阅日期

    @Column(columnDefinition="datetime comment '用户取消订阅日期'")
    private Date unsubscribeTime;//取消订阅日期
    @Column(columnDefinition="int comment '用户状态 -1：取消了订阅，0：本地禁用，1：正常订阅可用'")
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUnSubscribeTime() {
        return unsubscribeTime;
    }

    public void setUnSubscribeTime(Date unsubscribeTime) {
        this.unsubscribeTime = unsubscribeTime;
    }

    @Override
    public String toString() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "WXUserEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", unsubscribeTime=" + unsubscribeTime +
                ", status=" + status +
                '}';
    }
}
