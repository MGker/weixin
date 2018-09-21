package com.mg.weixin.bean.wxMessage.eventMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 09:36
 * @Description:
 */
public class LocationEventMessage extends BaseEventMessage {
    private float Latitude;
    private float Longitude;
    private float Precision;
    private String SendLocationInfo;//表示位置信息的json字符串
    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getPrecision() {
        return Precision;
    }

    public void setPrecision(float precision) {
        Precision = precision;
    }

    public String getSendLocationInfo() {
        return SendLocationInfo;
    }

    public void setSendLocationInfo(String sendLocationInfo) {
        SendLocationInfo = sendLocationInfo;
    }

    @Override
    public String toString() {
        return "LocationEventMessage{" +
                "Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", Precision=" + Precision +
                ", sendLocationInfo=" + SendLocationInfo +
                '}'+super.toString();
    }
}
