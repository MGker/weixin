package com.mg.weixin.dao;

import com.mg.weixin.bean.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

/**
 * @Auther: fujian
 * @Date: 2018/9/8 14:46
 * @Description:
 */
@Component
public interface AccessTokenDao extends JpaRepository<AccessTokenEntity,String> {
    @Query(value="select t.* from t_access_token t order by t.create_time desc limit 0,1", nativeQuery = true)
    AccessTokenEntity findTheLastAccessToken();
}
