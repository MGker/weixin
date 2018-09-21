package com.mg.weixin.bean.wxMessage.eventMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 11:28
 * @Description:
 */
public class ScanCodeEventMessage extends BaseEventMessage {
    private String ScanCodeInfo;

    public String getScanCodeInfo() {
        return ScanCodeInfo;
    }

    public void setScanCodeInfo(String scanCodeInfo) {
        ScanCodeInfo = scanCodeInfo;
    }
}
