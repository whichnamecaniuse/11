package com.cyber.ncre.util;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseFixer implements InitializingBean {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            fixComputestConstraint();
        } catch (Exception e) {
            LogManager.getLogger().error("修复数据库约束失败", e);
        }
    }

    private void fixComputestConstraint() {
        try {
            // 尝试方法1：分步修改
            tryUpdateConstraint();
        } catch (Exception e1) {
            LogManager.getLogger().warn("方法1修复约束失败，尝试方法2", e1);
            try {
                // 尝试方法2：单步修改
                tryUpdateConstraintAlternative();
            } catch (Exception e2) {
                LogManager.getLogger().warn("方法2修复约束失败，尝试方法3", e2);
                try {
                    // 尝试方法3：禁用约束检查后修改
                    tryUpdateConstraintWithDisabledChecks();
                } catch (Exception e3) {
                    LogManager.getLogger().error("所有方法都失败，无法修复约束", e3);
                }
            }
        }
    }

    private void tryUpdateConstraint() {
        // 方法1：先删除约束，然后修改列，最后添加新约束
        jdbcTemplate.execute("ALTER TABLE computest DROP CHECK computest_chk_1");
        jdbcTemplate.execute("ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' COMMENT '审核状态：待审核、审核通过、未通过、已删除'");
        jdbcTemplate.execute("ALTER TABLE computest ADD CONSTRAINT computest_chk_1 CHECK (cstatus IN ('待审核', '审核通过', '未通过', '已删除'))");
        LogManager.getLogger().info("方法1成功修复了computest表约束");
    }

    private void tryUpdateConstraintAlternative() {
        // 方法2：使用不带约束名称的方式
        jdbcTemplate.execute("ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' CHECK (cstatus IN ('待审核', '审核通过', '未通过', '已删除')) COMMENT '审核状态：待审核、审核通过、未通过、已删除'");
        LogManager.getLogger().info("方法2成功修复了computest表约束");
    }

    private void tryUpdateConstraintWithDisabledChecks() {
        // 方法3：临时禁用约束检查后再更新表结构
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0");
        jdbcTemplate.execute("SET @@SESSION.sql_mode=''");
        jdbcTemplate.execute("ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' COMMENT '审核状态：待审核、审核通过、未通过、已删除'");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1");
        LogManager.getLogger().info("方法3成功修复了computest表约束");
    }
} 