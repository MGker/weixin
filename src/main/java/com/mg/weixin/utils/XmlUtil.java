package com.mg.weixin.utils;

import com.mg.weixin.bean.wxMessage.normalMessage.BaseMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/13 15:01
 * @Description: 进行xml相关操作
 */
public class XmlUtil {
    public static String objToXml(Object obj) {
        XStream xstream = new XStream();
        xstream.alias("xml", obj.getClass());
        String xmlStr = xstream.toXML(obj);
        System.out.println(xmlStr);
        return xmlStr;
    }


    public static <T> T xmlToObject(String xmlMess, Class<T> c) throws Exception {
        SAXReader reader = new SAXReader();
        T object = c.newInstance();
        Document doc = reader.read(new ByteArrayInputStream(xmlMess.getBytes()));
        //获取根节点
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        for (Element e : list) {//e.getName(), e.getText()
            try {
                Field field = c.getDeclaredField(e.getName());
                field.setAccessible(true);


                field.set(object, convertType(e.getText(), field.getGenericType().getTypeName()));


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

    public static void main(String[] args) throws Exception {
        BaseMessage message = new BaseMessage();
        message.setFromUserName("mgker");
//        message.setCount(12);
        message.setCreateTime(12312);
        String xmlStr = objToXml(message);
        System.out.println(xmlStr);

        BaseMessage message1 = xmlToObject(xmlStr,BaseMessage.class);
        System.out.println(message1);
    }

    /**
     * 将 指定字符串转成指定java常见数据类型对象
     *
     * @param typeStr
     * @return
     */
    public static Object convertType(String objStr, String typeStr) {
        if ("byte".equals(typeStr) || "java.lang.Btye".equals(typeStr)) {
            return new Byte(objStr);
        } else if ("int".equals(typeStr) || "java.lang.Integer".equals(typeStr)) {
            return new Integer(objStr);
        } else if ("short".equals(typeStr) || "java.lang.Short".equals(typeStr)) {
            return new Short(objStr);
        } else if ("long".equals(typeStr) || "java.lang.Long".equals(typeStr)) {
            return new Long(objStr);
        } else if ("float".equals(typeStr) || "java.lang.Float".equals(typeStr)) {
            return new Float(objStr);
        } else if ("double".equals(typeStr) || "java.lang.Double".equals(typeStr)) {
            return new Double(objStr);
        } else if ("char".equals(typeStr) || "java.lang.Char".equals(typeStr)) {
            return new Character(objStr.charAt(0));
        } else if ("boolean".equals(typeStr) || "java.lang.Boolean".equals(typeStr)) {
            return new Boolean(objStr);
        } else {
            return objStr;
        }
    }

}

class Type {
    private byte byteF0;
    private Byte byteF1;
    private int int0;
    private Integer int1;
    private short short0;
    private Short short1;
    private long long0;
    private Long long1;
    private float float0;
    private Float float1;
    private String string;
}
