package com.cyber.ncre.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 统计数据Mapper接口
 */
public interface StatisticsMapper {
    
    /**
     * 获取报名统计数据 - 按天统计
     * @param period 时间周期 (week, month, year)
     * @return 每天的报名人数统计
     */
    List<Map<String, Object>> getRegistrationStatsByDay(@Param("period") String period);
    
    /**
     * 获取审核通过的报名统计数据 - 按天统计
     * @param period 时间周期 (week, month, year)
     * @return 每天审核通过的人数统计
     */
    List<Map<String, Object>> getApprovedRegistrationStatsByDay(@Param("period") String period);
} 