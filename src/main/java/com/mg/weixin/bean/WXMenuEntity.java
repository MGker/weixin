package com.mg.weixin.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: fujian
 * @Date: 2018/9/11 16:11
 * @Description: 微信服务号按钮菜单
 */
@Entity
@Table(name="T_WX_Menu")
public class WXMenuEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(nullable=false,unique = true)
    private String menuName;

    /**
     * 菜单类型
     */
    @Column
    private String type;

    /**
     * 菜单的key
     */
    @Column(name = "`key`")
    private String key;

    /**
     * 菜单级别 1:一级菜单 2:二级菜单
     */
    @Column(nullable=false)
    private Integer level;

    /**
     * 菜单状态 1:启用 0:禁用
     */
    @Column(nullable=false)
    private Integer status;

    /**
     * 父菜单的id(一级菜单没有)
     */
    @Column
    private Integer parentId;

    /**
     *对于不同菜单类型，以json格式保存特有的字段数据
     */
    @Column(length = 2024)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static enum MenuType{
        menuGroup("menu_group"),scancodeWaitmsg("scancode_waitmsg"),
        picSysphoto("pic_sysphoto"),picPhotoOrAlbum("pic_photo_or_album"),
        picWeixin("pic_weixin"),locationSelect("location_select"),
        mediaId("media_id"),viewLimited("view_limited"),click("click"),view("view");
        private String name;
        MenuType(String name){
            this.name=name;
        }
        public String getName(){
            return this.name;
        }
    }
}
