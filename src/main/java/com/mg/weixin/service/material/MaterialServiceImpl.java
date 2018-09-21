package com.mg.weixin.service.material;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.bean.MaterialEntity;
import com.mg.weixin.dao.MaterialDao;
import com.mg.weixin.rest.Result;
import com.mg.weixin.rest.WeiXinConfig;
import com.mg.weixin.utils.HttpUtil;
import com.mg.weixin.utils.StringUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: fujian
 * @Date: 2018/9/20 15:42
 * @Description:
 */
@Service
public class MaterialServiceImpl implements IMaterialService {
    private Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);
    private final String addNewsUrl = "https://api.weixin.qq.com/cgi-bin/material/add_news";
    private final String addOtherUrl = "https://api.weixin.qq.com/cgi-bin/material/add_material";
    private final String getMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/get_material";
    private final String deleteMaterialUrl = "https://api.weixin.qq.com/cgi-bin/material/del_material";

    @Resource
    private WeiXinConfig weiXinConfig;

    @Resource
    private MaterialDao materialDao;

    @Override
    public Result addNormalMaterial(MaterialEntity.Type type, String filePath) {
        Result result = new Result(0, false);
        File mediaFile = new File(filePath);
        if(mediaFile.exists()){
            Map<String, Object> param = new HashMap<>();
            param.put("access_token", weiXinConfig.getAccessTokenEntity().getAccessToken());
            param.put("type", type.text);
            param.put("media",mediaFile);
            HttpResponse resp = HttpUtil.multiPost(addOtherUrl, param);
            try {
                String resultStr = StringUtil.getRespContent(resp);
                result = dealAddResult(type,resultStr,result);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            log.error("上传的素材文件找不到:"+filePath);
            result.setMessage("上传的素材文件找不到:"+filePath);
        }
        return result;
    }

    @Override
    public Result addVideoMaterial(String title, String introduction, String videoPath) {
        Result result = new Result(0,false);
        File videoFile = new File(videoPath);
        if(videoFile.exists()){
            MaterialEntity.Type type = MaterialEntity.Type.VIDEO;
            Map<String,Object> param = new HashMap<>();
            param.put("access_token", weiXinConfig.getAccessTokenEntity().getAccessToken());
            param.put("type", type.text);
            param.put("media",videoFile);
            JSONObject desJson = new JSONObject();
            desJson.put("title",title);
            desJson.put("introduction",introduction);
            param.put("description",desJson);
            HttpResponse resp = HttpUtil.multiPost(addOtherUrl,param);

            try {
                String resultStr = StringUtil.getRespContent(resp);
                result = dealAddResult(type,resultStr,result);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            System.err.println("文件找不到:"+videoPath);
        }

        return result;
    }

    @Override
    public Result getNewsMaterial(String mediaId) {
        return null;
    }

    @Override
    public Result getVideoMaterial(String mediaId) {
        return null;
    }

    @Override
    public Result getNormalMaterial(String mediaId){
        Result  result= new Result(0,false);
        Map<String,String> param = new HashMap<>();
        String url =getMaterialUrl+"?access_token="
                +weiXinConfig.getAccessTokenEntity().getAccessToken();
        param.put("media_id",mediaId);
        HttpResponse resp = HttpUtil.post(url,param);

        try {
           Header header =  resp.getEntity().getContentType();
           InputStream in = resp.getEntity().getContent();
           if(header==null){
               result.setSuccess(true);
                result.setData(in);
           }else{
               String content = StringUtil.inputStream2String(in);
               result.setData(content);
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Result deleteMaterial(String mediaId) {
        Result  result = new Result(0,false);
        Map<String,String> param = new HashMap<>();
        param.put("media_id",mediaId);
        String url = deleteMaterialUrl+"?access_token="+weiXinConfig.getAccessTokenEntity().getAccessToken() ;
        HttpResponse resp = HttpUtil.post(url,param);
        try {
            String content = StringUtil.getRespContent(resp);
            System.err.println(content);
            JSONObject json = JSONObject.parseObject(content);
            if("0".equals(json.getString("errcode"))){
                result.setSuccess(true);
                result.setMessage("success");
                int code = materialDao.deleteByMediaId(mediaId);
                System.out.println("删除返回值:"+code);
            }else{
                result.setMessage(json.getString("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 处理添加素材的获取的返回结果
     * @param type
     * @param resultStr
     * @param result
     * @return
     */
    private Result dealAddResult(MaterialEntity.Type type,String resultStr,Result result){
        if (!"".equals(resultStr)) {
            JSONObject json = JSONObject.parseObject(resultStr);
            String mediaId = json.getString("media_id");
            System.err.println("mediaId:" + mediaId);
            if (mediaId != null) {
                MaterialEntity material = new MaterialEntity();
                material.setCreateTime(new Date());
                material.setMediaId(mediaId);
                material.setType(type.value);

                //url仅当素材为图片时存在
                String url = json.getString("url");
                material.setUrl(url);
                materialDao.save(material);
                result.setCode(1);
                result.setSuccess(true);
            }
        }
        return result;
    }
}
