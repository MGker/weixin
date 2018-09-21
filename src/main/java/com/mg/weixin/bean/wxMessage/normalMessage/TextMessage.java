package com.mg.weixin.bean.wxMessage.normalMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/12 18:33
 * @Description:
 */
public class TextMessage extends BaseMessage{
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "Content='" + Content + '\'' +
                '}'+super.toString();
    }
}
