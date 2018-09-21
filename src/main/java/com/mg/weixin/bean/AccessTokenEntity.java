package com.mg.weixin.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 14:39
 * @Description: 用作 操作 微信 accessToken 的数据库实体对象
 */
@Entity
@Table(name="T_AccessToken")
public class AccessTokenEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;//主键
    @Column(nullable=false)
    private String accessToken;
    @Column(nullable=false)
    private Date createTime;
    @Column(nullable=false)
    private Integer available;//是否可用 1可用 0不可用
    @Column(nullable=false)
    private Integer expireIn;//过期时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer isAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", createTime=" + createTime +
                ", available=" + available +
                ", expireIn=" + expireIn +
                '}';
    }
}
