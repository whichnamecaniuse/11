package com.cyber.ncre.service;

import com.cyber.ncre.entity.Announcement;
import java.util.List;

/**
 * 公告服务接口
 */
public interface AnnouncementService {
    
    /**
     * 获取公告列表
     * @param page 页码
     * @param size 每页条数
     * @param status 状态（可选）
     * @return 公告列表
     */
    List<Announcement> getAnnouncementList(int page, int size, String status);
    
    /**
     * 获取公告总数
     * @param status 状态（可选）
     * @return 公告总数
     */
    int getAnnouncementCount(String status);
    
    /**
     * 根据ID获取公告
     * @param id 公告ID
     * @return 公告对象
     */
    Announcement getAnnouncementById(Long id);
    
    /**
     * 新增公告
     * @param announcement 公告对象
     * @return 是否成功
     */
    boolean insert(Announcement announcement);
    
    /**
     * 更新公告
     * @param announcement 公告对象
     * @return 是否成功
     */
    boolean update(Announcement announcement);
    
    /**
     * 删除公告
     * @param id 公告ID
     * @return 是否成功
     */
    boolean delete(Long id);
    
    /**
     * 更新公告状态
     * @param id 公告ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateAnnouncementStatus(Long id, String status);
    
    /**
     * 获取最新发布的公告
     * @param limit 限制数量
     * @return 公告列表
     */
    List<Announcement> getLatestPublishedAnnouncements(int limit);
} 