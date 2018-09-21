package com.mg.weixin.utils;

import com.mg.weixin.bean.wxMessage.normalMessage.Article;
import com.mg.weixin.bean.wxMessage.normalMessage.BaseMessage;
import com.mg.weixin.bean.wxMessage.normalMessage.NewsMessage;
import com.mg.weixin.bean.wxMessage.normalMessage.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.json.XML;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;


/**
 * @Auther: fujian
 * @Date: 2018/9/12 14:20
 * @Description: 微信返回消息格式解析
 */
public class MessageUtil {
    public static void main(String[] args) throws Exception {

    }

    public static <T> T reqToObject(HttpServletRequest req, Class<T> c) throws Exception {
        SAXReader reader = new SAXReader();
        T object = c.newInstance();
        Document doc = reader.read(req.getInputStream());
        //获取根节点
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {
            try {
                Field field = c.getDeclaredField(e.getName());
                field.setAccessible(true);
                System.out.println(field.getType());

                field.set(object, XmlUtil.convertType(e.getText(), field.getGenericType().getTypeName()));


            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }

        return object;
    }

    /**
     * dom4j 解析 xml 转换为 map
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml2Map(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点.没有子节点的直接存入map中,有子节点的将子节点数组封装成json格式再存入map中
        for (Element e : elementList){
            List<Element> list = e.elements();
            if(list.size()==0){
                map.put(e.getName(), e.getText());
            }else{
                String childXml = e.asXML();
                JSONObject json = XML.toJSONObject(childXml);
                map.put(e.getName(),json.toString());
                System.err.println(json.toString());
            }
        }

        // 释放资源
        inputStream.close();
        return map;
    }

    public static <T> T mapToMessageObj(Map<String,String> messMap, Class<T> c)throws Exception{
        Set<String> keySet = messMap.keySet();
        T object = c.newInstance();
        Class superClass = c.getSuperclass();
        for(String key:keySet){
//            System.err.println(key+"---"+messMap.get(key));
            try {
                Field field = c.getDeclaredField(key);
                field.setAccessible(true);
//                System.out.println(field.getType());
                field.set(object, XmlUtil.convertType(messMap.get(key), field.getGenericType().getTypeName()));
            } catch (NoSuchFieldException e1) {
                try {
                    Field field = superClass.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(object, XmlUtil.convertType(messMap.get(key), field.getGenericType().getTypeName()));
                } catch (NoSuchFieldException e) {
                   // e.printStackTrace();
                    System.err.println("对象中找不到该属性："+key);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return object;
    }

   /* public static String convertToBackXml(BaseMessage baseMessage){
        if(baseMessage!=null){
            String toUser = baseMessage.getFromUserName();
            String fromUser = baseMessage.getToUserName();
            baseMessage.setFromUserName(fromUser);
            baseMessage.setToUserName(toUser);
            return XmlUtil.objToXml(baseMessage);
        }
        return null;
    }*/

    public static String textMessageXML(String fromUser,String toUser,String content){
        TextMessage message = new TextMessage();
        message.setFromUserName(fromUser);
        message.setToUserName(toUser);
        message.setContent(content);
        message.setMsgType("text");
        message.setCreateTime((new Date().getTime())/1000);
        return XmlUtil.objToXml(message);
    }

    public static String messageToXML(BaseMessage message){
        XStream xstream = new XStream();
        xstream.alias("xml", message.getClass());
        if(message instanceof NewsMessage){
            xstream.alias("item",Article.class);
        }
        String xmlStr = xstream.toXML(message);
        return xmlStr;
    }
}
