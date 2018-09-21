package com.mg.weixin.bean.wxMessage.eventMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/13 16:48
 * @Description: 基本事件消息数据封装类。可用于：关注/取消关注事件，自定义菜单事件
 */
public class BaseEventMessage {
    /**
     *开发者 微信号
     **/
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;

    /**
     * 消息创建时间 （整型）距离1970的秒数
     */
    private long CreateTime;

    /**
     * 消息类型，event
     */
    private String MsgType;

    private String EventKey;
    private String Event;
    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", EventKey='" + EventKey + '\'' +
                ", Event='" + Event + '\'' +
                '}';
    }
}
