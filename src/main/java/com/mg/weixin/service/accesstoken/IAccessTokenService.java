package com.mg.weixin.service.accesstoken;

import com.mg.weixin.bean.AccessTokenEntity;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 22:09
 * @Description:
 */
public interface IAccessTokenService {
    AccessTokenEntity refreshAccessToken();

    /**
     *获取最新可用的AccessTokenEntity对象
     * @return
     */
     AccessTokenEntity getTheAvaiableAccessToken();
}
