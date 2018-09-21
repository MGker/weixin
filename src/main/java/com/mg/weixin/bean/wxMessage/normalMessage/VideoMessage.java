package com.mg.weixin.bean.wxMessage.normalMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 09:16
 * @Description:
 */
public class VideoMessage extends BaseMessage {
    private String MediaId;//视频消息媒体id 可以调用多媒体文件下载接口拉取数据

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    @Override
    public String toString() {
        return "VideoMessage{" +
                "MediaId='" + MediaId + '\'' +
                '}'+super.toString();
    }
}
