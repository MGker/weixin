package com.mg.weixin.rest;

import com.mg.weixin.bean.AccessTokenEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 19:40
 * @Description: 微信
 */
@Component
@PropertySource("classpath:conf.properties")
public class WeiXinConfig {
    @Value("${appID}")
    private String appId;
    @Value("${appsecret}")
    private String appsecret;

    private AccessTokenEntity accessTokenEntity;//用来保存 最新可用的微信 access_token对象

    public  String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public AccessTokenEntity getAccessTokenEntity(){
       return  this.accessTokenEntity;
    }
    public void setAccessTokenEntity(AccessTokenEntity accessTokenEntity){
        this.accessTokenEntity = accessTokenEntity;
    }
}
