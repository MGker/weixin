package com.mg.weixin.service.wxmenu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.bean.WXMenuEntity;
import com.mg.weixin.dao.WXMenuDao;
import com.mg.weixin.rest.WeiXinConfig;
import com.mg.weixin.utils.StringUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/11 16:21
 * @Description:
 */
@Service
public class WXMenuServiceImpl implements IWXMenuService {
    @Resource
    private WXMenuDao wxMenuDao;
    @Resource
    private WeiXinConfig weiXinConfig;

    //创建菜单的微信接口地址
    private String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create";

    private HttpClient httpClient = HttpClients.createDefault();

    /**
     * 从数据库中获取微信菜单json数据
     *
     * @return
     */
    public JSONObject getMenuJson() {
        List<WXMenuEntity> menu1list = wxMenuDao.findByLevelAndStatus(1, 1);//获取所有可用的一级菜单
        JSONObject menuJson = new JSONObject();
        JSONArray btnJsonArr = new JSONArray();

        for (WXMenuEntity menu : menu1list) {
            JSONObject menu1Json = new JSONObject();
            if (menu.getType().equals(WXMenuEntity.MenuType.menuGroup.getName())) {//还有下级菜单
                JSONArray subBtnArr = new JSONArray();//存储该一级菜单下二级菜单
                List<WXMenuEntity> menu2list = wxMenuDao.findByParentIdAndStatus(menu.getId(), 1);
                if (menu2list.size() > 0) {//存在可用子菜单
                    for (WXMenuEntity childMenu : menu2list) {
                        String content = childMenu.getContent();
                        JSONObject childMenuJson = null;
                        if (content != null) {
                            childMenuJson = JSONObject.parseObject(content);
                        } else {
                            childMenuJson = new JSONObject();
                        }
                        childMenuJson.put("type", childMenu.getType());
                        childMenuJson.put("name", childMenu.getMenuName());
                        childMenuJson.put("key", childMenu.getKey());
                        subBtnArr.add(childMenuJson);
                    }
                    menu1Json.put("name", menu.getMenuName());
                    menu1Json.put("sub_button", subBtnArr);
                    btnJsonArr.add(menu1Json);
                }
            } else {//没有下级菜单
                String content = menu.getContent();
                if (content != null) {
                    JSONObject contentJson = JSONObject.parseObject(content);
                    menu1Json.putAll(contentJson);
                }

                menu1Json.put("type", menu.getType());
                menu1Json.put("name", menu.getMenuName());
                menu1Json.put("key", menu.getKey());
                btnJsonArr.add(menu1Json);
            }
        }
        menuJson.put("button", btnJsonArr);
        return menuJson;
    }

    @Override
    public boolean createMenu() {
        JSONObject menuJson = getMenuJson();
        System.err.println(menuJson.toJSONString());
        HttpPost post = new HttpPost(createMenuUrl + "?access_token="
                +weiXinConfig.getAccessTokenEntity().getAccessToken());
        try {
            StringEntity entity = new StringEntity(menuJson.toJSONString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);

            HttpResponse resp = httpClient.execute(post);

            String result = StringUtil.getRespContent(resp);
            System.err.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
