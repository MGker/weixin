package com.mg.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.bean.wxMessage.eventMessage.*;
import com.mg.weixin.bean.wxMessage.normalMessage.*;
import com.mg.weixin.service.message.IDealMessageService;
import com.mg.weixin.utils.MessageUtil;
import com.mg.weixin.utils.SHAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 13:45
 * @Description:
 */
@Controller
@PropertySource("classpath:conf.properties")
public class MainController {
    @Value("${token}")
    private String  TOKEN;

    @Resource
    IDealMessageService idealMessageService;


    @RequestMapping(value="token",method=RequestMethod.GET)
    public void token(HttpServletRequest req, HttpServletResponse resp) throws NoSuchAlgorithmException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        // 将token、timestamp、nonce三个参数进行字典序排序
        System.out.println("signature:"+signature);
        System.out.println("timestamp:"+timestamp);
        System.out.println("nonce:"+nonce);
        System.out.println("echostr:"+echostr);
        System.out.println("TOKEN:"+TOKEN);
        String[] params = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(params);
        // 将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String algorithm = "SHA-1";
        String sign = SHAUtil.sha1(clearText);

        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(sign)) {
            resp.getWriter().print(echostr);
        }
    }

    @RequestMapping(value="token",method=RequestMethod.POST)
    @ResponseBody
    public String wxNotice(HttpServletRequest req,
                         HttpServletResponse resp)throws Exception{
//        System.err.println(IOUtils.toString(req.getReader()));
        InputStream inputStream = req.getInputStream();
        Map<String, String> resultMap = MessageUtil.parseXml2Map(req);
        String msgType = resultMap.get("MsgType");
        if("event".equals(msgType)){//消息类型为事件
            String event = resultMap.get("Event");
            if("location_select".equals(event)){
                LocationEventMessage message = MessageUtil.mapToMessageObj(resultMap,LocationEventMessage.class);
                System.out.println(JSONObject.toJSONString(message));
            }else if("subscribe".equals(event)&&resultMap.get("Ticket")!=null){
                SubscribeEventMessage message = MessageUtil.mapToMessageObj(resultMap,SubscribeEventMessage.class);
                System.out.println(JSONObject.toJSONString(message));
            }else if("pic_photo_or_album".equals(event)
                    ||"pic_weixin".equals(event)||"pic_sysphoto".equals(event)){//发送照片事件
                ImageEventMessage message = MessageUtil.mapToMessageObj(resultMap,ImageEventMessage.class);
                System.out.println(JSONObject.toJSONString(message));
            }else if("VIEW".equals(event)){//跳转视图事件,如打开网页
                ViewEventMessage message = MessageUtil.mapToMessageObj(resultMap,ViewEventMessage.class);
                System.out.println(JSONObject.toJSONString(message));
            }else if("scancode_push".equals(event)||"scancode_waitmsg".equals(event)){//扫码事件
                ScanCodeEventMessage message = MessageUtil.mapToMessageObj(resultMap,ScanCodeEventMessage.class);
                System.out.println(JSONObject.toJSONString(message));
            }else{
                BaseEventMessage message = MessageUtil.mapToMessageObj(resultMap,BaseEventMessage.class);
                if("subscribe".equals(message.getEvent())||"unsubscribe".equals(message.getEvent())){
                    idealMessageService.dealSubscribe(message);
                }
                System.out.println(JSONObject.toJSONString(message));
            }
        }else if("text".equals(msgType)){//普通文本消息
            TextMessage message = MessageUtil.mapToMessageObj(resultMap,TextMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            return  idealMessageService.dealTextMessage(message);
        }else if("image".equals(msgType)){//图片
            ImageMessage message = MessageUtil.mapToMessageObj(resultMap,ImageMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            String content = "收到您的消息：image";
            return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
        }else if("voice".equals(msgType)){//语音
            VoiceMessage message = MessageUtil.mapToMessageObj(resultMap,VoiceMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            String content = "收到您的消息：voice";
            return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
        }else if("video".equals(msgType)){//视频
            VideoMessage message = MessageUtil.mapToMessageObj(resultMap,VideoMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            String content = "收到您的消息：video";
            return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
        }else if("shortvideo".equals(msgType)){//短视频
           ShortVideoMessage message = MessageUtil.mapToMessageObj(resultMap,ShortVideoMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            String content = "收到您的消息：shortvideo";
            return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
        }else if("location".equals(msgType)){//位置
          LocationMessage message = MessageUtil.mapToMessageObj(resultMap,LocationMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            String content = "收到您的消息：location";
            return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
        }else if("link".equals(msgType)){//链接
            LinkMessage message = MessageUtil.mapToMessageObj(resultMap,LinkMessage.class);
            System.out.println(JSONObject.toJSONString(message));
            String content = "收到您的消息：link";
            return MessageUtil.textMessageXML(message.getToUserName(),message.getFromUserName(),content);
        }

        return "";
    }

}
