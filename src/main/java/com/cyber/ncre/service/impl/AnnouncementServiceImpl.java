package com.cyber.ncre.service.impl;

import com.cyber.ncre.entity.Announcement;
import com.cyber.ncre.mapper.AnnouncementMapper;
import com.cyber.ncre.service.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 公告服务实现类
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImpl.class);
    
    @Autowired
    private AnnouncementMapper announcementMapper;
    
    @Override
    public List<Announcement> getAnnouncementList(int page, int size, String status) {
        logger.debug("获取公告列表，页码: {}，每页条数: {}，状态: {}", page, size, status);
        int offset = (page - 1) * size;
        try {
            return announcementMapper.getAnnouncementList(status, offset, size);
        } catch (Exception e) {
            logger.error("获取公告列表失败", e);
            return null;
        }
    }
    
    @Override
    public int getAnnouncementCount(String status) {
        logger.debug("获取公告总数，状态: {}", status);
        try {
            return announcementMapper.getAnnouncementCount(status);
        } catch (Exception e) {
            logger.error("获取公告总数失败", e);
            return 0;
        }
    }
    
    @Override
    public Announcement getAnnouncementById(Long id) {
        logger.debug("根据ID获取公告: {}", id);
        try {
            return announcementMapper.getAnnouncementById(id);
        } catch (Exception e) {
            logger.error("根据ID获取公告失败, id: {}", id, e);
            return null;
        }
    }
    
    @Override
    public boolean insert(Announcement announcement) {
        logger.debug("新增公告: {}", announcement);
        try {
            // 设置创建时间和更新时间
            Date now = new Date();
            announcement.setCreateTime(now);
            announcement.setUpdateTime(now);
            
            // 如果状态为空，设置为草稿
            if (announcement.getStatus() == null || announcement.getStatus().isEmpty()) {
                announcement.setStatus("draft");
            }
            
            return announcementMapper.insert(announcement) > 0;
        } catch (Exception e) {
            logger.error("新增公告失败", e);
            return false;
        }
    }
    
    @Override
    public boolean update(Announcement announcement) {
        logger.debug("更新公告: {}", announcement);
        try {
            // 设置更新时间
            announcement.setUpdateTime(new Date());
            return announcementMapper.update(announcement) > 0;
        } catch (Exception e) {
            logger.error("更新公告失败, id: {}", announcement.getId(), e);
            return false;
        }
    }
    
    @Override
    public boolean delete(Long id) {
        logger.debug("删除公告, id: {}", id);
        try {
            return announcementMapper.delete(id) > 0;
        } catch (Exception e) {
            logger.error("删除公告失败, id: {}", id, e);
            return false;
        }
    }
    
    @Override
    public boolean updateAnnouncementStatus(Long id, String status) {
        logger.debug("更新公告状态, id: {}, status: {}", id, status);
        try {
            return announcementMapper.updateAnnouncementStatus(id, status) > 0;
        } catch (Exception e) {
            logger.error("更新公告状态失败, id: {}, status: {}", id, status, e);
            return false;
        }
    }
    
    @Override
    public List<Announcement> getLatestPublishedAnnouncements(int limit) {
        logger.debug("获取最新发布的公告, limit: {}", limit);
        try {
            return announcementMapper.getLatestPublishedAnnouncements(limit);
        } catch (Exception e) {
            logger.error("获取最新发布的公告失败, limit: {}", limit, e);
            return null;
        }
    }
} 