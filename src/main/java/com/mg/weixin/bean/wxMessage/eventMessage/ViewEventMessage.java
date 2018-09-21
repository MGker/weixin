package com.mg.weixin.bean.wxMessage.eventMessage;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 11:22
 * @Description:
 */
public class ViewEventMessage extends BaseEventMessage {
    private String MenuId;//菜单ID

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
