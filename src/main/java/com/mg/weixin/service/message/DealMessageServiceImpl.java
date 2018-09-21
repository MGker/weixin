package com.mg.weixin.service.message;

import com.mg.weixin.bean.WXUserEntity;
import com.mg.weixin.bean.wxMessage.eventMessage.BaseEventMessage;
import com.mg.weixin.bean.wxMessage.normalMessage.Article;
import com.mg.weixin.bean.wxMessage.normalMessage.NewsMessage;
import com.mg.weixin.bean.wxMessage.normalMessage.TextMessage;
import com.mg.weixin.dao.WXUserDao;
import com.mg.weixin.utils.MessageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 14:46
 * @Description:
 */
@Service
public class DealMessageServiceImpl implements IDealMessageService {
    @Resource
    WXUserDao wxUserDao;

    /**
     * 处理订阅 取消订阅事件
     * @param message
     */
    public void dealSubscribe(BaseEventMessage message){
        String event = message.getEvent();
        String fromUser = message.getFromUserName();
        WXUserEntity wxUser = wxUserDao.findByUserName(fromUser);
        if(wxUser==null){//用户不存在本地,则新建
            wxUser = new WXUserEntity();
            wxUser.setUserName(fromUser);
        }
        Date operateTime = new Date(message.getCreateTime()*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if("subscribe".equals(event)){//用户订阅事件
            wxUser.setSubscribeTime(operateTime);
            wxUser.setStatus(1);
            System.err.println("用户订阅:"+wxUser);
            wxUserDao.save(wxUser);
        }else if("unsubscribe".equals(event)){
            wxUser.setUnSubscribeTime(operateTime);
            wxUser.setSubscribeTime(null);
            wxUser.setStatus(-1);
            System.err.println("用户取消订阅:"+wxUser);
            wxUserDao.save(wxUser);
        }
    }

    public String dealTextMessage(TextMessage message){
        String content = "收到您的消息："+message.getContent();
        if("图文".equals(message.getContent())){
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(message.getFromUserName());
            newsMessage.setFromUserName(message.getToUserName());
            newsMessage.setCreateTime(new Date().getTime());
            List<Article> articleList = new ArrayList<>();
            Article article1 = new Article();
            article1.setTitle("图文消息");
            article1.setDescription("图文消息描述");
            article1.setPicUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3947877243,466477032&fm=26&gp=0.jpg");
            article1.setUrl("http://www.qq.com");
            articleList.add(article1);

            Article article2 = new Article();
            article2.setTitle("图文消息2");
            article2.setDescription("图文消息描述2");
            article2.setPicUrl("http://img4.imgtn.bdimg.com/it/u=513181329,4265564633&fm=200&gp=0.jpg");
            article2.setUrl("http://www.qq.com");
            articleList.add(article2);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            newsMessage.setMsgType("news");
            newsMessage.setCreateTime((new Date().getTime())/1000);

            String xmlStr = MessageUtil.messageToXML(newsMessage);
            System.out.println(xmlStr);
            return xmlStr;
        }
        return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
    }

}
