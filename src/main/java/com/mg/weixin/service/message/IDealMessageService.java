package com.mg.weixin.service.message;

import com.mg.weixin.bean.wxMessage.eventMessage.BaseEventMessage;
import com.mg.weixin.bean.wxMessage.normalMessage.TextMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 14:46
 * @Description:
 */
public interface IDealMessageService {
    public void dealSubscribe(BaseEventMessage message);

    public String dealTextMessage(TextMessage message);
}
