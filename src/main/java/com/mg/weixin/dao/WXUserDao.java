package com.mg.weixin.dao;

import com.mg.weixin.bean.WXUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: fujian
 * @Date: 2018/9/14 15:07
 * @Description:
 */
public interface WXUserDao extends JpaRepository<WXUserEntity,String> {
     WXUserEntity findByUserName(String fromUser);
}
