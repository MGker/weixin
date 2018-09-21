package com.mg.weixin.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: fujian
 * @Date: 2018/9/20 15:27
 * @Description:微信素材表实体类
 */

@Entity
@Table(name="t_material")
public class MaterialEntity {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    @Column(nullable = false)
    private String mediaId;
    @Column(columnDefinition="varchar(255) comment '当素材类型为图片时才有,该图片的访问地址'")
    private String url;
    @Column(nullable = false,columnDefinition="int(2) comment '素材的类型(1:图片,2:语音,3:视频,4:缩略图,5:图文)'")
    private int type;
    @Column
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static enum Type{
        IMAGE(1,"image"),VOICE(2,"voice"),VIDEO(3,"video"),THUMB(4,"thumb"),NEWS(5,"news");
        public int value;
        public String text;
        Type(int value,String text){
            this.text=text;
            this.value=value;
        }
    }
}
