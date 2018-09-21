package com.mg.weixin.service.material;

import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.bean.MaterialEntity;
import com.mg.weixin.rest.Result;

import java.io.InputStream;

/**
 * @Auther: fujian
 * @Date: 2018/9/20 15:42
 * @Description:
 */
public interface IMaterialService {

    /**
     * 新增普通永久素材
     * @param type 普通素材类型 图片,语音,缩略图
     * @param filePath 素材文件地址
     * @return
     */
     Result addNormalMaterial(MaterialEntity.Type type, String filePath);

    /**
     * 新增视频素材
     * @param title 标题
     * @param introduction 视频介绍
     * @param videoPath 视频文件地址
     * @return
     */
    Result addVideoMaterial(String title,String introduction,String videoPath);

    Result getNewsMaterial(String mediaId);

    Result getVideoMaterial(String mediaId);

    Result getNormalMaterial(String mediaId);

    Result deleteMaterial(String mediaId);

}
