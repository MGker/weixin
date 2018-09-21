package com.mg.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: fujian
 * @Date: 2018/9/15 17:39
 * @Description:
 */
public class HttpUtil {
    private static HttpClient httpClient = HttpClients.createDefault();

    public static HttpResponse get(String url){
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse resp = httpClient.execute(get);
            return resp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse post(String url,Map<String,String> paramMap){
        HttpPost post = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(JSONObject.toJSONString(paramMap));
            entity.setContentEncoding("utf8");
            post.setEntity(entity);
            return httpClient.execute(post);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse put(String url){
        HttpPut put = new HttpPut(url);
        try {
           return httpClient.execute(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse delete(String url){
        HttpDelete delete = new HttpDelete(url);
        try {
            return httpClient.execute(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse multiPost(String url,Map<String,Object> paramMap){
        MultipartEntityBuilder multi = MultipartEntityBuilder.create();
        HttpPost post = new HttpPost(url);
        Set<String> keySet = paramMap.keySet();
        for(String key:keySet){
            Object obj = paramMap.get(key);
            if(obj instanceof File){
                FileBody bin = new FileBody((File)obj);
                multi.addPart(key,bin);
            }else{
                StringBody param = new StringBody(obj.toString(), ContentType.create(
                        "text/plain", Consts.UTF_8));
                multi.addPart(key,param);
            }
        }
        post.setEntity(multi.build());
        try {
            return httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
