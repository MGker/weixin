package com.mg.weixin.dao;

import com.mg.weixin.bean.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: fujian
 * @Date: 2018/9/20 15:40
 * @Description:
 */
public interface MaterialDao extends JpaRepository<MaterialEntity,String> {
    @Transactional
    @Modifying
    @Query("delete from MaterialEntity t where t.mediaId=?1")
    public int deleteByMediaId(String mediaId);
}
