package com.mg.weixin.bean.wxMessage.normalMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 09:17
 * @Description:
 */
public class ShortVideoMessage extends BaseMessage {
    private String MediaId;
    private String ThumbMediaId;//视频消息缩略图的媒体id,可以调用多媒体文件下载接口拉取数据

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    @Override
    public String toString() {
        return "ShortVideoMessage{" +
                "MediaId='" + MediaId + '\'' +
                ", ThumbMediaId='" + ThumbMediaId + '\'' +
                '}'+super.toString();
    }
}
