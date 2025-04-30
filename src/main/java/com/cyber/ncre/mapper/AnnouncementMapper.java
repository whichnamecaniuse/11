package com.cyber.ncre.mapper;

import com.cyber.ncre.entity.Announcement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告Mapper接口
 */
public interface AnnouncementMapper {
    
    /**
     * 获取公告列表，支持分页和状态筛选
     * @param status 状态（可选）
     * @param offset 分页偏移量
     * @param limit 每页数量
     * @return 公告列表
     */
    List<Announcement> getAnnouncementList(@Param("status") String status, 
                                          @Param("offset") int offset, 
                                          @Param("limit") int limit);
    
    /**
     * 获取公告总数
     * @param status 状态（可选）
     * @return 公告总数
     */
    int getAnnouncementCount(@Param("status") String status);
    
    /**
     * 根据ID获取公告
     * @param id 公告ID
     * @return 公告对象
     */
    Announcement getAnnouncementById(@Param("id") Long id);
    
    /**
     * 新增公告
     * @param announcement 公告对象
     * @return 受影响的行数
     */
    int insert(Announcement announcement);
    
    /**
     * 更新公告
     * @param announcement 公告对象
     * @return 受影响的行数
     */
    int update(Announcement announcement);
    
    /**
     * 删除公告
     * @param id 公告ID
     * @return 受影响的行数
     */
    int delete(@Param("id") Long id);
    
    /**
     * 更新公告状态
     * @param id 公告ID
     * @param status 状态
     * @return 受影响的行数
     */
    int updateAnnouncementStatus(@Param("id") Long id, @Param("status") String status);
    
    /**
     * 获取最新的已发布公告
     * @param limit 数量
     * @return 公告列表
     */
    List<Announcement> getLatestPublishedAnnouncements(@Param("limit") int limit);
} 