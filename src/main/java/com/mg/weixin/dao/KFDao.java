package com.mg.weixin.dao;

import com.mg.weixin.bean.KFAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: fujian
 * @Date: 2018/9/15 17:24
 * @Description:
 */
public interface KFDao extends JpaRepository<KFAccountEntity,String> {
}
