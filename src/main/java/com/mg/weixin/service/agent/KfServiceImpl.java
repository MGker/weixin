package com.mg.weixin.service.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.bean.KFAccountEntity;
import com.mg.weixin.dao.KFDao;
import com.mg.weixin.rest.WeiXinConfig;
import com.mg.weixin.utils.HttpUtil;
import com.mg.weixin.utils.StringUtil;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: fujian
 * @Date: 2018/9/15 17:25
 * @Description:
 */
@Service
public class KfServiceImpl implements IKFService {
    @Resource
    private WeiXinConfig weiXinConfig;
    @Resource
    private KFDao kfDao;
    private String addUrl = "https://api.weixin.qq.com/customservice/kfaccount/add";//添加客服接口
    private String updateUrl = "https://api.weixin.qq.com/customservice/kfaccount/update";//编辑客服接口
    private String deleteUrl = "https://api.weixin.qq.com/customservice/kfaccount/del";//删除客户接口
    private String kfImageUrl = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg";//设置客服头像的接口
    private String getKFListUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";//获取所有客服账号

    public void addKFAccount(KFAccountEntity accountEntity){
        Map paramMap = new HashMap<>();
        paramMap.put("kf_account",accountEntity.getAccountName());
        paramMap.put("nickname ",accountEntity.getNickname());
        paramMap.put("password ",accountEntity.getPassword());
        try {
            HttpResponse resp = HttpUtil.post(addUrl+"?access_token="+
                    weiXinConfig.getAccessTokenEntity().getAccessToken(),paramMap);
            String result = StringUtil.getRespContent(resp);
            System.err.println(result);
            JSONObject json = JSONObject.parseObject(result);
            if("0".equals(json.getString("errcode"))){
                System.out.println("客服添加成功");
            }else{
                System.err.println("客服添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateKFAccount(KFAccountEntity accountEntity){
        Map paramMap = new HashMap<>();
        paramMap.put("kf_account",accountEntity.getAccountName());
        paramMap.put("nickname ",accountEntity.getNickname());
        paramMap.put("password ",accountEntity.getPassword());
        try {
            HttpResponse resp = HttpUtil.post(updateUrl+"?access_token="+
                    weiXinConfig.getAccessTokenEntity().getAccessToken(),paramMap);
            String result = StringUtil.getRespContent(resp);
            System.err.println(result);
            JSONObject json = JSONObject.parseObject(result);
            if("0".equals(json.getString("errcode"))){
                System.out.println("客服更新成功");
            }else{
                System.err.println("客服更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKFAccount(KFAccountEntity accountEntity){
        Map paramMap = new HashMap<>();
        paramMap.put("kf_account",accountEntity.getAccountName());
        paramMap.put("nickname ",accountEntity.getNickname());
        paramMap.put("password ",accountEntity.getPassword());
        try {
            HttpResponse resp = HttpUtil.post(deleteUrl+"?access_token="+
                    weiXinConfig.getAccessTokenEntity().getAccessToken(),paramMap);
            String result = StringUtil.getRespContent(resp);
            System.err.println(result);
            JSONObject json = JSONObject.parseObject(result);
            if("0".equals(json.getString("errcode"))){
                System.out.println("客服删除成功");
            }else{
                System.err.println("客服删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setKFImage(String kfAccount,String imagePath){
        Map paramMap = new HashMap<>();
        File image = new File(imagePath);
        paramMap.put("file",image);
        try {
            HttpResponse resp = HttpUtil.multiPost(kfImageUrl+"?access_token="+
                    weiXinConfig.getAccessTokenEntity().getAccessToken()+"&kf_account="+kfAccount,paramMap);
            String result = StringUtil.getRespContent(resp);
            System.err.println(result);
            JSONObject json = JSONObject.parseObject(result);
            if("0".equals(json.getString("errcode"))){
                System.out.println("客服头像设置成功");
            }else{
                System.err.println("客服头像设置失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KFAccountEntity> getAllRemoteKF(){
        HttpResponse resp = HttpUtil.get(getKFListUrl+"?access_token="+
                weiXinConfig.getAccessTokenEntity().getAccessToken());
        List<KFAccountEntity> kfList = new ArrayList<KFAccountEntity>();
        try {
            String result = StringUtil.getRespContent(resp);
            JSONObject json = JSONObject.parseObject(result);
            JSONArray jsonArr = json.getJSONArray("kf_list");
            if(jsonArr!=null){
                for(int i=0;i<jsonArr.size();i++){
                    JSONObject kfJson = jsonArr.getJSONObject(i);
                    KFAccountEntity kf = new KFAccountEntity();
                    kf.setAccountName(kfJson.getString("kf_account"));
                    kf.setNickname(kfJson.getString("kf_nick"));
                    kf.setId(kfJson.getString("kf_id"));
                    kf.setHeadImgUrl(kfJson.getString("kf_headimgurl"));
                    kfList.add(kf);
                }
            }else{
                System.err.println("获取所有客服失败:"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kfList;
    }
}
