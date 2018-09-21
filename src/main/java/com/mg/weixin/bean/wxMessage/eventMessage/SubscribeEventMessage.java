package com.mg.weixin.bean.wxMessage.eventMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 09:33
 * @Description: 扫描关注事件推送
 */
public class SubscribeEventMessage extends BaseEventMessage {
    private String Ticket;//二维码的ticket，可用来换取二维码图片

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
