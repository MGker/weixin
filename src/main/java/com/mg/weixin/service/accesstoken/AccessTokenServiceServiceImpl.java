package com.mg.weixin.service.accesstoken;

import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.bean.AccessTokenEntity;
import com.mg.weixin.dao.AccessTokenDao;
import com.mg.weixin.rest.WeiXinConfig;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 20:16
 * @Description: 处理微信 access_token的业务类
 */
@Service
public class AccessTokenServiceServiceImpl implements IAccessTokenService {
    @Resource
    private AccessTokenDao accessTokenDao;
    @Resource
    private WeiXinConfig weiXinConfig;
    private HttpClient httpClient = HttpClients.createDefault();
    private Logger log = LoggerFactory.getLogger(AccessTokenServiceServiceImpl.class);


    /**
     * 从微信服务 获取 accessToken 并保存到库中
     * @return
     */
    @Override
    public AccessTokenEntity refreshAccessToken() {
        String result = getAccessTokenFromWX();
        System.err.println(result);
        JSONObject resultJson = JSONObject.parseObject(result);
        System.err.println(resultJson.getString("errcode"));
        if(resultJson.getString("errcode")==null){
            String accessToken = resultJson.getString("access_token");
            Integer expiresIn = resultJson.getInteger("expires_in");
            AccessTokenEntity accessTokenEntityObj = new AccessTokenEntity();
            accessTokenEntityObj.setAccessToken(accessToken);
            accessTokenEntityObj.setExpireIn(expiresIn);
            accessTokenEntityObj.setCreateTime(new Date());
            accessTokenEntityObj.setAvailable(1);
            accessTokenDao.save(accessTokenEntityObj);
            System.err.println(accessTokenEntityObj);
//            weiXinConfig.setAccessTokenEntity(accessTokenEntityObj);
            return accessTokenEntityObj;
        }else{
            log.error("更新access_token失败.微信返回错误提示:"+resultJson);
            return null;
        }



    }

    /**
     * 获取可用的access_token(库中没过期则用库中的，过期了则执行 access_token刷新方法从微信获取)
     * @return
     */
    @Override
    public AccessTokenEntity getTheAvaiableAccessToken() {
        AccessTokenEntity accessTokenEntity = accessTokenDao.findTheLastAccessToken();//从库中取出最新的AccessToken记录
        if(accessTokenEntity!=null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(accessTokenEntity.getCreateTime());
            calendar.add(Calendar.SECOND,accessTokenEntity.getExpireIn());
            int compareNum = calendar.getTime().compareTo(new Date());//比较accesstoken过期时间是否超过当前时间
            if(compareNum>0){//库中最新记录还没有过期
                return accessTokenEntity;
            }
        }
        //库中最新的accesstoken过期或者还库里没有accesstoken,则从微信端获取最新的accesstoken
        return refreshAccessToken();
    }

    public String getAccessTokenFromWX() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential" +
                "&appid=" + weiXinConfig.getAppId() +
                "&secret=" + weiXinConfig.getAppsecret();
        HttpGet get = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = httpClient.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int code = response.getStatusLine().getStatusCode();
        if (code == 200 || code == 201) {
            try {
                String result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            log.error("请求微信 access_token 接口失败,返回码:" + code);
        }
        return "";
    }

    public static void main(String[] args) {

        AccessTokenServiceServiceImpl accessImpl = new AccessTokenServiceServiceImpl();
        accessImpl.weiXinConfig = new WeiXinConfig();
        accessImpl.weiXinConfig.setAppId("wx9069710499e6140e");
        accessImpl.weiXinConfig.setAppsecret("1dcdc04860eb77fd89b66ab761a926f5");
        String result = accessImpl.getAccessTokenFromWX();
        System.err.println(result);
    }
}
