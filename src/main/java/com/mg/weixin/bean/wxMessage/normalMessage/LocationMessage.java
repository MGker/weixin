package com.mg.weixin.bean.wxMessage.normalMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 09:19
 * @Description:
 */
public class LocationMessage extends BaseMessage {
    private float Location_X;
    private float Location_Y;
    private float Scale;
    private String Label;

    public float getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(float location_X) {
        Location_X = location_X;
    }

    public float getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(float location_Y) {
        Location_Y = location_Y;
    }

    public float getScale() {
        return Scale;
    }

    public void setScale(float scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "Location_X=" + Location_X +
                ", Location_Y=" + Location_Y +
                ", Scale=" + Scale +
                ", Label='" + Label + '\'' +
                '}'+super.toString();
    }
}
