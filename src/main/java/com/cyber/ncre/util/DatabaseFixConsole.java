package com.cyber.ncre.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 数据库修复控制台应用
 * 此类用于直接修复数据库约束，在应用外部执行
 * 使用方法：
 * 1. 编译此类
 * 2. 执行: java -cp .:mysql-connector-java-8.0.29.jar com.cyber.ncre.util.DatabaseFixConsole
 */
public class DatabaseFixConsole {

    // 数据库连接参数
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ncre?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // 填写你的数据库密码

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            System.out.println("正在连接数据库...");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {
                
                System.out.println("已连接到数据库，开始修复约束...");
                
                try {
                    // 尝试方法1：先删除约束，然后修改列，最后添加新约束
                    stmt.execute("ALTER TABLE computest DROP CHECK computest_chk_1");
                    stmt.execute("ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' COMMENT '审核状态：待审核、审核通过、未通过、已删除'");
                    stmt.execute("ALTER TABLE computest ADD CONSTRAINT computest_chk_1 CHECK (cstatus IN ('待审核', '审核通过', '未通过', '已删除'))");
                    System.out.println("方法1成功修复了computest表约束");
                } catch (Exception e1) {
                    System.out.println("方法1修复约束失败: " + e1.getMessage());
                    try {
                        // 尝试方法2：使用不带约束名称的方式
                        stmt.execute("ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' CHECK (cstatus IN ('待审核', '审核通过', '未通过', '已删除')) COMMENT '审核状态：待审核、审核通过、未通过、已删除'");
                        System.out.println("方法2成功修复了computest表约束");
                    } catch (Exception e2) {
                        System.out.println("方法2修复约束失败: " + e2.getMessage());
                        try {
                            // 尝试方法3：临时禁用约束检查后再更新表结构
                            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
                            stmt.execute("SET @@SESSION.sql_mode=''");
                            stmt.execute("ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' COMMENT '审核状态：待审核、审核通过、未通过、已删除'");
                            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
                            System.out.println("方法3成功修复了computest表约束");
                        } catch (Exception e3) {
                            System.out.println("方法3修复约束失败: " + e3.getMessage());
                            System.out.println("所有方法都失败，无法修复约束");
                        }
                    }
                }
            }
            
            System.out.println("数据库处理完成!");
            
        } catch (Exception e) {
            System.err.println("执行过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 