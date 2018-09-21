package com.mg.weixin.rest;

import com.mg.weixin.bean.AccessTokenEntity;
import com.mg.weixin.service.accesstoken.IAccessTokenService;
import com.mg.weixin.service.wxmenu.WXMenuServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 19:44
 * @Description: 在Spring 容器 加载完成后 执行.
 */
@Component
public class InitParam implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    IAccessTokenService iAccesToken;
    @Resource
    WeiXinConfig weiXinConfig;
    @Resource
    private WXMenuServiceImpl wxMenuImpl;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //只在root application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理
        if (event.getApplicationContext().getParent() == null) {
            accessToken();

//            wxMenu();

        }
    }

    /**
     * 定义关于 accessToken 项目启动时执行的内容
     */
    private void accessToken(){
        //获取可用的AccessToken对象
        AccessTokenEntity accessTokenEntity = iAccesToken.getTheAvaiableAccessToken();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(accessTokenEntity.getCreateTime());
        calendar.add(Calendar.SECOND,accessTokenEntity.getExpireIn());
        long delay = (calendar.getTimeInMillis() - new Date().getTime())/1000;//计算该可用accesstoken的过期时长（单位秒）
        delay = delay-100;
        if(delay<0){//如果过期时间过短，则置零，让定时任务可以立马执行获取最新的accesstoken
            delay = 0;
        }else{
            weiXinConfig.setAccessTokenEntity(accessTokenEntity);
        }
        //定时任务
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(()->{
            AccessTokenEntity accessTokenEntity1 = iAccesToken.refreshAccessToken();
            weiXinConfig.setAccessTokenEntity(accessTokenEntity);
        }, delay,7100,TimeUnit.SECONDS);
    }

    /**
     * 定义 关于微信菜单 项目启动执行的操作
     */
    public void wxMenu(){
        //创建微信菜单
        wxMenuImpl.createMenu();
    }
}
