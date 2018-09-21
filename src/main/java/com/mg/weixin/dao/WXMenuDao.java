package com.mg.weixin.dao;

import com.mg.weixin.bean.WXMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/11 16:21
 * @Description:
 */
public interface WXMenuDao extends JpaRepository<WXMenuEntity,String> {

    /**
     * * 获取所有可用的菜单对象
     * @param status
     * @return
     */
     List<WXMenuEntity> findByStatus(int status);

    /**
     * 获取所有指定级别和状态的菜单
     * @param level
     * @param status
     * @return
     */
    List<WXMenuEntity> findByLevelAndStatus(int level,int status);

    /**
     * 获取指定父菜单和状态的菜单
     * @param parentId
     * @param status
     * @return
     */
    List<WXMenuEntity> findByParentIdAndStatus(int parentId,int status);




}
