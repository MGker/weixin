package com.mg.weixin.bean.wxMessage.normalMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 09:12
 * @Description:
 */
public class VoiceMessage extends BaseMessage {
    private String MediaId;
    private String Format;//语音格式，如amr,speex等
    private String Recognition;//语音识别结果，UTF8编码
    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    public String getRecognition() {
        return Recognition;
    }

    public void setRecognition(String recognition) {
        Recognition = recognition;
    }

    @Override
    public String toString() {
        return "VoiceMessage{" +
                "MediaId='" + MediaId + '\'' +
                ", Format='" + Format + '\'' +
                ", Recognition='" + Recognition + '\'' +
                '}'+super.toString();
    }
}
