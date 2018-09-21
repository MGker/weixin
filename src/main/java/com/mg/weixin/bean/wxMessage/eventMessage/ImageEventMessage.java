package com.mg.weixin.bean.wxMessage.eventMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 11:07
 * @Description:
 */
public class ImageEventMessage extends BaseEventMessage{
    private String SendPicsInfo;

    public String getSendPicsInfo() {
        return SendPicsInfo;
    }

    public void setSendPicsInfo(String sendPicsInfo) {
        SendPicsInfo = sendPicsInfo;
    }
}
