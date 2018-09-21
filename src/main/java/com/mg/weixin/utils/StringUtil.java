package com.mg.weixin.utils;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Auther: fujian
 * @Date: 2018/9/11 19:15
 * @Description:
 */
public class StringUtil {
    public static String getRespContent(HttpResponse resp)throws Exception{
        int statusCode= statusCode = resp.getStatusLine().getStatusCode();
        String result = "";
        if(statusCode==200|statusCode==201){
            result = IOUtils.toString(new InputStreamReader(resp.getEntity().getContent()));
            System.out.println(result);
        }else{
            System.err.println("请求错误："+statusCode);
        }
        return result;
    }

    public static String inputStream2String(InputStream in){
        String str= null;
        try {
            str = IOUtils.toString(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str ;
    }
}
