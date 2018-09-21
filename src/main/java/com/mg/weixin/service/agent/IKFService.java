package com.mg.weixin.service.agent;

import com.mg.weixin.bean.KFAccountEntity;

import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/15 17:25
 * @Description:
 */
public interface IKFService {
    public void addKFAccount(KFAccountEntity accountEntity);

    public void updateKFAccount(KFAccountEntity accountEntity);

    public void deleteKFAccount(KFAccountEntity accountEntity);

    public void setKFImage(String kfAccount,String imagePath);

    public List<KFAccountEntity> getAllRemoteKF();
}
