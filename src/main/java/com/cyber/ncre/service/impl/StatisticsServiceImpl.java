package com.cyber.ncre.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyber.ncre.mapper.StatisticsMapper;
import com.cyber.ncre.service.StatisticsService;

/**
 * 统计数据服务实现类
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    
    @Autowired
    private StatisticsMapper statisticsMapper;
    
    @Override
    public List<Map<String, Object>> getRegistrationStatsByDay(String period) {
        logger.debug("获取报名统计数据, 周期: {}", period);
        try {
            return statisticsMapper.getRegistrationStatsByDay(period);
        } catch (Exception e) {
            logger.error("获取报名统计数据失败", e);
            return null;
        }
    }
    
    @Override
    public List<Map<String, Object>> getApprovedRegistrationStatsByDay(String period) {
        logger.debug("获取审核通过的报名统计数据, 周期: {}", period);
        try {
            return statisticsMapper.getApprovedRegistrationStatsByDay(period);
        } catch (Exception e) {
            logger.error("获取审核通过的报名统计数据失败", e);
            return null;
        }
    }
} 